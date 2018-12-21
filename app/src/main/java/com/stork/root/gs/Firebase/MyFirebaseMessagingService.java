package com.stork.root.gs.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.stork.root.gs.MainActivity;
import com.stork.root.gs.MySingleton;
import com.stork.root.gs.R;

/**
 * Created by sonka on 29.10.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
            String title, message, img_url;
            title = remoteMessage.getNotification().getTitle();
            message = remoteMessage.getNotification().getBody();
            img_url = remoteMessage.getData().get("image");

            sendNotification(message,title,img_url);

           /* Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setContentTitle(title);
            notificationBuilder.setContentText(message);
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setSound(sounduri);
            notificationBuilder.setStyle(new NotificationCompat.InboxStyle(notificationBuilder));
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setSmallIcon(R.drawable.ic_notification_icon);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0,notificationBuilder.build());
            */
    }

        private void sendNotification(String messageBody, String title, String img_url) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            final NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_notification_icon)
                            .setContentTitle(title)
                            .setContentText(messageBody)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);
            if (!img_url.equals("")) {
                ImageRequest imageRequest = new ImageRequest(img_url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

                    }
                }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                MySingleton.getInstance(this).addToRequestQueue(imageRequest);

            }else{
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
            }
        }
}
