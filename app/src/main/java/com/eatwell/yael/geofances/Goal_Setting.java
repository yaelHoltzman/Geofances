package com.eatwell.yael.geofances;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Goal_Setting extends AppCompatActivity {

    public static class GoalSettings extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.goal_setting);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            LinearLayout v = (LinearLayout) super.onCreateView(inflater, container, savedInstanceState);

            Button btn = new Button(getActivity().getApplicationContext());
            btn.setText("Next");

            v.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Goal_Setting.GetGSContext(), LocationSelector.class);
                    startActivity(intent);
                }
            });

            return v;
        }

    }

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_goal__setting);
        context = this.getApplicationContext();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new GoalSettings())
                .commit();
        PreferenceManager.setDefaultValues(this, R.xml.goal_setting, false);
        context = getApplicationContext();
    }


    public static Context GetGSContext() {
        return context;
    }

}


