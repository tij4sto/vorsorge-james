package de.s.j.vorsorge_james.notifications;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.Set;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;

final class Notification_Untersuchung extends NotificationBuilder {

    private KindBenoetigteUntersuchungMap untersuchungMap;

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

        Set<DbKindDatensatz> kinder = untersuchungMap.keySet();
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
            for(DbKindDatensatz kind : kinder){
                List<DbUntersuchungDatensatz> untersuchungen = untersuchungMap.get(kind);
                Log.d("MyAlarm", "Anzahl Untersuchungen für " + kind.getName() + ": " + untersuchungen.size());
            }
            setContentText("Kinder haben Untersuchungen");
        }

    }
}
