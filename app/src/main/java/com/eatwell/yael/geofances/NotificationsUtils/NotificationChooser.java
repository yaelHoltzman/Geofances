package com.eatwell.yael.geofances.NotificationsUtils;

import com.eatwell.yael.geofances.Firebase_Utils.FirebaseMessagingService;
import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;

public class NotificationChooser {

    static private FirebaseMessagingService fbms = new FirebaseMessagingService();

    public static void SendNextNotification(String location, String geofenceTransition) {
        //create instance of user
          User user = User.getInstance();

        //get the appropriate notification according to user's goals
        if (user.getNumberOfGoals() == 0) {
            return;
        }

        Goal userGoal = user.getGoal();
        String notification  = userGoal.GetNextNotification(location, geofenceTransition);

        //send notification to user
        fbms.sendNotification(user.getmContext().getString(R.string.pref_title_new_message_notifications), notification);
    }
}
