package com.eatwell.yael.geofances.Utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.eatwell.yael.geofances.UserPreferences.User;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class GeofenceManager {

    private static final String TAG = GeofenceManager.class.getSimpleName();
    private static int numberOfGeofences = 0;

    private GeofencingClient mGeofencingClient;
    private String GEOFENCE_REQ_ID;
    private PendingIntent geofencePendingIntent;
    private User user;
    private Activity mActivity;


    public GeofenceManager(Activity mActivity, String geofenceId) {
        this.mActivity = mActivity;
        user = User.getInstance();
        GEOFENCE_REQ_ID = /*"Home"*/geofenceId;
        mGeofencingClient = LocationServices.getGeofencingClient(user.getmContext());
        Log.d(TAG, "init mGeofencingClient");
    }

    // Create a Geofence Request
    private GeofencingRequest createGeofenceRequest( Geofence geofence ) {
        Log.d(TAG, "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger( GeofencingRequest. INITIAL_TRIGGER_EXIT)
                .addGeofence( geofence )
                .build();
    }


    private PendingIntent createGeofencePendingIntent() {
        final int GEOFENCE_REQ_CODE = 0;

        Log.d(TAG, "createGeofencePendingIntent");
        if ( geofencePendingIntent!= null )
            return geofencePendingIntent;

        Intent intent = new Intent(user.getmContext(), GeofenceTrasitionService.class);

        geofencePendingIntent= PendingIntent.getService(user.getmContext(), GEOFENCE_REQ_CODE, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }


    // Create a Geofence
    private Geofence createGeofence(LatLng latLng) {
        Log.d(TAG, "createGeofence");

        final float GEOFENCE_RADIUS = 100.0f; // in meters
        final long GEO_DURATION = 60 * 60 * 1000 * 24 * 7;

        return new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(/*entry.getKey()*/GEOFENCE_REQ_ID)

                .setCircularRegion(
                        latLng.latitude,
                        latLng.longitude,
                        GEOFENCE_RADIUS
                        //Constants.GEOFENCE_RADIUS_IN_METERS
                )
                .setExpirationDuration(/*GEOFENCE_EXPIRATION_IN_MILLISECONDS*/GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request, Context context) {
        Log.d(TAG, "addGeofence");

        if (PermissionsChecker.checkLocationPermission(context))
            mGeofencingClient.
                    addGeofences(request, createGeofencePendingIntent())
                    .addOnSuccessListener(mActivity, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Geofences added
                            ++numberOfGeofences;
                            Log.d(TAG, "current geofence number:" + numberOfGeofences);
                        }
                    })
                    .addOnFailureListener(mActivity, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to add geofences
                            Log.e(TAG, "addGeofence error " + e.getMessage());
                        }
                    });
    }

    // Clear Geofence
    public void clearGeofence (final String geofenceId) {
        List<String> geoFenceToRemove = new ArrayList<>();
        geoFenceToRemove.add(geofenceId);

        mGeofencingClient.removeGeofences(geoFenceToRemove)
                .addOnSuccessListener(mActivity, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Geofences removed
                        user.removeLocation(geofenceId);
                    }
                })
                .addOnFailureListener(mActivity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to remove geofences
                        Log.e(TAG, "clearGeofence failed");
                    }
                });
    }


/*
    public void clearAllGeofence() {
        mGeofencingClient.removeGeofences(createGeofencePendingIntent())
                .addOnSuccessListener(mActivity, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Geofences removed
                        //TODO, ;
                    }
                })
                .addOnFailureListener(mActivity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to remove geofences
                        Log.e(TAG, "clearGeofence failed");
                    }
                });
    }
*/


    // Start Geofence creation process
    public void startGeofence(Marker geoFenceMarker, Context context) {
        Log.i(TAG, "startGeofence()");

        if( geoFenceMarker != null ) {
            Geofence geofence = createGeofence( geoFenceMarker.getPosition());
            GeofencingRequest geofenceRequest = createGeofenceRequest( geofence );
            addGeofence( geofenceRequest, context );
            saveGeofence(geoFenceMarker);
        } else {
            Log.e(TAG, "Geofence marker is null");
        }
    }


    // Saving GeoFence marker with prefs mng
    private void saveGeofence(Marker geoFenceMarker) {
        Log.d(TAG, "saveGeofence()");

        User user = User.getInstance();
        user.addLocation(GEOFENCE_REQ_ID, new LatLng(geoFenceMarker.getPosition().latitude, geoFenceMarker.getPosition().longitude));
    }
}
