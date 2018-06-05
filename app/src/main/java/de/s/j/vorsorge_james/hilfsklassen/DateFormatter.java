package de.s.j.vorsorge_james.hilfsklassen;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    public static final String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public static final Date formatDate(String date){
        return new Date(date);
    }

    public static final String formatDateToDBCorrect(Date date){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);
        return sdf.format(date);
    }
}
