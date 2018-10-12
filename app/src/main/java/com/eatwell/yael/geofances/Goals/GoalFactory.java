package com.eatwell.yael.geofances.Goals;

import java.util.ArrayList;



public class GoalFactory {

    private static ArrayList<String> preferences;

    public GoalFactory() {

        preferences = new ArrayList<>();

        preferences.add("Mindful");
        preferences.add("Weightloss");
    }

    public static Goal getGoal (String goalKey) {
            if (goalKey.equals("Mindful")) {
                return new GoalMindfulEating();
            }
            else if (goalKey.equals("Weightloss")) {
                return new GoalWeightLoss();
            }
            return null;
        }

    public static ArrayList<String> getAllGoalPreferences() {
        return preferences;
    }

}
