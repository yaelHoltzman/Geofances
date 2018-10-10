package com.eatwell.yael.geofances.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eatwell.yael.geofances.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isFirstRun= prefs.getBoolean(getString(R.string.isFirstRun), false);

        if (isFirstRun) {

            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).commit();

            //show start activity
            startActivity(new Intent(Home.this, StartActivity.class));
            Toast.makeText(Home.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }

        setContentView(R.layout.activity_home);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}


