package de.s.j.vorsorge_james.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.childListViewActivity.ChildListViewActivity;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;

final class NotificationHelper {

    private Context activity;
    private NotificationManager notificationManager;

    NotificationHelper(Context activity){
        this.activity = activity;
        notificationManager =  (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    void sendSampleNotification(String message){
        sendSampleNotification(activity, message);
    }

    void sendBenoetigteUntersuchungNotification (KindBenoetigteUntersuchungMap allBenoetigteUntersuchungen){
        final int uniqueID = 67539650;
        NotificationBuilder builder = new Notification_Untersuchung(activity, allBenoetigteUntersuchungen);
        notificationManager.notify(uniqueID, builder.build());
    }

    void sendSampleNotification(Context activityContext, String message){
        final int uniqueID = 34657;

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(activityContext, "vorsorge-james-notification-channel");
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        notificationBuilder.setTicker(message);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setContentTitle("Sample Notification");
        notificationBuilder.setContentText(message);



        notificationManager.notify(uniqueID, notificationBuilder.build());
        Log.d("MyAlarm", "Notification built");
    }

    public void sendAnotherSample(String message){
        final int uniqueID = 11112;

        NotificationBuilder notificationBuilder =
                new Notification_Termin(activity);


        notificationManager.notify(uniqueID, notificationBuilder.build());
        Log.d("MyAlarm", "Notification built");
    }

    private NotificationCompat.Builder makeBuilder(){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(activity, "vorsorge-james-notification-channel");
        return builder;
    }



}
