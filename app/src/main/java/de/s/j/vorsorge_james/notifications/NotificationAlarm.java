package de.s.j.vorsorge_james.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
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

        List<DbKindHatUntersuchungDatensatz> alleTermine = dataSource.getKindHatUntersuchungListe();
        List<DbKindHatUntersuchungDatensatz> appointmentsToAnnounce = new LinkedList<>();

        for(DbKindHatUntersuchungDatensatz termin : alleTermine){
            try {
                Calendar today = Calendar.getInstance();
                Calendar appointment = getDateOfAppointment(termin.getTermin());
                Calendar reminderDate = getDateOfReminder(termin.getTermin());

            /*    SimpleDateFormat stringToDate = new SimpleDateFormat("MM/dd/yyyy");
                Date appointment = stringToDate.parse(termin.getTermin());
                Log.d("MyAlarm", termin.getTermin() + " parsed to: " + appointment.toString());
               // Calendar

                Calendar minusAppointment = Calendar.getInstance();
                minusAppointment.setTime(appointment);
                minusAppointment.add(Calendar.YEAR, 2000);
                Log.d("MyAlarm", "As Calendar: " + minusAppointment.getTime().toString());

                minusAppointment.add(Calendar.DAY_OF_MONTH, -3);*/
                boolean reminderIsDue = today.after(reminderDate) && today.before(appointment);
                if(reminderIsDue){
                    appointmentsToAnnounce.add(termin);
                }
                Log.d("MyAlarm", "Today: " + today.getTime().toString() + " is after " + reminderDate.getTime().toString() + " == " +reminderIsDue);
            } catch (ParseException e){
                Log.d("MyAlarm", "Exception: " + e.getMessage());

            }

        }
        Log.d("MyAlarm", "Number of Appointments to announce: " + appointmentsToAnnounce.size());
        if(appointmentsToAnnounce.size() > 0){
            notificationHelper.sendAnnounceAppointmentsNotification(appointmentsToAnnounce);
        }
        Log.d("NotificationAlarm", "NotificationAlarm triggered");

        // notificationHelper.sendSampleNotification(dataSource.toString());
        // notificationHelper.sendAnotherSample("Another notification");
    }

    private static Calendar getDateOfAppointment(String string) throws ParseException {
        SimpleDateFormat stringToDate = new SimpleDateFormat("MM/dd/yyyy");
        Date appointment = stringToDate.parse(string);
        Calendar appointmentAsCalendar = Calendar.getInstance();
        appointmentAsCalendar.setTime(appointment);
        appointmentAsCalendar.add(Calendar.YEAR, 2000);
        return appointmentAsCalendar;
    }

    private static Calendar getDateOfReminder(String string) throws ParseException {
        Calendar appointment = getDateOfAppointment(string);
        appointment.add(Calendar.DAY_OF_YEAR, -3);
        return appointment;
    }
}
