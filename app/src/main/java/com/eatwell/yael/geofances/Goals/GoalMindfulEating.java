package com.eatwell.yael.geofances.Goals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;

import com.eatwell.yael.geofances.Firebase_Utils.FirebaseStorageImpl;
import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;

import java.util.HashMap;


public class GoalMindfulEating extends AppCompatActivity implements Goal {

    private static final String TAG = GoalMindfulEating.class.getSimpleName();
    private static String mGoalName;


    private HashMap<Pair<String, String>, String> notifications;
    private User user;


    @Override
    public String GetNextNotification(String location, String geofenceTransition) {
        Pair<String, String> keyPair = new Pair<>(location, geofenceTransition);
        String newNotification = notifications.get(keyPair);
        if (newNotification == null) {
            Log.e(TAG, "null new Notification");
            newNotification = " ";
        }
        return newNotification;
    }


    @Override
    public String GetWallPaperUrl(String location, String geofenceTransition) {

        user = User.getInstance();

        Intent intent = new Intent(/*GoalMindfulEating.this*/user.getmContext() ,FirebaseStorageImpl.class);
        user.getmContext().startService(intent);
        return " ";

        //return FirebaseStorageImpl.getWallpaperUrl(mGoalName + location + geofenceTransition);

        /*Pair<String, String> keyPair = new Pair<>(location, geofenceTransition);
        String wppUrl = wallPPUrls.get(keyPair);
        if (wppUrl == null) {
            Log.e(TAG, "null new wpp Url");
        }

        return wppUrl;*/
    }





    private HashMap<Pair<String, String>, String> wallPPUrls;

    GoalMindfulEating() {
        notifications = new HashMap<>();

        user = User.getInstance();
        mGoalName = user.getmContext().getString(R.string.preference1);

        Pair<String, String> keyPair1 = new Pair<>(user.getmContext().getResources().getString(R.string.home), user.getmContext().getResources().getString(R.string.geofence_enter));
        notifications.put(keyPair1, user.getmContext().getResources().getString(R.string.notification1));
        Pair<String, String> keyPair2 = new Pair<>(user.getmContext().getResources().getString(R.string.home), user.getmContext().getResources().getString(R.string.geofence_exit));
        notifications.put(keyPair2, user.getmContext().getResources().getString(R.string.notification2));
        Pair<String, String> keyPair3 = new Pair<>(user.getmContext().getResources().getString(R.string.work), user.getmContext().getResources().getString(R.string.geofence_enter));
        notifications.put(keyPair3, user.getmContext().getResources().getString(R.string.notification3));
        Pair<String, String> keyPair4 = new Pair<>(user.getmContext().getResources().getString(R.string.work), user.getmContext().getResources().getString(R.string.geofence_exit));
        notifications.put(keyPair4, user.getmContext().getResources().getString(R.string.notification4));


        wallPPUrls = new HashMap<>();

        Pair<String, String> keyPair5 = new Pair<>("Home", "GEOFENCE_TRANSITION_ENTER");
        wallPPUrls.put(keyPair5, /*ConfigHelper.getConfigValue(user.getmContext(), "MindfulEatingHomeGEOFENCE_TRANSITION_ENTER"));*/
                "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/Mindful1.jpg?alt=media&token=e5ea2288-df11-4c16-bbb2-34404acea7f6");
        Pair<String, String> keyPair6 = new Pair<>("Home", "GEOFENCE_TRANSITION_EXIT");
        wallPPUrls.put(keyPair6, /*ConfigHelper.getConfigValue(user.getmContext(), "MindfulEatingHomeGEOFENCE_TRANSITION_EXIT"));*/
                "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/Mindful2.jpg?alt=media&token=a831df41-71f0-4a63-9b82-30da5e1b6c94");
        Pair<String, String> keyPair7 = new Pair<>("Work", "GEOFENCE_TRANSITION_ENTER");
        wallPPUrls.put(keyPair7, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/Mindful3.jpg?alt=media&token=4f0db69d-48cd-4043-bdb7-c80429d0af65");
        Pair<String, String> keyPair8 = new Pair<>("Work", "GEOFENCE_TRANSITION_EXIT");
        wallPPUrls.put(keyPair8, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/Mindful4.jpg?alt=media&token=53386b53-f107-40ef-bc7f-cd4d65322b42");

    }
}


