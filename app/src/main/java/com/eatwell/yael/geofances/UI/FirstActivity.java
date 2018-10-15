package com.eatwell.yael.geofances.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //create user singleton object
        User user = User.getInstance();
        user.setmContext(getApplicationContext());

        user.setFirstRun(true); //TODO

        if (user.isFirstRun()) {
            //show start activity
            startActivity(new Intent(FirstActivity.this, StartActivity.class));
            Toast.makeText(FirstActivity.this, "Welcome", Toast.LENGTH_LONG)
                    .show();
        } else {
            //startActivity(new Intent(FirstActivity.this, Home.class));
            startActivity(new Intent(FirstActivity.this, TestActivity.class));
        }
    }
}