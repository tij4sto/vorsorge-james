package de.s.j.vorsorge_james.hilfsklassen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public static final String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM yyyy");
        return sdf.format(date);
    }
}
