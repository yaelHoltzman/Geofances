package com.eatwell.yael.geofances.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.eatwell.yael.geofances.R;

public class Notification extends AppCompatActivity {

    private static final String NOTIFICATION_MSG = "";
    private static String notificationMsgRecieved = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView msg = findViewById(R.id.msgTextView);
        msg.setText(notificationMsgRecieved);
    }







    /*public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent( context, Notification.class );
        intent.putExtra( NOTIFICATION_MSG, msg );
        notificationMsgRecieved = msg;
        return intent;
    }*/
}
