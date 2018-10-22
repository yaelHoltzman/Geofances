package com.eatwell.yael.geofances.UI;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eatwell.yael.geofances.R;

public class GoalSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new Settings.GoalPreferenceFragment())
                .commit();
        PreferenceManager.setDefaultValues(this, R.xml.goal_setting, false);
    }
}