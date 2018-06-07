package de.s.j.vorsorge_james.calendar;

import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

import de.s.j.vorsorge_james.hilfsklassen.DateFormatter;

import static org.junit.Assert.assertEquals;

public class CalenderArithmetic {

    @Test
    public void simpleDateSubtraction(){
        Calendar origin = Calendar.getInstance();
        origin.set(2018, 6,6, 12,00);

        Calendar threeDaysBefore = Calendar.getInstance();
        threeDaysBefore.set(2018, 6,6, 12,00);
        threeDaysBefore.add(Calendar.DAY_OF_YEAR, -3);

        Calendar expected = Calendar.getInstance();
        expected.set(2018, 6,3, 12,00);

        assertEquals(expected, threeDaysBefore);
    }


    @Test
    public void dateSubtractionMonthOverflow(){
        Calendar threeDaysBefore = Calendar.getInstance();
        threeDaysBefore.set(2018, 5,1, 12,00);
        threeDaysBefore.add(Calendar.DAY_OF_YEAR, -3);

        Calendar expected = Calendar.getInstance();
        expected.set(2018, 4,29, 12,00);

        assertEquals( "" + expected.getTime().toString() + " " + threeDaysBefore.getTime().toString(), expected, threeDaysBefore);
    }

    @Test
    public void dateSubtractionYearOverflow(){
        Calendar threeDaysBefore = Calendar.getInstance();
        threeDaysBefore.set(2018, 0,1, 12,00, 00);
        threeDaysBefore.add(Calendar.DAY_OF_YEAR, -1);

        Calendar expected = Calendar.getInstance();
        expected.set(2017, 11,30, 12,00, 00);
        expected.add(Calendar.DAY_OF_YEAR, 1);

        assertEquals( "" + expected.getTime().toString() + " " + threeDaysBefore.getTime().toString(), expected, threeDaysBefore);
    }

    @Test
    public void formatterWithYearOverflow() throws ParseException {
        String string = "01/01/2018";
        Calendar calendar = DateFormatter.parseStringToCalendar(string);

        Calendar expected = Calendar.getInstance();
        expected.set(2018, 0,1);

        assertEquals( "" + expected.getTime().toString() + " " + calendar.getTime().toString(), expected, calendar);
    }

}
