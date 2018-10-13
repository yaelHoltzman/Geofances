package com.eatwell.yael.geofances.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.Goals.GoalMindfulEating;
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
        Goal goal = new GoalMindfulEating();


        WallPaperChooser.ChangeWallPaper("Home", "GEOFENCE_TRANSITION_EXIT");
    }
}
