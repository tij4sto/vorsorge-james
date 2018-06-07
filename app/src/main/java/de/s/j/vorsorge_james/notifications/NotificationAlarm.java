package de.s.j.vorsorge_james.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Custon alarm, that triggers notifications to be built.
 */
public class NotificationAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationAccess access = new NotificationAccess(context);
        access.showScreeningsNotification();
        access.showAppointmentsNotification();
    }


}
