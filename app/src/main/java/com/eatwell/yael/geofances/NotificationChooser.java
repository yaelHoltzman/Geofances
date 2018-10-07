package com.eatwell.yael.geofances;

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
