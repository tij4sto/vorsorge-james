package de.s.j.vorsorge_james.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import de.s.j.vorsorge_james.activities.childListViewActivity.ChildListViewActivity;

abstract class NotificationBuilder extends NotificationCompat.Builder {

    protected static final String channelId = "vorsorge-james-notification-channel";
    protected Context context;

    public NotificationBuilder(@NonNull Context context) {
        super(context, channelId);
        this.context = context;
    }

    protected final void setup(){
        setupSubClass();
        setWhen(System.currentTimeMillis());
        setAutoCancel(true);
        setContentIntent(makeAccessAppIntent());
    }

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
