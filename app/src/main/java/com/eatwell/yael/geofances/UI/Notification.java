package com.eatwell.yael.geofances.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.eatwell.yael.geofances.R;

public class Notification extends LocationSelector {

    private static final String NOTIFICATION_MSG = "";
    private static String notificationMsgRecieved;

    public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent( context, Notification.class );
        intent.putExtra( NOTIFICATION_MSG, msg );
        notificationMsgRecieved = msg;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView msg = findViewById(R.id.msgTextView);
        msg.setText(notificationMsgRecieved);
    }
}
