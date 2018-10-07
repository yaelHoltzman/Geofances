package com.eatwell.yael.geofances;

import android.util.Pair;

import java.util.HashMap;

public class Goal_WeightLoss implements Goal {

    private HashMap<Pair<String, String>, String> notifications;
    private HashMap<Pair<String, String>, String> wallPPUrls;

    Goal_WeightLoss()
    {
        notifications = new HashMap<>();
        wallPPUrls= new HashMap<>();

        Pair<String, String> keyPair1 = new Pair<>("Home", "GEOFENCE_TRANSITION_ENTER");
        notifications.put(keyPair1, "test1");
        Pair<String, String> keyPair2 = new Pair<>("Home", "GEOFENCE_TRANSITION_EXIT");
        notifications.put(keyPair2, "test2");
        Pair<String, String> keyPair3 = new Pair<>("Work", "GEOFENCE_TRANSITION_ENTER");
        notifications.put(keyPair3, "test3");
        Pair<String, String> keyPair4 = new Pair<>("Work", "GEOFENCE_TRANSITION_EXIT");
        notifications.put(keyPair4, "test4");


        Pair<String, String> keyPair5 = new Pair<>("Home", "GEOFENCE_TRANSITION_ENTER");
        wallPPUrls.put(keyPair5, "test1");
        Pair<String, String> keyPair6 = new Pair<>("Home", "GEOFENCE_TRANSITION_EXIT");
        wallPPUrls.put(keyPair6, "test2");
        Pair<String, String> keyPair7 = new Pair<>("Work", "GEOFENCE_TRANSITION_ENTER");
        wallPPUrls.put(keyPair7, "test3");
        Pair<String, String> keyPair8 = new Pair<>("Work", "GEOFENCE_TRANSITION_EXIT");
        wallPPUrls.put(keyPair8, "test4");

    }

    @Override
    public String GetNextNotification(String location, String geofenceTransition) {
        Pair<String, String> keyPair = new Pair<>(location, geofenceTransition);
        String newNotification = notifications.get(keyPair);
        if (newNotification == null) {
            //TODO log error
            //TODO - throw exception?
        }
        return newNotification;
    }

    @Override
    public String GetWallPaperUrl(String location, String geofenceTransition) {
        Pair<String, String> keyPair = new Pair<>(location, geofenceTransition);
        String wppUrl = wallPPUrls.get(keyPair);
        if (wppUrl == null)
        {
            //TODO log error
            //TODO - throw exception?
        }
        return wppUrl;
    }
}
