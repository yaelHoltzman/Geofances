package com.eatwell.yael.geofances.Firebase_Utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.eatwell.yael.geofances.UserPreferences.User;
import com.google.firebase.storage.StorageReference;

import java.util.function.Consumer;


public class FirebaseStorageImpl {

    private static final String TAG = FirebaseStorageImpl.class.getSimpleName();
    private static StorageReference mStorageRef;
    private static Bitmap[] bmp = new Bitmap[1];
    private static  com.google.firebase.storage.FirebaseStorage storage = com.google.firebase.storage.FirebaseStorage.getInstance();

    public static void getImageBitmap(String url, Consumer<Bitmap> func) {

        Log.i(TAG, "getImageBitmap");

        //TODO-> make sure its initialized correctly and remove this
        if (storage == null ) {
            storage = com.google.firebase.storage.FirebaseStorage.getInstance();
        }

        if (bmp == null) {
            bmp = new Bitmap[1];
        }

        //get reference to image url
        mStorageRef = storage.getReferenceFromUrl(url);

        //args must be final in anonymous class
        final Consumer<Bitmap> funcFinal = func;
        User user = User.getInstance();

        Glide.with(user.getmContext())
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        funcFinal.accept(resource);
                    }
                });
    }
}
