package com.eatwell.yael.geofances.Firebase_Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.eatwell.yael.geofances.UserPreferences.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

//TODO- should it extend Service/ IntentService instead?
public class FirebaseStorageImpl extends Activity {

    private static final String TAG = FirebaseStorageImpl.class.getSimpleName();
    private static StorageReference mStorageRef;
    private static Bitmap[] bmp = new Bitmap[1];
    private static  com.google.firebase.storage.FirebaseStorage storage = com.google.firebase.storage.FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_storage);

        // storage initialization
        storage = com.google.firebase.storage.FirebaseStorage.getInstance();

        bmp = new Bitmap[1];

    }


    public static Bitmap getImageBitmap(String url, Consumer<Bitmap> func) {

        if (storage == null ) {
            storage = com.google.firebase.storage.FirebaseStorage.getInstance();
        }

        if (bmp == null) {
            bmp = new Bitmap[1];
        }

        //max size of storage item
        final long ONE_MEGABYTE = 1024 * 1024;

        //get reference to image url
        mStorageRef = storage.getReferenceFromUrl(url);

        //get bytes from storage reference and put it into Bitmap
        /*mStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true; //inMutable-> its pixels can be changes
                bmp[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                int errorCode = ((StorageException) exception).getErrorCode();
                String errorMessage = exception.getMessage();
                Log.d(TAG, errorMessage + errorCode);
            }
        });
        */

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
        return bmp[0];
    }
        /*
        Glide.with(user.getmContext())
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        bmp[0] = resource;
                    }
                });*/
}


/*

    Glide.with(user.getmContext())
                //.using(new FirebaseImageLoader())
                .asBitmap()
                .load(gsReference)
                .into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                bmp[0] = resource;
            }
        });*/
//}


        /*
        Glide.with(user.getmContext())
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        bmp[0] = resource;
                    }
                });
        return bmp[0];
    }
}
*/
