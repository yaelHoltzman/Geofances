package com.eatwell.yael.geofances.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //first activity updates activity context in user singleton
        User user = User.getInstance();
        user.setmContext(getApplicationContext());

        //if this is the app's first run, start from "start" activity
        if (user.isFirstRun()) {

            //show start activity
            startActivity(new Intent(Home.this, StartActivity.class));
            Toast.makeText(Home.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }

        //TODO- should I pass it into onResume?
        user.setFirstRun(false);
        setContentView(R.layout.activity_home);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}


