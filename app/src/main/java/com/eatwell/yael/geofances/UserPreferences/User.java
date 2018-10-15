package com.eatwell.yael.geofances.UserPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.Goals.GoalFactory;
import com.eatwell.yael.geofances.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

//User Singleton
public class User {

    private static final String TAG = User.class.getSimpleName();
    //Goal Indicators
    private ArrayList<Goal> goalArrayList;
    private int currentGoalIndex;
    private int numberOfGoals;

    //app
    private Context mContext;
    private SharedPreferences sharedPref;
    private GoalFactory goalFactory;

    //Location Indicators
    private HashMap<String, LatLng> userLocations;

    //boolean vals settings
    private static boolean isNotificationsOn = true;
    private static boolean isSoundOn = true;
    private static boolean isVibrateOn = true;
    private static boolean isHomeLocationSet = false;
    private static boolean isWorkLocationSet = false;


    private static boolean isWallPaperHome = true;
    private static boolean isWallPaperLock = true;

    private boolean isThisFirstRun = true;


    //user Instance
    private static User userInstance;

    public static User getInstance() {

        if (userInstance == null) {
            userInstance = new User();
        }

        return userInstance;
    }


    public boolean isWallPaperHome() {
        return isWallPaperHome;
    }

    public boolean isWallPaperLock() {
        return isWallPaperLock;
    }

    public LatLng getLocation (String locationType) {
        return userLocations.get(locationType);
    }

    public void addLocation(String locationType, LatLng latLng) {
        userLocations.put(locationType, latLng);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putLong( "KEY_GEOFENCE_LAT" + locationType, (long)latLng.latitude);
        editor.putLong( "KEY_GEOFENCE_LON" + locationType, (long)latLng.longitude);
        editor.apply();
    }

    public void removeLocation(String locationType) {
        userLocations.remove(locationType);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("KEY_GEOFENCE_LAT" + locationType);
        editor.remove("KEY_GEOFENCE_LON" + locationType);
    }

    public void setIsLocationSet(String locationType, boolean val) {

        if (locationType.equals(mContext.getResources().getString(R.string.home))) {
            isHomeLocationSet = val;
        } else if (locationType.equals(mContext.getResources().getString(R.string.work))) {
            isWorkLocationSet= val;
        }
    }

    public boolean isLocationSet(String locationType) {
        boolean res = false;

        if (locationType.equals(mContext.getResources().getString(R.string.home))) {
            res =  isHomeLocationSet;
        } else if (locationType.equals(mContext.getResources().getString(R.string.work))) {
            res =  isWorkLocationSet;
        }
        return res;
    }

    public void setmContext(Context context) {
        mContext = context;
    }

    public Context getmContext() {
       return mContext;
    }

    public int getNumberOfGoals() {
        return numberOfGoals;
    }

    public Goal getGoal() {
        Log.d(TAG, "getGoal");
        //loadGoals();

        if (numberOfGoals == 0) {
            Log.i(TAG, "request for goal, 0 goals");
            return null;
        }

        Goal goal = goalArrayList.get(currentGoalIndex % numberOfGoals);
        ++currentGoalIndex;

        return goal;
    }


    public void setLocation(LatLng latLng, String locationName) {
        userLocations.put(locationName, latLng);
    }

    public boolean isGettingNotifications() {

        return isNotificationsOn;
    }

    private User() {
        currentGoalIndex = 0;
        numberOfGoals = 0;
        goalArrayList = new ArrayList<>();
        userLocations = new HashMap<>();
        isNotificationsOn = true;
        goalFactory = new GoalFactory();

    }

    public void loadPreferences() {
        if (sharedPref == null) {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        }

        isNotificationsOn = sharedPref.getBoolean("switch_recieveNotifications", true);
        isSoundOn = sharedPref.getBoolean("@string/switch_notificationSounds", true);
        isVibrateOn= sharedPref.getBoolean("switch_notificationVibration", true);
        isWallPaperLock = sharedPref.getBoolean("switch_changeLockWallpaper", true);
        isWallPaperHome = sharedPref.getBoolean("switch_changeHomeWallpaper", true);
        //TODO add notification recieving time preference
    }

    public void loadGoals() {

        goalFactory.setPreferences();

        if (sharedPref == null) {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        }

        ArrayList<String> allGoalPreferences = goalFactory.getAllGoalPreferences();
        for (String entry : allGoalPreferences) {

            boolean pref = sharedPref.getBoolean(entry, true);
            if (pref) {
                //create a new goal
                goalArrayList.add(goalFactory.getGoal(entry));
                ++numberOfGoals;
                Log.d(TAG, "Number of goals: " + numberOfGoals);
            }
        }
    }

    //preference getter functions
    public boolean isNotificationsOn() {
        return isNotificationsOn;
    }

    public boolean isVibration() {
        return isVibrateOn;
    }

    public boolean isFirstRun() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        isThisFirstRun= prefs.getBoolean(getmContext().getResources().getString(R.string.isFirstRun), false);

        return isThisFirstRun;
    }

    public void setFirstRun(boolean firstRun) {

        if (sharedPref == null) {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        }

        sharedPref.edit()
                    .putBoolean(getmContext().getResources().getString(R.string.isFirstRun), firstRun).apply();
    }

}