package de.s.j.vorsorge_james.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;

/**
 * Custon alarm, that triggers a notification to be built.
 */
public class NotificationAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);

        DbAccess dataSource = new DbAccess(context);
        List<DbKindDatensatz> allKinder = dataSource.getKindListe();
        KindBenoetigteUntersuchungMap allBenoetigteUntersuchungen = new KindBenoetigteUntersuchungMap();
        for(DbKindDatensatz kind : allKinder){
            List<DbUntersuchungDatensatz> benoetigteUntersuchungen = dataSource.getBenoetigteUntersuchungenOhneTermine(kind);

            if(benoetigteUntersuchungen.size() > 0){
                allBenoetigteUntersuchungen.put(kind, benoetigteUntersuchungen);
            }
        }
        if(allBenoetigteUntersuchungen.size() > 0){
            notificationHelper.sendBenoetigteUntersuchungNotification(allBenoetigteUntersuchungen);
        }

        Log.d("NotificationAlarm", "NotificationAlarm triggered");

        // notificationHelper.sendSampleNotification(dataSource.toString());
        // notificationHelper.sendAnotherSample("Another notification");
        Log.d("NotificationAlarm", dataSource.getKindHatUntersuchungListe().toString());
    }
}
