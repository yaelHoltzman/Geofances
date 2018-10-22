package com.eatwell.yael.geofances.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        User user = User.getInstance();
        user.setmContext(getApplicationContext());
        if (user.isFirstRun()) {
            user.setFirstRun(false);
        }

        TextView testText = /*(TextView)*/findViewById(R.id.testTextView);
        testText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTest();
            }
        });
    }

    private void goToTest() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}