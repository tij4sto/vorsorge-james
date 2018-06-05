package de.s.j.vorsorge_james.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import static android.content.Context.ALARM_SERVICE;

public final class NotificationAlarmManager {

    private AlarmManager manager;
    private Context context;

    public NotificationAlarmManager(Context context) {
        this.manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        this.context = context;
        Log.d("NotificationAlarm", "NotificationAlarmManager initialized");
    }

    public void start(){
        PendingIntent alarmIntent = makeAlarmIntent();
        manager.setRepeating(AlarmManager.RTC_WAKEUP, FireingTime.getTime().getTimeInMillis(), (1000 * 30), alarmIntent);
        Log.d("NotificationAlarm", "NotificationAlarmManager started");
        enableBootReciever(true);
    }

    /**
     * Makes a PendingIntent that will access
     * NotificationAlarm.class to trigger a notification.
     * @return PendingIntent that will access
     * NotificationAlarm.class to trigger a notification.
     */
    private PendingIntent makeAlarmIntent(){
        Intent intent = new Intent(context, NotificationAlarm.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        return alarmIntent;
    }

    public void cancel(){
        PendingIntent pendingIntent = makeAlarmIntent();
        Log.d("NotificationAlarm", "NotificationAlarmManager stopped");
        manager.cancel(pendingIntent);
        enableBootReciever(false);

    }

    /**
     * Enables or disables the BootReceiver.
     * @param enable Shall the BootReceier start after the device boots
     *               to start a new alarm?
     */
    private void enableBootReciever(boolean enable){
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        if(enable){
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
            Log.d("NotificationAlarm", "Enabled BootReceiver");
        } else {
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            Log.d("NotificationAlarm", "Disabled BootReceiver");
        }

    }
}
