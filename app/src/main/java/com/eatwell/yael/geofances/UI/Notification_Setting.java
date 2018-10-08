package com.eatwell.yael.geofances.UI;

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

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.Utils.LocationSelector;

public class Notification_Setting extends AppCompatActivity {

    public static class NotificationSettings extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.notifications_setting);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            LinearLayout v = (LinearLayout) super.onCreateView(inflater, container, savedInstanceState);

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
                .replace(android.R.id.content, new NotificationSettings())
                .commit();
        PreferenceManager.setDefaultValues(this, R.xml.notifications_setting, false);
    }


    public static Context GetGSContext() {
        return context;
    }
}
