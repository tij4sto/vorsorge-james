package de.s.j.vorsorge_james.alarmManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.notifications.NotificationHelper;

public class MyAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "there", Toast.LENGTH_LONG);
        DbAccess dataSource = new DbAccess(context);
        Log.d("MyAlarm", "MyAlarm triggered");
        new NotificationHelper(context).sendSampleNotification(dataSource.toString());
        Log.d("MyAlarm", dataSource.getKindHatUntersuchungListe().toString());
    }
}
