package com.eatwell.yael.geofances.Utils;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.eatwell.yael.geofances.NotificationsUtils.NotificationChooser;
import com.eatwell.yael.geofances.UserPreferences.User;
import com.eatwell.yael.geofances.WallpaperUtils.WallPaperChooser;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class GeofenceTrasitionService extends IntentService {

    private static final String TAG = GeofenceTrasitionService.class.getSimpleName();
    private static User user = User.getInstance();


    public GeofenceTrasitionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // Handling errors
        if ( geofencingEvent.hasError() ) {
            String errorMsg = getErrorString(geofencingEvent.getErrorCode() );
            Log.e( TAG, errorMsg );
            return;
        }

        //get transition
        int geoFenceTransition = geofencingEvent.getGeofenceTransition();
        // Check if the transition type is of interest
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ) {
            // Get the geofence that were triggered
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();


            //check if user requests to receive notifications
            //TODO- better to extract boolean from user and change from preference only there?
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(user.getmContext());

            boolean isNotificationsOn = user.isNotificationsOn();
            boolean isWallPaperOn = user.isWallPaperHome() || user.isWallPaperLock();

            if (isNotificationsOn) {
                // Send notification
                NotificationChooser.SendNextNotification(getGeofenceTrasition(geoFenceTransition),
                        getTriggeringGeofences(geoFenceTransition, triggeringGeofences));
            }

            if (isWallPaperOn) {
                WallPaperChooser.ChangeWallPaper(getGeofenceTrasition(geoFenceTransition),
                        getTriggeringGeofences(geoFenceTransition, triggeringGeofences));
            }
        }
    }


    private String getTriggeringGeofences(int geoFenceTransition, List<Geofence> triggeringGeofences) {
        // get the ID of each geofence triggered
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for ( Geofence geofence : triggeringGeofences ) {
            triggeringGeofencesList.add( geofence.getRequestId() );
        }

        return triggeringGeofencesList + " ";
    }

    private String getGeofenceTrasition (int geofenceTransition) {

        if ( geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER)
            return "GEOFENCE_TRANSITION_ENTER";
        //if ( geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT )
        return "GEOFENCE_TRANSITION_EXIT ";
    }


    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }
}