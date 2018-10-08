package com.eatwell.yael.geofances.Notifications;

import com.eatwell.yael.geofances.Goals.Goal;
import com.eatwell.yael.geofances.UserP.User;

public class NotificationChooser implements NotificationChooserI {

    private User user;
    private NotificationSender ns;

    public NotificationChooser() {
        user = new User();
        ns = new NotificationSender();
    }

    public void SendNextNotification(String location, String geofenceTransition) {
        //get the appropriate notification according to user's goals
        Goal userGoal = user.GetGoal();
        String notification  = userGoal.GetNextNotification(location, geofenceTransition);

        //send notification to user
        ns.sendNotification(notification);
    }
}
