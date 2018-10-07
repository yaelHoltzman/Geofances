package com.eatwell.yael.geofances.Firebase_Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.eatwell.yael.geofances.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationManager notifManager;
    private NotificationChannel mChannel;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        String message = remoteMessage.getNotification().getBody();
        String title = remoteMessage.getNotification().getTitle();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
            //if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
              //  scheduleJob();
            //} else {
                // Handle message within 10 seconds
              //  handleNow();
            //}


        // Check if message contains a notification payload.
        //if (remoteMessage.getNotification() != null) {
          //  Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        //}

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        SendNotification(title, message);

        }

        public void SendNotification(String title, String message) {
            Intent intent;
            PendingIntent pendingIntent;
            NotificationCompat.Builder builder;

            if (notifManager == null) {
                notifManager = (NotificationManager) getSystemService
                        (Context.NOTIFICATION_SERVICE);
            }

            intent = new Intent(this, com.eatwell.yael.geofances.UI.Notification.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                if (mChannel == null) {
                    NotificationChannel mChannel = new NotificationChannel
                            ("0", title, importance);
                    mChannel.setDescription(message);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]
                            {100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notifManager.createNotificationChannel(mChannel);
                }
                builder = new NotificationCompat.Builder(this, "0");

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                builder.setContentTitle(title)  // flare_icon_30
                        .setSmallIcon(R.mipmap.ic_launcher) // required
                        .setContentText(message)  // required
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource
                                (getResources(), R.mipmap.app_logo))
                        .setBadgeIconType(R.mipmap.app_logo)
                        .setContentIntent(pendingIntent)
                        .setSound(RingtoneManager.getDefaultUri
                                (RingtoneManager.TYPE_NOTIFICATION))
                        .setVibrate(new long[]{100, 200, 300, 400,
                                500, 400, 300, 200, 400});
            } else {

                builder = new NotificationCompat.Builder(this);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                builder.setContentTitle(title)
                        .setSmallIcon(R.mipmap.app_logo) // required
                        .setContentText(message)  // required
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setSound(RingtoneManager.getDefaultUri
                                (RingtoneManager.TYPE_NOTIFICATION))
                        .setVibrate(new long[]{100, 200, 300, 400, 500,
                                400, 300, 200, 400})
                        .setPriority(Notification.PRIORITY_HIGH);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification notification = builder.build();
                notifManager.notify(0, notification);
            }
        }
}


   // private void scheduleJob() {
        //TODO - later implement
    //}

    //private void handleNow() {
        //TODO - later implement
    //}
//}