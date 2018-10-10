package com.eatwell.yael.geofances.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;
import com.eatwell.yael.geofances.Wallpaper.WallPaperChanger;
import com.eatwell.yael.geofances.Wallpaper.WallPaperChooser;

public class TestActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        user = User.getInstance();
        user.setmContext(getApplicationContext());
        WallPaperChooser.ChangeWallPaper("Home", "GEOFENCE_TRANSITION_EXIT");
    }
}
