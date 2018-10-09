package com.eatwell.yael.geofances.Goals;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eatwell.yael.geofances.UI.Goal_Setting;

import java.util.ArrayList;
import java.util.Map;


public class GoalFactory {

    private static ArrayList<String> preferences;

    public GoalFactory() {

        preferences = new ArrayList<>();

        preferences.add("Mindful");
        preferences.add("Weightloss");
    }

    public static Goal getGoal (String goalKey) {
            if (goalKey == "Mindful") {
                return new Goal_MindfulEating();
            }
            else if (goalKey == "Weightloss") {
                return new Goal_WeightLoss();
            }
            return null;
        }

    public static ArrayList<String> getAllGoalPreferences() {
        return preferences;
    }

}
