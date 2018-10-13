package com.eatwell.yael.geofances.WallpaperUtils;

import android.support.v4.app.Fragment;

import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.UserPreferences.User;

public class WallPaperChooser {

    private static User user;

    public static void ChangeWallPaper(String location, String geofenceTransition) {

        if (user == null) {
            user = User.getInstance();
        }

        if (user.getNumberOfGoals() == 0) {
            return;
        }

        Goal userGoal = user.GetGoal();
        String wppUrl = userGoal.GetWallPaperUrl(location, geofenceTransition);
        WallPaperChanger.ChangeWallPaper(wppUrl);
    }
}