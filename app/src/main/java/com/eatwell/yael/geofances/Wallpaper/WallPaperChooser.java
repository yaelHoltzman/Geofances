package com.eatwell.yael.geofances.Wallpaper;

import android.support.v4.app.Fragment;

import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.UserPreferences.User;

public class WallPaperChooser extends Fragment /*implements WallPaperChooserI*/ {

    private static User user;

    public static void ChangeWallPaper(String location, String geofenceTransition) {

        if (user == null) {
            user = User.getInstance();
        }

        Goal userGoal = user.GetGoal();
        String wppUrl = userGoal.GetWallPaperUrl(location, geofenceTransition);
        WallPaperChanger.ChangeWallPaper(wppUrl);
    }
}