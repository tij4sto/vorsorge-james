package de.s.j.vorsorge_james.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;

final class NotificationHelper {

    private Context activity;
    private NotificationManager notificationManager;

    NotificationHelper(Context activity){
        this.activity = activity;
        notificationManager =  (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    void sendBenoetigteUntersuchungNotification (KindBenoetigteUntersuchungMap allBenoetigteUntersuchungen){
        final int uniqueID = 67539650;
        NotificationBuilder builder = new Notification_Untersuchung(activity, allBenoetigteUntersuchungen);
        setChannel(builder);
        notificationManager.notify(uniqueID, builder.build());
    }

    void sendAnnounceAppointmentsNotification(List<DbKindHatUntersuchungDatensatz> appointmentsToAnnounce){
        final int uniqueID = 9878965;
        NotificationBuilder builder = new Notification_Termin(activity, appointmentsToAnnounce);
        setChannel(builder);
        notificationManager.notify(uniqueID, builder.build());
    }

    private void setChannel(NotificationBuilder builder){
        if (Build.VERSION.SDK_INT >= 26 ){
            notificationManager.createNotificationChannel(builder.getNotificationChannel());
        }
    }

    private NotificationCompat.Builder makeBuilder(){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(activity, "vorsorge-james-notification-channel");
        return builder;
    }

}
