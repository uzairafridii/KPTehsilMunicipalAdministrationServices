package com.uzair.kptehsilmunicipaladministrationservices.AppModules.FirebaseMessagingService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.uzair.kptehsilmunicipaladministrationservices.R;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "Channel Name";
    public static final int NOTIFICATION_ID = 1;
    private String title, body, click_action;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        title = remoteMessage.getNotification().getTitle();
        body = remoteMessage.getNotification().getBody();
        click_action = remoteMessage.getNotification().getClickAction();
        Log.d("notifictionbody", "onMessageReceived: " + title + "\n" + body);

        showNotification(this , title , body , click_action);

    }


    public void showNotification(Context context, String title, String body , String click_action)
    {

        // add a title and body to the notification custom view
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
        contentView.setImageViewResource(R.id.notificationImage, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.notificationTitle, title);
        contentView.setTextViewText(R.id.notificationBody, body);


        // set click intent action
        Intent intent = new Intent(click_action);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // the version of device is oreo then create channel for notification
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        // set the default sound and set notification view , data
         Uri defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContent(contentView)
                .setSound(defSoundUri)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});


        // notify the notification through notification manager class method notify
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
