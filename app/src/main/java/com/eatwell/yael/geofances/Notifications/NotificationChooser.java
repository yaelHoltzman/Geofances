package com.eatwell.yael.geofances.Notifications;

import android.support.v4.app.Fragment;

import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.UserPreferences.User;

public class NotificationChooser extends Fragment {

    static private User user;


    public static void SendNextNotification(String location, String geofenceTransition) {
        //TODO create instance of user
        if (user == null) {
            user = User.getInstance();
        }

        //get the appropriate notification according to user's goals
        Goal userGoal = user.GetGoal();
        String notification  = userGoal.GetNextNotification(location, geofenceTransition);

        //send notification to user
        NotificationSender.sendNotification(notification);
    }
}
