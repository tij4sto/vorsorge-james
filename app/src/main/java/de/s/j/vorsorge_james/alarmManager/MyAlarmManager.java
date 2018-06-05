package de.s.j.vorsorge_james.alarmManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import de.s.j.vorsorge_james.childListViewActivity.ChildListViewActivity;

import static android.content.Context.ALARM_SERVICE;

public final class MyAlarmManager {

    private AlarmManager manager;
    private Context context;

    public MyAlarmManager(Context context) {
        this.manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        this.context = context;
    }

    public void start(){
        Intent intent = new Intent(context, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, FireingTime.getTime().getTimeInMillis(), (1000 * 30), pendingIntent);
    }

    public void cancel(){
        Intent intent = new Intent(context, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        manager.cancel(pendingIntent);
    }
}
