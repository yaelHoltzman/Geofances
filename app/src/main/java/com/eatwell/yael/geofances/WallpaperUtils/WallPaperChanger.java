package com.eatwell.yael.geofances.WallpaperUtils;

import android.app.WallpaperManager;
import android.graphics.Bitmap;

import com.eatwell.yael.geofances.Firebase_Utils.FirebaseStorageImpl;
import com.eatwell.yael.geofances.UserPreferences.User;
//import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.function.Consumer;


public class WallPaperChanger /*implements WallPaperChangerI*/ {

    public static void ChangeWallPaper(String wPpUrl) {

        final User user = User.getInstance();

        FirebaseStorageImpl.getImageBitmap(wPpUrl, new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) {

                try {
                    WallpaperManager.getInstance(user.getmContext()).setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




    }
}
