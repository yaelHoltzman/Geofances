package com.eatwell.yael.geofances;

public interface NotificationChooserI {
    void SendNextNotification(String location, String geofenceTransition);
}
