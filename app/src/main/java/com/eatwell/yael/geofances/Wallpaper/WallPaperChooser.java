package com.eatwell.yael.geofances.Wallpaper;

import android.support.v4.app.Fragment;

import com.eatwell.yael.geofances.Goal.Goal;
import com.eatwell.yael.geofances.User.User;

public class WallPaperChooser extends Fragment implements WallPaperChooserI {

    private User user = new User();
    private WallPaperChanger wpC = new WallPaperChanger(getContext());

    @Override
    public void ChangeWallPaper(String location, String geofenceTransition) {
        Goal userGoal = user.GetGoal();
        String wppUrl = userGoal.GetWallPaperUrl(location, geofenceTransition);
        wpC.ChangeWallPaper(wppUrl);
    }
}
