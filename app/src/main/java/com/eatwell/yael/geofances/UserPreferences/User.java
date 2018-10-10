package com.eatwell.yael.geofances.UserPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.Goals.GoalFactory;
import com.eatwell.yael.geofances.UI.Goal_Setting;
import com.eatwell.yael.geofances.UI.SettingsActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;


public class User extends Activity implements UserI {

    private static final String TAG = User.class.getSimpleName();
    //Goal Indicators
    private ArrayList<Goal> goalArrayList;
    private static int currentGoalIndex;
    private static int numberOfGoals;

    //private static User mContext;
    private Context mContext;

    private SharedPreferences sharedPref;
    private GoalFactory goalFactory;
    //private Context mcontext;


    //Location Indicators
    private HashMap<String, LatLng> userLocations;

    //Notification flag
    private static boolean isNotificationsOn;

    private static User userInstance;

    public static User getInstance() {

        if (userInstance == null) {
            userInstance = new User();
        }

        return userInstance;
    }

    public void setmContext(Context context) {
        mContext = context;
    }

    public Context getmContext() {
       return mContext;
    }

    @Override
    public Goal GetGoal() {
        Log.d(TAG, "GetGoal");
        LoadGoals(goalArrayList);

        ++currentGoalIndex;
        return goalArrayList.get(currentGoalIndex % numberOfGoals);
    }

    @Override
    public LatLng GetLocation(String locationName) {

        return userLocations.get(locationName);
    }

    @Override
    public void SetLocation(LatLng latLng, String locationName) {
        userLocations.put(locationName, latLng);
    }

    @Override
    public boolean IsGettingNotifications() {

        return isNotificationsOn;
    }

    private User(/*Context context*/) {
        currentGoalIndex = 0;
        numberOfGoals = 0;
        goalArrayList = new ArrayList<>();
        userLocations = new HashMap<>();
        isNotificationsOn = false;
        goalFactory = new GoalFactory();
        //mContext = this;
        //mcontext = context;

        //TODO - use Loadgoals in Onclick Next of usergoal settings and remove from here?
        //go over the goals and add them to the list
        //LoadGoals(goalArrayList);
        //TODO make sure number of goals is not 0?
    }

    public void LoadGoals (ArrayList<Goal> goalArrayList) {

        //SettingsActivity sa = new SettingsActivity();
        //SettingsActivity.GeneralPreferenceFragment gfs = new SettingsActivity.GeneralPreferenceFragment();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);

        //isNotificationsOn = sharedPref.getBoolean("switch_recieveNotifications", false);


        ArrayList<String> allGoalPreferences = goalFactory.getAllGoalPreferences();
        for (String entry : allGoalPreferences) {

            boolean pref = sharedPref.getBoolean(entry, true);
            if (pref) {
                //create a new goal
                goalArrayList.add(goalFactory.getGoal(entry));
                ++numberOfGoals;
                Log.d(TAG, "NUMBER OF GOALS: " + numberOfGoals);
            }
        }
    }

}