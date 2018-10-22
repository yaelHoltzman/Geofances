package com.eatwell.yael.geofances.Goals;

import java.util.ArrayList;

public class GoalFactory {

    private ArrayList<String> preferences;

    public GoalFactory() {
        setPreferences();
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

    private void setPreferences () {
        preferences = new ArrayList<>();
        preferences.add("Mindful");
        preferences.add("Weightloss");
    }
}
