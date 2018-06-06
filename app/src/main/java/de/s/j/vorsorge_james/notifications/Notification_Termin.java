package de.s.j.vorsorge_james.notifications;

import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import de.s.j.vorsorge_james.R;

final class Notification_Termin extends NotificationBuilder {

    protected static final String channelId = "vorsorge-james-notification-channel";

    Notification_Termin(@NonNull Context context) {
        super(context, channelId);
       // notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background);
       /* notificationBuilder.setTicker(message);
        notificationBuilder.setContentTitle("Another Sample Notification");
        notificationBuilder.setContentText(message);*/
    }

    @Override
    protected NotificationChannel getNotificationChannel() {
        return null;
    }

    @Override
    protected void setupSubClass() {

    }

    @Override
    protected Intent makeOpenActivityIntent() {
        return null;
    }
}
