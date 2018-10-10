package com.eatwell.yael.geofances.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.Utils.LocationSelector;

public class Notification extends LocationSelector {

    private static final String NOTIFICATION_MSG = "";

    public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent( context, Notification.class );
        intent.putExtra( NOTIFICATION_MSG, msg );
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }
}
