package com.eatwell.yael.geofances.WallpaperUtils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.UserPreferences.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class WallPaperChooser {

    private static User user;

    public interface CallbacksWallPaperChooser {
        void onSuccess();

        void onFail(String msg);
    }

    public static void ChangeWallPaper(String location, String geofenceTransition, final CallbacksWallPaperChooser listener) {

        if (user == null) {
            user = User.getInstance();
        }

        if (user.getNumberOfGoals() == 0) {
            user.loadGoals();
            //return;
        }

        final Goal userGoal = user.getGoal();
        String wppUrl = userGoal.GetWallPaperUrl(location, geofenceTransition);

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(wppUrl);

        mStorageRef/*.child(wppUrl)*/.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                com.squareup.picasso.Target target = new com.squareup.picasso.Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        WallPaperChanger.ChangeWallPaper(bitmap);
                        listener.onSuccess();
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        listener.onFail(errorDrawable.toString());
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };

                Picasso.with(user.getmContext()).load(uri).into(target);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFail(e.getMessage());
            }
        });
    }
}