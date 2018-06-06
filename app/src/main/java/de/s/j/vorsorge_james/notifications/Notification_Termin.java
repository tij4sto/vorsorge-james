package de.s.j.vorsorge_james.notifications;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import de.s.j.vorsorge_james.R;

final class Notification_Termin extends NotificationBuilder {

    public Notification_Termin(@NonNull Context context) {
        super(context);
       // notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background);
       /* notificationBuilder.setTicker(message);
        notificationBuilder.setContentTitle("Another Sample Notification");
        notificationBuilder.setContentText(message);*/
    }

    @Override
    protected void setupSubClass() {

    }

    @Override
    protected Intent makeOpenActivityIntent() {
        return null;
    }
}
