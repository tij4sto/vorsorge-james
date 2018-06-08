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
        if(FIX){
            fireingTime.set(Calendar.HOUR_OF_DAY, 2);
            fireingTime.set(Calendar.MINUTE, 32);

            Calendar today = Calendar.getInstance();
            if(fireingTime.before(today)){
                Log.d("MyAlarm", today.getTime().toString() + " past fireing time");
                fireingTime.set(Calendar.DAY_OF_YEAR, fireingTime.get(Calendar.DAY_OF_YEAR) + 1);
            }
        } else {
            fireingTime.set(Calendar.MINUTE, fireingTime.get(Calendar.MINUTE) + 1);

        }
        fireingTime.set(Calendar.SECOND, 0);

        Log.d("MyAlarm", "Fireing Time :"+ fireingTime.getTime().toString());
        return fireingTime;
    }
}
