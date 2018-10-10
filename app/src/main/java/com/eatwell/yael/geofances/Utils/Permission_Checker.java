package com.eatwell.yael.geofances.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Permission_Checker extends AppCompatActivity {
    private static final String TAG = Permission_Checker.class.getSimpleName();

    private static final int REQ_PERMISSION = 999;

    // Check for permission to access Location
    public static boolean checkPermission(Context context) {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }

    // Asks for permission
    public static void askPermission(Activity activity) {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(activity,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                REQ_PERMISSION
        );
    }

    //TODO- implement this in the calling activity?
    // Verify user's response of the permission requested
    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ( requestCode ) {
            case REQ_PERMISSION: {
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    // Permission granted
                    Log.w(TAG, "permissionsGranted");

                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }*/

    // App cannot work without the permissions
    private static void permissionsDenied() {
        Log.w(TAG, "permissionsDenied()");
        // TODO close app and warn user
    }

}

