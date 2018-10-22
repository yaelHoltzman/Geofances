package com.eatwell.yael.geofances.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eatwell.yael.geofances.Firebase_Utils.FirebaseMessagingService;
//import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;
import com.eatwell.yael.geofances.WallpaperUtils.WallPaperChooser;


public class TestActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        user = User.getInstance();
        user.setmContext(getApplicationContext());

        Button buttonWpp = findViewById(R.id.button);
        Button buttonNotification = findViewById(R.id.button2);


        buttonWpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Goal goal = user.getGoal();

                WallPaperChooser.ChangeWallPaper("Home", "GEOFENCE_TRANSITION_EXIT", new WallPaperChooser.CallbacksWallPaperChooser() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(TestActivity.this, "wallpaper changed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(String msg) {
                        Toast.makeText(TestActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buttonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = User.getInstance();
                user.setmContext(getApplicationContext());

                FirebaseMessagingService fmsg = new FirebaseMessagingService();
                fmsg.sendNotification("testTitle", "test message");
            }
        });

    }
}
