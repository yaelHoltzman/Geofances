package com.eatwell.yael.geofances.Notification;

public interface NotificationChooserI {
    void SendNextNotification(String location, String geofenceTransition);
}
