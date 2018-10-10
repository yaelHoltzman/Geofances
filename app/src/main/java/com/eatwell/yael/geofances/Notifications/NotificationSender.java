package com.eatwell.yael.geofances.Notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;


public class NotificationSender extends IntentService {

    private static final String TAG = NotificationSender.class.getSimpleName();
    private static Context context;// = user.getContext(); TODO initialize context here

    public static final int NOTIFICATION_ID = 0;

    public NotificationSender() {
        super(TAG);
    }

    public static void sendNotification( String msg ) {
        Log.i(TAG, "sendNotification: " + msg );

        // Intent to start the main Activity
        Intent notificationIntent = com.eatwell.yael.geofances.UI.Notification.makeNotificationIntent(
                context, msg
        );

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(com.eatwell.yael.geofances.UI.Notification.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        // Creating and sending Notification
        NotificationManager notificatioMng =
                (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
        notificatioMng.notify(
                NOTIFICATION_ID,
                createNotification(msg, notificationPendingIntent));

    }

    // Create notification
    private static Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        //TODO deprecated API fix
        notificationBuilder
                .setSmallIcon(R.drawable.ic_action_location)
                .setColor(Color.RED)
                .setContentTitle(msg)
                .setContentText("Geofence Notification!")
                .setContentIntent(notificationPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }


    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
