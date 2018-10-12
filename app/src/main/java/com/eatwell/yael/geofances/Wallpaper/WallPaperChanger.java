package com.eatwell.yael.geofances.Wallpaper;

import android.app.WallpaperManager;
import android.graphics.Bitmap;

import com.eatwell.yael.geofances.Firebase_Utils.FirebaseStorageImpl;
import com.eatwell.yael.geofances.UserPreferences.User;
//import com.squareup.picasso.Picasso;

import java.io.IOException;


public class WallPaperChanger /*implements WallPaperChangerI*/ {

    public static void ChangeWallPaper(String wPpUrl) {

        User user = User.getInstance();

        /*Bitmap wPpBitmap =Picasso
                .load(wPpUrl)
                .get();
*/
        Bitmap wPpBitmap = FirebaseStorageImpl.getImageBitmap(wPpUrl);


        try {
            WallpaperManager.getInstance(user.getmContext()).setBitmap(wPpBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
