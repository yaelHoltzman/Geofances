package com.eatwell.yael.geofances.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.Goals.GoalMindfulEating;
import com.eatwell.yael.geofances.NotificationsUtils.NotificationChooser;
import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;
import com.eatwell.yael.geofances.WallpaperUtils.WallPaperChooser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TestActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        user = User.getInstance();
        user.setmContext(getApplicationContext());

        Button buttonWpp = (Button)findViewById(R.id.button);

        buttonWpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goal goal = user.getGoal();

                WallPaperChooser.ChangeWallPaper("Work", "GEOFENCE_TRANSITION_EXIT", new WallPaperChooser.CallbacksWallPaperChooser() {
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

    }
}
