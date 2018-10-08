package com.eatwell.yael.geofances.Notifications;

public interface NotificationChooserI {
    void SendNextNotification(String location, String geofenceTransition);
}
