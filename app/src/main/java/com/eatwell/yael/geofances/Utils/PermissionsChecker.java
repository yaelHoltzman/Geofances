package com.eatwell.yael.geofances.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class PermissionsChecker {
    private static final String TAG = PermissionsChecker.class.getSimpleName();

    private static final int REQ_PERMISSION = 999;

    // Check for permission to access Location
    public static boolean checkLocationPermission(Context context) {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }

    // Asks for permission
    public static void askLocationPermission(Activity activity) {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(activity,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                REQ_PERMISSION
        );
    }

    // App cannot work without the permissions
    private static void permissionsDenied() {
        Log.w(TAG, "permissionsDenied()");
        // TODO close app and warn user
    }

}

