package com.eatwell.yael.geofances.Goals;

import android.content.Context;

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;

import java.util.ArrayList;



public class GoalFactory {

    private static ArrayList<String> preferences;
    private static User user = User.getInstance();

    public GoalFactory() {


    }

    public Goal getGoal (String goalKey) {
            if (goalKey.equals("Mindful")) {
                return new GoalMindfulEating();
            }
            else if (goalKey.equals("Weightloss")) {
                return new GoalWeightLoss();
            }
            return null;
        }

    public  ArrayList<String> getAllGoalPreferences() {

        return preferences;
    }

    public void setPreferences () {
        preferences = new ArrayList<>();
        preferences.add("Mindful");
        preferences.add("Weightloss");
    }

}
