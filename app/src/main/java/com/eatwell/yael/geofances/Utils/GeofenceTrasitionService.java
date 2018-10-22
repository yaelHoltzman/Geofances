package com.eatwell.yael.geofances.Utils;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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
    private User user = User.getInstance();


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


            //check if user requests to receive notifications and change wallpaper
            boolean isNotificationsOn = user.isNotificationsOn();
            boolean isWallPaperOn = user.isWallPaperHome() || user.isWallPaperLock();

            if (isNotificationsOn) {
                NotificationChooser.SendNextNotification(getGeofenceTrasition(geoFenceTransition),
                        getTriggeringGeofences(triggeringGeofences));
            }

            if (isWallPaperOn) {
                WallPaperChooser.ChangeWallPaper(getGeofenceTrasition(geoFenceTransition),
                        getTriggeringGeofences(triggeringGeofences), new WallPaperChooser.CallbacksWallPaperChooser() {
                            @Override
                            public void onSuccess() {
                                Toast toast = new Toast(user.getmContext());
                                toast.setText("Wallpaper changed");
                                toast.show();
                            }

                            @Override
                            public void onFail(String msg) {
                                Log.e(TAG, "wallpaper change error");
                            }
                        });
            }
        }
    }


    private String getTriggeringGeofences(List<Geofence> triggeringGeofences) {
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