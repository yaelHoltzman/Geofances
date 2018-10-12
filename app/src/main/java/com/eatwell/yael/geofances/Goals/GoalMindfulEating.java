package com.eatwell.yael.geofances.Goals;

import android.util.Log;
import android.util.Pair;
import java.util.HashMap;


public class GoalMindfulEating implements Goal {

    private static final String TAG = GoalMindfulEating.class.getSimpleName();

    private HashMap<Pair<String, String>, String> notifications;
    private HashMap<Pair<String, String>, String> wallPPUrls;

    GoalMindfulEating()
    {
        notifications = new HashMap<>();
        wallPPUrls = new HashMap<>();

        Pair<String, String> keyPair1 = new Pair<>("Home", "GEOFENCE_TRANSITION_ENTER");
        notifications.put(keyPair1, "Don't forget to eat a healthy balanced meal");
        Pair<String, String> keyPair2 = new Pair<>("Home", "GEOFENCE_TRANSITION_EXIT");
        notifications.put(keyPair2, "Eat at least 5 vegetables per day :)");
        Pair<String, String> keyPair3 = new Pair<>("Work", "GEOFENCE_TRANSITION_ENTER");
        notifications.put(keyPair3, "Snacks are empty calories, when hungry- eat a balanced meal");
        Pair<String, String> keyPair4 = new Pair<>("Work", "GEOFENCE_TRANSITION_EXIT");
        notifications.put(keyPair4, "go excersice if you have time");

        //TODO read from config file or storage??

        Pair<String, String> keyPair5 = new Pair<>("Home", "GEOFENCE_TRANSITION_ENTER");
        wallPPUrls.put(keyPair5, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/Mindful1.jpg?alt=media&token=e5ea2288-df11-4c16-bbb2-34404acea7f6");
        Pair<String, String> keyPair6 = new Pair<>("Home", "GEOFENCE_TRANSITION_EXIT");
        wallPPUrls.put(keyPair6, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/Mindful2.jpg?alt=media&token=a831df41-71f0-4a63-9b82-30da5e1b6c94");
        Pair<String, String> keyPair7 = new Pair<>("Work", "GEOFENCE_TRANSITION_ENTER");
        wallPPUrls.put(keyPair7, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/Mindful3.jpg?alt=media&token=4f0db69d-48cd-4043-bdb7-c80429d0af65");
        Pair<String, String> keyPair8 = new Pair<>("Work", "GEOFENCE_TRANSITION_EXIT");
        wallPPUrls.put(keyPair8, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/Mindful4.jpg?alt=media&token=53386b53-f107-40ef-bc7f-cd4d65322b42");

    }

    @Override
    public String GetNextNotification(String location, String geofenceTransition) {
        Pair<String, String> keyPair = new Pair<>(location, geofenceTransition);
        String newNotification = notifications.get(keyPair);
        if (newNotification == null) {
            Log.e(TAG, "null new Notification");
            //TODO- anything else should be executed?
        }
        return newNotification;
    }


    @Override
    public String GetWallPaperUrl(String location, String geofenceTransition) {
        Pair<String, String> keyPair = new Pair<>(location, geofenceTransition);
        String wppUrl = wallPPUrls.get(keyPair);
        if (wppUrl == null) {
            Log.e(TAG, "null new wpp Url");
        }
        return wppUrl;
    }

}
