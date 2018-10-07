package com.eatwell.yael.geofances;

import com.google.android.gms.maps.model.LatLng;

public interface UserI {
    Goal GetGoal();
    LatLng GetLocation(String locationName);
    void SetLocation(LatLng latLng, String locationName);
    boolean IsGettingNotifications();
}
