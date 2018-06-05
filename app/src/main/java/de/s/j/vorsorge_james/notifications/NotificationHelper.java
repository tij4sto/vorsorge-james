package de.s.j.vorsorge_james.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.childListViewActivity.ChildListViewActivity;

final class NotificationHelper {

    Context activity;

    public NotificationHelper(Context activity){
        this.activity = activity;
    }

    public void sendSampleNotification(String message){
        sendSampleNotification(activity, message);
    }

    public void sendSampleNotification(Context activityContext, String message){
        final int uniqueID = 34657;

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(activityContext, "vorsorge-james-notification-channel");
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        notificationBuilder.setTicker(message);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setContentTitle("Sample Notification");
        notificationBuilder.setContentText(message);

        Intent openActivityIntent = new Intent(activityContext, ChildListViewActivity.class);
        PendingIntent accessAppIntent =
                PendingIntent.getActivity(activityContext, 0, openActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(accessAppIntent);

        NotificationManager notificationManager = (NotificationManager) activityContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID, notificationBuilder.build());
        Log.d("MyAlarm", "Notification built");
    }

}
