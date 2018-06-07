package de.s.j.vorsorge_james.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

final class Notification_Termin extends NotificationBuilder {

    protected static final String channelId = "vorsorge-james-nf-channel-appointments";
    private List<DbKindHatUntersuchungDatensatz> appointments;

    Notification_Termin(@NonNull Context context, List<DbKindHatUntersuchungDatensatz> appointments) {
        super(context, channelId);
        this.appointments = appointments;
        super.setup();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected NotificationChannel getNotificationChannel() {
        NotificationChannel channel_termine =
                new NotificationChannel(channelId, "Termine", NotificationManager.IMPORTANCE_DEFAULT);
        return channel_termine;
    }

    @Override
    protected void setupSubClass() {
        if(appointments.size() == 1){
            setupForJustOneAppointment();
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            for(DbKindHatUntersuchungDatensatz appointment : appointments){

            }

            setTicker("Es steht Termine an");
            setContentText("Noti");
            setContentTitle("Termine stehen");
        }

        setSmallIcon(R.drawable.vorsorge_james_icon_small);


    }

    /**
     * Sets the notification up for displaying a single appointment.
     */
    private void setupForJustOneAppointment(){
        for (DbKindHatUntersuchungDatensatz appointment: appointments) { // Accessing appointment, only one loop
            String dateOfAppointment = appointment.getTermin();
            dateOfAppointment = dateOfAppointment.replace('/', '.');
            String screeningName = DbUntersuchungTyp.getUntersuchungTypByID(appointment.getIdUnterschung()).getName();
            String childName = new DbAccess(context).getKindDatensatzById(appointment.getIdKind()).getName();

            StringBuilder builder = new StringBuilder("Am ");
            builder.append(dateOfAppointment);
            builder.append(": ");
            builder.append(screeningName);
            builder.append(" für ");
            builder.append(childName);
            setContentText(builder.toString());

            builder = new StringBuilder("Nächster Termin: ");
            builder.append(screeningName);
            builder.append(" für ");
            builder.append(childName);
            builder.append(" am ");
            builder.append(dateOfAppointment);
            setTicker(builder.toString());

            setContentTitle("Ein Termin steht bevor.");
        }
    }

    @Override
    protected Intent makeOpenActivityIntent() {
        return super.makeOpenActivityIntent();
    }
}
