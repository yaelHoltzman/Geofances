package com.eatwell.yael.geofances.Firebase_Utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;
import com.eatwell.yael.geofances.WallpaperUtils.WallPaperChanger;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;


public class FirebaseStorageImpl {

    private static final String TAG = FirebaseStorageImpl.class.getSimpleName();
    private static StorageReference mStorageRef;
    private static Bitmap[] bmp = new Bitmap[1];
    private static com.google.firebase.storage.FirebaseStorage storage = com.google.firebase.storage.FirebaseStorage.getInstance();
    private static String selectedUrl;
    private static User user;


    public static String getWallpaperUrl(String key) {
        if (storage == null) {
            storage = com.google.firebase.storage.FirebaseStorage.getInstance();
        }
        if (user == null) {
            user = User.getInstance();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference imageUrlsRef = database.getReference(user.getmContext().getResources().getString(R.string.database_imageurl_rootName)).child(key).child("Images");

        imageUrlsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long numOfEntries = dataSnapshot.getChildrenCount();
                int randomNumber = new Random().nextInt((int)numOfEntries);

                selectedUrl = dataSnapshot.child(user.getmContext().getResources().getString(randomNumber)).getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "error retrieving image url from storage" + databaseError.getCode());
            }
        });

        return selectedUrl;
    }
}