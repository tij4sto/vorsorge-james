package de.s.j.vorsorge_james.notifications;

import android.content.Context;
import android.support.annotation.NonNull;
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

public final class NotificationAccess {

    public final static int daysBeforeAppointmentToRemind = 3;

    private NotificationHelper notificationHelper;
    private DbAccess dataSource;

    public NotificationAccess(Context context){
        notificationHelper = new NotificationHelper(context);
        dataSource = new DbAccess(context);
    }

    /**
     * Shows a notification, if a screening (Untersuchung) for any child is due.
     */
    public void showScreeningsNotification(){
        KindBenoetigteUntersuchungMap allBenoetigteUntersuchungen = getScreeningsToAnnounce();
        if(allBenoetigteUntersuchungen.size() > 0){
            notificationHelper.sendBenoetigteUntersuchungNotification(allBenoetigteUntersuchungen);
        }
    }

    /**
     * Returns a map of children, who have a screening due in the near future.
     * @return map of children, who have a screening due in the near future
     */
    @NonNull
    private KindBenoetigteUntersuchungMap getScreeningsToAnnounce() {
        List<DbKindDatensatz> allKinder = dataSource.getKindListe();
        KindBenoetigteUntersuchungMap allBenoetigteUntersuchungen = new KindBenoetigteUntersuchungMap();
        for(DbKindDatensatz kind : allKinder){
            List<DbUntersuchungDatensatz> benoetigteUntersuchungen = dataSource.getBenoetigteUntersuchungenOhneTermine(kind);

            if(benoetigteUntersuchungen.size() > 0){
                allBenoetigteUntersuchungen.put(kind, benoetigteUntersuchungen);
            }
        }
        return allBenoetigteUntersuchungen;
    }

    /**
     * Shows a notification, if a appointment (Termin) for any child is due in a few days.
     */
    public void showAppointmentsNotification(){
        List<DbKindHatUntersuchungDatensatz> appointmentsToAnnounce = getAppointmentsToAnnounce();
        Log.d("MyAlarm", "Number of Appointments to announce: " + appointmentsToAnnounce.size());
        if(appointmentsToAnnounce.size() > 0){
            notificationHelper.sendAnnounceAppointmentsNotification(appointmentsToAnnounce);
        }
        Log.d("NotificationAlarm", "NotificationAlarm triggered");
    }

    @NonNull
    private List<DbKindHatUntersuchungDatensatz> getAppointmentsToAnnounce() {
        List<DbKindHatUntersuchungDatensatz> alleTermine = dataSource.getKindHatUntersuchungListe();
        List<DbKindHatUntersuchungDatensatz> appointmentsToAnnounce = new LinkedList<>();

        for(DbKindHatUntersuchungDatensatz termin : alleTermine){
            try {
                Calendar today = Calendar.getInstance();
                Calendar appointment = getDateOfAppointment(termin.getTermin());
                Calendar reminderDate = getDateOfReminder(termin.getTermin());

                boolean reminderIsDue = today.after(reminderDate) && today.before(appointment);
                if(reminderIsDue){
                    appointmentsToAnnounce.add(termin);
                }
                Log.d("MyAlarm", "Today: " + today.getTime().toString() + " is after " + reminderDate.getTime().toString() + " == " +reminderIsDue);
            } catch (ParseException e){
                Log.d("MyAlarm", "Exception: " + e.getMessage());

            }
        }
        return appointmentsToAnnounce;
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
        appointment.add(Calendar.DAY_OF_YEAR, -daysBeforeAppointmentToRemind);
        return appointment;
    }

}
