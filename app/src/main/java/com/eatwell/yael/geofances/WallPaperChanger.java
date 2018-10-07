package com.eatwell.yael.geofances;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import java.io.IOException;

/*
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
*/


public class WallPaperChanger implements WallPaperChangerI {

    private final Context context;
    private MyStorageActivity storage;

    public WallPaperChanger(Context context) {
        this.context = context;
        storage = new MyStorageActivity();
    }


    @Override
    public void ChangeWallPaper(String wPpUrl) {

        setWallPaperFromUrl(wPpUrl);
    }

    private void setWallPaperFromUrl(String imagePageURL) {

        Bitmap wPpBitmap = storage.getImageBitmap(imagePageURL);

        try {
            WallpaperManager.getInstance(context).setBitmap(wPpBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    private void setWallpaperFromUrl(String imagePageURL) {
        Glide.with(context)
                .load(imagePageURL)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        try {
                            WallpaperManager.getInstance(context).setBitmap(resource);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

*/
}
