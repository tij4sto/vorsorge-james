package de.s.j.vorsorge_james.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import de.s.j.vorsorge_james.database.DbAccess;

/**
 * Custon alarm, that triggers a notification to built.
 */
public class NotificationAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DbAccess dataSource = new DbAccess(context);
        Log.d("NotificationAlarm", "NotificationAlarm triggered");
        new NotificationHelper(context).sendSampleNotification(dataSource.toString());
        Log.d("NotificationAlarm", dataSource.getKindHatUntersuchungListe().toString());
    }
}
