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
import com.eatwell.yael.geofances.UserPreferences.User;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = FirebaseMessagingService.class.getSimpleName();
    private static NotificationManager notifManager;
    private static NotificationChannel mChannel;
    private final String CHANNEL_ID = User.getInstance().getmContext().getResources().getString(R.string.channel_id);
    private User user;
    private static int notificationNumber = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        user = User.getInstance();

        //check user preference regarding receiving notifications
        if (!user.isNotificationsOn()) {
            return;
        }

        String message = remoteMessage.getNotification().getBody();
        String title = remoteMessage.getNotification().getTitle();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        SendNotification(title, message + remoteMessage.getData());
    }

    public void SendNotification(String title, String message) {
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        Log.d(TAG, "SendNotification");

        if (user == null) {
            user = User.getInstance();
        }

        if (notifManager == null) {
            notifManager = (NotificationManager) user.getmContext().getSystemService
                    (Context.NOTIFICATION_SERVICE);
        }

        //create an intent for the activity which will be presented when opening notification
        intent = new Intent(user.getmContext()/*this*/, com.eatwell.yael.geofances.UI.Notification.class);

        //Build.VERSION.SDK_INT- The SDK version of the software currently running on this hardware device
        //To avoid executing that block of code on devices older than Android 8.0 (because of channel):
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            if (mChannel == null) {
                mChannel = new NotificationChannel
                        (CHANNEL_ID, title, importance);
                mChannel.setDescription(message);
                mChannel.enableVibration(user.isVibration());
                mChannel.setVibrationPattern(new long[]
                        {100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.setName(user.getmContext().getResources().getString(R.string.channel_name));
                notifManager.createNotificationChannel(mChannel);
            }
        }

        builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(user.getmContext(), 0, intent, 0);
        builder.setContentTitle(title)
        .setSmallIcon(R.drawable.yael_element)
        .setContentText(message)
        .setDefaults(Notification.DEFAULT_ALL)
        .setAutoCancel(true)
//        .setLargeIcon(BitmapFactory.decodeResource
  //              (getResources(), R.drawable.yael_element))
    //    .setBadgeIconType(R.drawable.yael_element)
        .setContentIntent(pendingIntent)
        .setSound(RingtoneManager.getDefaultUri
                (RingtoneManager.TYPE_NOTIFICATION))
        .setVibrate(new long[]{100, 200, 300, 400,
                500, 400, 300, 200, 400});


        Notification notification = builder.build();
        notifManager.notify(++notificationNumber, notification);
    }
}
