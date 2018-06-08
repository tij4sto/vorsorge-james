package de.s.j.vorsorge_james.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.singleChildViewActivity.SingleChildView;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

final class Notification_Untersuchung extends NotificationBuilder {

    protected static final String channelId = "vorsorge-james-nf-channel-screenings";

    private KindBenoetigteUntersuchungMap untersuchungMap;
    private LinkedList<DbKindDatensatz> kinder;
    private LinkedList<DbUntersuchungDatensatz> untersuchungen;

    public Notification_Untersuchung(@NonNull Context context, KindBenoetigteUntersuchungMap untersuchungMap) {
        super(context, channelId);
        this.untersuchungMap = untersuchungMap;
        super.setup();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected NotificationChannel getNotificationChannel() {
        NotificationChannel channel_untersuchungen =
                new NotificationChannel(channelId, "Untersuchungen", NotificationManager.IMPORTANCE_DEFAULT);
        return channel_untersuchungen;
    }

    @Override
    protected void setupSubClass() {
        setTicker("Es werden demnächst " + untersuchungMap.size() + " Untersuchungen fällig.");
        setSmallIcon(R.drawable.vorsorge_james_icon_small);


        int color = context.getResources().getColor(R.color.orange);
        setColor(color);

        kinder = new LinkedList<>(untersuchungMap.keySet());
        Log.d("MyAlarm", "Anzahl Kinder mit Untersuchungen: " + kinder.size());
        if(kinder.size() == 1){
            for(DbKindDatensatz kind : kinder){

                List<DbUntersuchungDatensatz> untersuchungen = untersuchungMap.get(kind);
                Log.d("MyAlarm", "Anzahl Untersuchungen für " + kind.getName() + ": " + untersuchungen.size());
                setTicker("Es werden demnächst " + untersuchungen.size() + " Untersuchungen fällig.");
                if(untersuchungen.size() == 1){
                    setContentTitle("Eine Untersuchung für " + kind.getName() +  " wird fällig.");
                    setContentText("zwischen " + DbUntersuchungTyp.getZeitraumString(untersuchungen.get(0), kind));
                } else {
                    setContentText("Neue Untersuchungen:");
                    setContentTitle(untersuchungen.size() + " Untersuchungen für " + kind.getName() +  " werden fällig.");
                }
            }
        } else {
            setContentTitle("Untersuchungen werden fällig.");
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            int numberOfScreenings = 0;
            for(DbKindDatensatz kind : kinder){
                List<DbUntersuchungDatensatz> untersuchungenDesKindes = untersuchungMap.get(kind);
                numberOfScreenings += untersuchungenDesKindes.size();
                if(untersuchungenDesKindes.size() == 1){
                    DbUntersuchungDatensatz untersuchung = untersuchungenDesKindes.get(0);
                    inboxStyle.addLine("Für " + kind.getName() + " wird Untersuchung fällig:");
                    inboxStyle.addLine("   " +untersuchung.getName() + " " + DbUntersuchungTyp.getZeitraumString(untersuchung, kind));
                } else {
                    inboxStyle.addLine("Für " + kind.getName() + " werden Untersuchungen fällig:");
                    for(DbUntersuchungDatensatz untersuchung : untersuchungenDesKindes){
                        Log.d("MyAlarm", "Anzahl Untersuchungen für " + kind.getName() + ": " + untersuchungenDesKindes.size());
                        inboxStyle.addLine("   " +untersuchung.getName() + " " + DbUntersuchungTyp.getZeitraumString(untersuchung, kind));
                    }
                }
            }
            setStyle(inboxStyle);
            setTicker("Es werden demnächst " + numberOfScreenings + " Untersuchungen fällig.");
            setContentText(untersuchungMap.size() + " Untersuchungen werden fällig.");
        }
    }

    @Override
    protected Intent makeOpenActivityIntent() {
        Log.d("MyAlarm", "Kinder.size() == " + kinder.size());
        if(kinder.size() == 1){
            Intent intent = new Intent(super.context, SingleChildView.class);
            intent.putExtra("id", ""+ kinder.getFirst().getId());
            return intent;
        }
        return super.makeOpenActivityIntent();
    }
}
