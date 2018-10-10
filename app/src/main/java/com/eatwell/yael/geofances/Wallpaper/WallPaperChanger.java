package com.eatwell.yael.geofances.Wallpaper;

import android.app.WallpaperManager;
import android.graphics.Bitmap;

import com.eatwell.yael.geofances.Firebase_Utils.FirebaseStorageImpl;
import com.eatwell.yael.geofances.UserPreferences.User;

import java.io.IOException;


public class WallPaperChanger /*implements WallPaperChangerI*/ {

    public static void ChangeWallPaper(String wPpUrl) {

        Bitmap wPpBitmap = FirebaseStorageImpl.getImageBitmap(wPpUrl);
        User user = User.getInstance();

        try {
            WallpaperManager.getInstance(user.getmContext()).setBitmap(wPpBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
