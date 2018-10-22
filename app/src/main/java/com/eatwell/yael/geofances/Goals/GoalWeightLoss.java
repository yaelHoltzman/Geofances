package com.eatwell.yael.geofances.Goals;

import android.util.Log;
import android.util.Pair;

import com.eatwell.yael.geofances.Firebase_Utils.FirebaseStorageImpl;
import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;

import java.util.HashMap;

public class GoalWeightLoss implements Goal {

    private static final String TAG = GoalWeightLoss.class.getSimpleName();
    private static String mGoalName;

    private HashMap<Pair<String, String>, String> notifications;
    private User user;
    private HashMap<Pair<String, String>, String> wallPPUrls;

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
        return FirebaseStorageImpl.getWallpaperUrl(mGoalName + location + geofenceTransition);
        /*Pair<String, String> keyPair = new Pair<>(location, geofenceTransition);
        String wppUrl = wallPPUrls.get(keyPair);
        if (wppUrl == null) {
            Log.e(TAG, "null new wpp Url");
        }*/
        //return wppUrl;
    }

    public GoalWeightLoss() {
        notifications = new HashMap<>();

        user = User.getInstance();
        mGoalName = user.getmContext().getString(R.string.preference2);

        Pair<String, String> keyPair1 = new Pair<>(user.getmContext().getResources().getString(R.string.home), user.getmContext().getResources().getString(R.string.geofence_enter));
        notifications.put(keyPair1, user.getmContext().getResources().getString(R.string.notification5));
        Pair<String, String> keyPair2 = new Pair<>(user.getmContext().getResources().getString(R.string.home), user.getmContext().getResources().getString(R.string.geofence_exit));
        notifications.put(keyPair2, user.getmContext().getResources().getString(R.string.notification6));
        Pair<String, String> keyPair3 = new Pair<>(user.getmContext().getResources().getString(R.string.work), user.getmContext().getResources().getString(R.string.geofence_enter));
        notifications.put(keyPair3, user.getmContext().getResources().getString(R.string.notification7));
        Pair<String, String> keyPair4 = new Pair<>(user.getmContext().getResources().getString(R.string.work), user.getmContext().getResources().getString(R.string.geofence_exit));
        notifications.put(keyPair4, user.getmContext().getResources().getString(R.string.notification8));





        wallPPUrls= new HashMap<>();
        Pair<String, String> keyPair5 = new Pair<>("Home", "GEOFENCE_TRANSITION_ENTER");
        wallPPUrls.put(keyPair5, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/weightLoss1.jpg?alt=media&token=40929684-756e-485b-bf4f-421a0ca50b7d");
        Pair<String, String> keyPair6 = new Pair<>("Home", "GEOFENCE_TRANSITION_EXIT");
        wallPPUrls.put(keyPair6, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/weightLoss2.jpg?alt=media&token=6568cee1-c824-43c2-96ce-3c056dc6adb9");
        Pair<String, String> keyPair7 = new Pair<>("Work", "GEOFENCE_TRANSITION_ENTER");
        wallPPUrls.put(keyPair7, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/weightLoss3.jpg?alt=media&token=326f9f85-1523-4804-b83f-d7c539f0e7f2");
        Pair<String, String> keyPair8 = new Pair<>("Work", "GEOFENCE_TRANSITION_EXIT");
        wallPPUrls.put(keyPair8, "https://firebasestorage.googleapis.com/v0/b/geofances-f9a17.appspot.com/o/weightLoss4.jpg?alt=media&token=d7f86776-5884-4f79-8a3c-f3765b473d60");
    }
}