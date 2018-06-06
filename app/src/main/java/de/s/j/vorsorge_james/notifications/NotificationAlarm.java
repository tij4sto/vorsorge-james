package de.s.j.vorsorge_james.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.ParseException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.hilfsklassen.DateFormatter;

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

        List<DbKindHatUntersuchungDatensatz> alleTermine = dataSource.getKindHatUntersuchungListe();
        for(DbKindHatUntersuchungDatensatz termin : alleTermine){
            Date dateAsString = new Date(termin.getTermin());
       /*     Log.d("MyAlarm", "Date as String: " + termin.getTermin());
            Log.d("MyAlarm", "Date of Appointment " + termin.getIdKind() + " " + dateAsString.toString());
            Log.d("MyAlarm", "Today: " + Calendar.getInstance().getTime().toString());*/
            //Date today = Date.from(Instant.);
            try {
                Calendar today = Calendar.getInstance();
                Calendar minusAppointment = DateFormatter.parseStringToCalendar(termin.getTermin());
                minusAppointment.add(Calendar.DAY_OF_MONTH, -3);
                boolean reminderIsDue = DateFormatter.isAfterDay(today, minusAppointment);
                Log.d("MyAlarm", "Today: " + today.getTime().toString() + " is after " + minusAppointment.getTime().toString() + " == " +reminderIsDue);
            } catch (ParseException e){
                Log.d("MyAlarm", "Exception: " + e.getMessage());

            }

        }

        Log.d("NotificationAlarm", "NotificationAlarm triggered");

        // notificationHelper.sendSampleNotification(dataSource.toString());
        // notificationHelper.sendAnotherSample("Another notification");
    }
}
