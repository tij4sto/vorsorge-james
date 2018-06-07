package de.s.j.vorsorge_james.hilfsklassen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    public static final String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public static final String formatDateFromString(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date d = new Date(sdf.parse(date).getTime());
        return formatDateToDBCorrect(d);
    }

    public static final Date formatDate(String date){
        return new Date(date);
    }

    public static final String formatDateToDBCorrect(Date date){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);
        return sdf.format(date);
    }

    public static final Calendar parseStringToCalendar(String string) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendar.setTime(sdf.parse(string));
        return calendar;
    }

    /**
     * Refactors a date as String from format MM/dd/yy to
     * dd.MM.yy.
     * @param date Example: "06/23/18"
     * @return Example: "23.06.18"
     */
    public static String formatDateStringFromDb(String date){
        String [] part = date.split("/");
        StringBuilder builder = new StringBuilder(part[1]);
        builder.append('.');
        builder.append(part[0]);
        builder.append('.');
        builder.append(part[2]);
        return builder.toString();
    }


}
