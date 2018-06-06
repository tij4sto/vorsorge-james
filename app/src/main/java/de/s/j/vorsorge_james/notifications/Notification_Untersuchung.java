package de.s.j.vorsorge_james.notifications;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.singleChildViewActivity.SingleChildView;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

final class Notification_Untersuchung extends NotificationBuilder {

    private KindBenoetigteUntersuchungMap untersuchungMap;
    private LinkedList<DbKindDatensatz> kinder;

    public Notification_Untersuchung(@NonNull Context context, KindBenoetigteUntersuchungMap untersuchungMap) {
        super(context);
        this.untersuchungMap = untersuchungMap;
        super.setup();
    }

    @Override
    protected void setupSubClass() {
        setTicker("" + untersuchungMap.size());
        setSmallIcon(R.drawable.ic_launcher_background);
        setContentTitle("Es stehen Untersuchungen an.");

        int color = context.getResources().getColor(R.color.orange);
        setColor(color);

        kinder = new LinkedList<>(untersuchungMap.keySet());
        Log.d("MyAlarm", "Anzahl Kinder mit Untersuchungen: " + kinder.size());
        if(kinder.size() == 1){

            for(DbKindDatensatz kind : kinder){
                List<DbUntersuchungDatensatz> untersuchungen = untersuchungMap.get(kind);
                Log.d("MyAlarm", "Anzahl Untersuchungen für " + kind.getName() + ": " + untersuchungen.size());
                if(untersuchungen.size() == 1){
                    setContentText(kind.getName() + " hat eine Untersuchung am " + untersuchungen.get(0));
                } else {
                    setContentText(kind.getName() + " hat Untersuchungen");
                }
            }
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            for(DbKindDatensatz kind : kinder){
                List<DbUntersuchungDatensatz> untersuchungenDesKindes = untersuchungMap.get(kind);
                if(untersuchungenDesKindes.size() == 1){
                    DbUntersuchungDatensatz untersuchung = untersuchungenDesKindes.get(0);
                    inboxStyle.addLine(kind.getName() + " hat Untersuchung " + untersuchung.getName() + " " +DbUntersuchungTyp.getZeitraumString(untersuchung, kind));
                } else {
                    inboxStyle.addLine(kind.getName() + " hat Untersuchungen:");
                    for(DbUntersuchungDatensatz untersuchung : untersuchungenDesKindes){
                        Log.d("MyAlarm", "Anzahl Untersuchungen für " + kind.getName() + ": " + untersuchungenDesKindes.size());
                        inboxStyle.addLine(untersuchung.getName() + " " + DbUntersuchungTyp.getZeitraumString(untersuchung, kind));
                    }
                }


            }
            setStyle(inboxStyle);


            setContentText("Kinder haben Untersuchungen");
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
