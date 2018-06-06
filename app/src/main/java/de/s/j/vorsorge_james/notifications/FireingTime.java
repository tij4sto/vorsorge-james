package de.s.j.vorsorge_james.notifications;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

/**
 * Test class for providing a time for building a notification.
 */
public final class FireingTime {

    private final static boolean FIX = true;

    public static final Calendar getTime(){
        Calendar fireingTime = Calendar.getInstance();
      //  fireingTime.set(Calendar.MINUTE, fireingTime.get(Calendar.MINUTE) + 1);
     //   fireingTime.set(Calendar.SECOND, 0);
        Log.d("MyAlarm", "Fireing Time :"+ fireingTime.getTime().toString());
        return fireingTime;
    }
}
