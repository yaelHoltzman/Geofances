package com.eatwell.yael.geofances.Goals;

public interface Goal {

    String GetNextNotification(String location, String geofenceTransition);

    //TODO - later implement
    String GetWallPaperUrl(String location, String geofenceTransition);
}