package com.eatwell.yael.geofances.WallpaperUtils;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import com.eatwell.yael.geofances.UserPreferences.User;
import java.io.IOException;



public class WallPaperChanger {

    static void ChangeWallPaper(Bitmap bitmap) {

        final User user = User.getInstance();

        try {
            WallpaperManager.getInstance(user.getmContext()).setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
