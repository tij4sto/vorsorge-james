package de.s.j.vorsorge_james.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import de.s.j.vorsorge_james.activities.childListViewActivity.ChildListViewActivity;

abstract class NotificationBuilder extends NotificationCompat.Builder {

    protected Context context;

    NotificationBuilder(@NonNull Context context, String channelId) {
        super(context, channelId);
        this.context = context;
    }

    protected final void setup(){
        setupSubClass();
        setWhen(System.currentTimeMillis());
        setAutoCancel(true);
        setContentIntent(makeAccessAppIntent());
    }

    protected abstract NotificationChannel getNotificationChannel();

    protected abstract void setupSubClass();

    protected Intent makeOpenActivityIntent(){
        Intent openActivityIntent = new Intent(context, ChildListViewActivity.class);
        return openActivityIntent;
    }

    private PendingIntent makeAccessAppIntent(){
        Intent openActivityIntent = makeOpenActivityIntent();
        PendingIntent accessAppIntent =
                PendingIntent.getActivity(context, 0, openActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return accessAppIntent;
    }
}
