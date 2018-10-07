package com.eatwell.yael.geofances.Goal;

import android.util.Pair;

import com.eatwell.yael.geofances.Goal.Goal;

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
        wallPPUrls.put(keyPair5, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/weightLoss1.jpg?alt=media&token=40929684-756e-485b-bf4f-421a0ca50b7d");
        Pair<String, String> keyPair6 = new Pair<>("Home", "GEOFENCE_TRANSITION_EXIT");
        wallPPUrls.put(keyPair6, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/weightLoss2.jpg?alt=media&token=6568cee1-c824-43c2-96ce-3c056dc6adb9");
        Pair<String, String> keyPair7 = new Pair<>("Work", "GEOFENCE_TRANSITION_ENTER");
        wallPPUrls.put(keyPair7, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/weightLoss3.jpg?alt=media&token=326f9f85-1523-4804-b83f-d7c539f0e7f2");
        Pair<String, String> keyPair8 = new Pair<>("Work", "GEOFENCE_TRANSITION_EXIT");
        wallPPUrls.put(keyPair8, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/weightLoss4.jpg?alt=media&token=d7f86776-5884-4f79-8a3c-f3765b473d60");

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
