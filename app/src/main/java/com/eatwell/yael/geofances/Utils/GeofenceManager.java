package com.eatwell.yael.geofances.Utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.eatwell.yael.geofances.UserPreferences.User;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class GeofenceManager extends AppCompatActivity {

    private static final String TAG = GeofenceManager.class.getSimpleName();
    private GeofencingClient mGeofencingClient;

    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "Home"; //TODO this will be the type of geofence
    private static final float GEOFENCE_RADIUS = 100.0f; // in meters
    private PendingIntent geofencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;
    private static int numberOfGeofences = 0;
    private final String KEY_GEOFENCE_LAT = "GEOFENCE LATITUDE";
    private final String KEY_GEOFENCE_LON = "GEOFENCE LONGITUDE";
    private static User user = User.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //user = User.getInstance();

        Log.d(TAG, "init mGeofencingClient");
        mGeofencingClient = LocationServices.getGeofencingClient(user.getmContext());
    }

    // Create a Geofence Request
    private GeofencingRequest createGeofenceRequest( Geofence geofence ) {
        Log.d(TAG, "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger( GeofencingRequest. INITIAL_TRIGGER_DWELL)
                .addGeofence( geofence )
                .build();
    }

    public float GetGeofenceRadius() {
        return GEOFENCE_RADIUS;
    }


    private PendingIntent createGeofencePendingIntent() {
        Log.d(TAG, "createGeofencePendingIntent");
        if ( geofencePendingIntent!= null )
            return geofencePendingIntent;

        Intent intent = new Intent(user.getmContext(), GeofenceTrasitionService.class);

        geofencePendingIntent= PendingIntent.getService(user.getmContext(), GEOFENCE_REQ_CODE, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    // Create a Geofence
    private Geofence createGeofence(LatLng latLng, float radius ) {
        Log.d(TAG, "createGeofence");

        //mGeofenceList.add(
        return new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(/*entry.getKey()*/GEOFENCE_REQ_ID)

                .setCircularRegion(
                        latLng.latitude,
                        latLng.longitude,
                        radius
                        //Constants.GEOFENCE_RADIUS_IN_METERS
                )
                .setExpirationDuration(/*Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS*/GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request, Context context) {
        Log.d(TAG, "addGeofence");

        if (mGeofencingClient == null) {
            if (user == null) {
                user = User.getInstance();
            }
            mGeofencingClient = LocationServices.getGeofencingClient(user.getmContext());
        }


        Permission_Checker checker = new Permission_Checker();
        if (checker.checkPermission(context))
            mGeofencingClient.
                    addGeofences(request, createGeofencePendingIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Geofences added
                            ++numberOfGeofences;
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
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
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Geofences removed
                        user.removeLocation(geofenceId);
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to remove geofences
                        Log.e(TAG, "clearGeofence failed");
                    }
                });
    }



    public void clearAllGeofence() {
        mGeofencingClient.removeGeofences(createGeofencePendingIntent())
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Geofences removed
                        //TODO- ?, ;
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to remove geofences
                        Log.e(TAG, "clearGeofence failed");
                    }
                });
    }

    // Start Geofence creation process
    public void startGeofence(Marker geoFenceMarker, Context context) {
        Log.i(TAG, "startGeofence()");

        if( geoFenceMarker != null ) {
            Geofence geofence = createGeofence( geoFenceMarker.getPosition(), GEOFENCE_RADIUS );
            GeofencingRequest geofenceRequest = createGeofenceRequest( geofence );
            addGeofence( geofenceRequest, context );
            saveGeofence(geoFenceMarker);
        } else {
            Log.e(TAG, "Geofence marker is null");
        }
    }


    // Saving GeoFence marker with prefs mng
    public void saveGeofence(Marker geoFenceMarker) {
        Log.d(TAG, "saveGeofence()");

        User user = User.getInstance();
        Context context = user.getmContext();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putLong( KEY_GEOFENCE_LAT, Double.doubleToRawLongBits( geoFenceMarker.getPosition().latitude ));
        editor.putLong( KEY_GEOFENCE_LON, Double.doubleToRawLongBits( geoFenceMarker.getPosition().longitude ));
        editor.apply();
    }

    public LatLng recoverGeofence() {
        Log.d(TAG, "recoverGeofence");
        LatLng latLngRecovered;

        User user = User.getInstance();
        Context context = user.getmContext();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        if ( sharedPref.contains( KEY_GEOFENCE_LAT ) && sharedPref.contains( KEY_GEOFENCE_LON )) {
            double lat = Double.longBitsToDouble( sharedPref.getLong( KEY_GEOFENCE_LAT, -1 ));
            double lon = Double.longBitsToDouble( sharedPref.getLong( KEY_GEOFENCE_LON, -1 ));

            latLngRecovered = new LatLng( lat, lon );
            return latLngRecovered;
        }

        return new LatLng(0, 0);
    }

}
