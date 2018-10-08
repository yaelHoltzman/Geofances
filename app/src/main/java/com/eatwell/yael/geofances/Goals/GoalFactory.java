package com.eatwell.yael.geofances.Goals;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eatwell.yael.geofances.UI.Goal_Setting;

import java.util.ArrayList;
import java.util.Map;

public class GoalFactory {

    private Map<String, Goal> goalCreators;

    private Goal_MindfulEating meGoal;
    private Goal_WeightLoss wlGoal;

    private SharedPreferences sharedPref;

    public GoalFactory() {

        goalCreators.put("switch_mindful", meGoal);
        goalCreators.put("switch_weightLoss", wlGoal);

        //TODO add water drink and fitness goals as well

    }

    public int GetGoals(ArrayList<Goal> goalArrayList) {

        int numberOfGoals = 0;

        Context goalSettingContext = Goal_Setting.GetGSContext();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(goalSettingContext);

        for (Map.Entry<String, Goal> entry : goalCreators.entrySet()) {
            boolean isGoal = LoadPref(entry.getKey(), false, goalArrayList);
            if (isGoal) {
                ++numberOfGoals;
            }
        }

        return numberOfGoals;
    }

    private boolean LoadPref(String key, boolean defVal, ArrayList<Goal> goalArrayList) {

        boolean pref = sharedPref.getBoolean(key, defVal);
        if (pref) {
            goalArrayList.add(new Goal_MindfulEating());
            return true;
        }
        return false;
    }


}
