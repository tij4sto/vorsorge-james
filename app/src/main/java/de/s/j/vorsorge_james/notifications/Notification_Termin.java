package de.s.j.vorsorge_james.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.singleChildViewActivity.SingleChildView;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

final class Notification_Termin extends NotificationBuilder {

    protected static final String channelId = "vorsorge-james-nf-channel-appointments";
    private LinkedList<DbKindHatUntersuchungDatensatz> appointments = new LinkedList<>();
    private DbAccess dataSource;

    Notification_Termin(@NonNull Context context, List<DbKindHatUntersuchungDatensatz> appointments) {
        super(context, channelId);
        this.dataSource = new DbAccess(context);
        this.appointments.addAll(appointments);
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
            setupForMultipleAppointments();
        }

        setSmallIcon(R.drawable.vorsorge_james_icon_small);
        int color = context.getResources().getColor(R.color.green);
        setColor(color);

    }

    /**
     * Sets the notification up for multiple appointments.
     */
    private void setupForMultipleAppointments() {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        ArrayList<String> childNames = new ArrayList<>();
        for(DbKindHatUntersuchungDatensatz appointment : appointments){
            String dateOfAppointment = formatDate(appointment.getTermin());
            String screeningName = DbUntersuchungTyp.getUntersuchungTypByID(appointment.getIdUnterschung()).getName();
            String childName = dataSource.getKindDatensatzById(appointment.getIdKind()).getName();

            childNames.add(childName);

            StringBuilder builder = new StringBuilder("Am ");
            builder.append(dateOfAppointment);
            builder.append(": ");
            builder.append(screeningName);
            builder.append(" für ");
            builder.append(childName);
            inboxStyle.addLine(builder.toString());
        }
        setStyle(inboxStyle);
        setTicker("Es stehen demnächst "+ appointments.size() +" Termine an.");

        StringBuilder builder = new StringBuilder(childNames.get(0));
        for(int i = 1; i < childNames.size() - 1; i++){
            builder.append(", ");
            builder.append(childNames.get(i));
        }
        builder.append(" und ");
        builder.append(childNames.get(childNames.size()-1));
        builder.append(" haben Termine.");

        setContentText(builder.toString());
        setContentTitle("Mehrere Termine stehen bevor.");
    }

    /**
     * Sets the notification up for displaying a single appointment.
     */
    private void setupForJustOneAppointment(){
        DbKindHatUntersuchungDatensatz appointment = appointments.getFirst();
        String dateOfAppointment = formatDate(appointment.getTermin());
        String screeningName = DbUntersuchungTyp.getUntersuchungTypByID(appointment.getIdUnterschung()).getName();
        String childName = dataSource.getKindDatensatzById(appointment.getIdKind()).getName();

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

    /**
     * Refactors a date as String from format MM/dd/yy to
     * dd.MM.yy.
     * @param date Example: "06/23/18"
     * @return Example: "23.06.18"
     */
    protected static String formatDate(String date){
        String [] part = date.split("/");
        StringBuilder builder = new StringBuilder(part[1]);
        builder.append('.');
        builder.append(part[0]);
        builder.append('.');
        builder.append(part[2]);
        return builder.toString();
    }

    @Override
    protected Intent makeOpenActivityIntent() {
        if(appointments.size() == 1){
            Intent intent = new Intent(super.context, SingleChildView.class);
            intent.putExtra("id", ""+ appointments.getFirst().getIdKind());
            return intent;
        }
        return super.makeOpenActivityIntent();
    }
}
