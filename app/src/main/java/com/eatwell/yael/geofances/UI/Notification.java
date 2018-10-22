package com.eatwell.yael.geofances.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.eatwell.yael.geofances.R;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String notificationMsgRecieved;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent intent = getIntent();
        notificationMsgRecieved = intent.getStringExtra("msg");

        TextView msg = findViewById(R.id.msgTextView);
        msg.setText(notificationMsgRecieved);
    }
}
