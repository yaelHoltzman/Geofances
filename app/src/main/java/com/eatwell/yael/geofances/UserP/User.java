package com.eatwell.yael.geofances.UserP;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.Goals.GoalFactory;
import com.eatwell.yael.geofances.UI.Goal_Setting;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;


public class User implements UserI {

    private static final String TAG = User.class.getSimpleName();

    //Goal Indicators
    private ArrayList<Goal> goalArrayList;
    private int currentGoalIndex;
    private int numberOfGoals;

    //Location Indicators
    private HashMap<String, LatLng> userLocations;

    //Notification flag
    private boolean isNotificationsOn;


    @Override
    public Goal GetGoal() {
        Log.d(TAG, "GetGoal");
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

    public User() {
        currentGoalIndex = 0;
        numberOfGoals = 0;
        goalArrayList = new ArrayList<>();
        userLocations = new HashMap<>();
        isNotificationsOn = false;
        GoalFactory goalFactory = new GoalFactory();

        //go over the goals and add them to the list
        //TODO - is this the right context?
        Context goalSettingContext = Goal_Setting.GetGSContext();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(goalSettingContext);

        isNotificationsOn = sharedPref.getBoolean("switch_recieveNotifications", false);

        numberOfGoals = goalFactory.GetGoals(goalArrayList);
        //TODO make sure number of goals is not 0?
    }
}