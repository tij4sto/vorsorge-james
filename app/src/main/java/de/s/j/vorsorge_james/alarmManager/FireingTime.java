package de.s.j.vorsorge_james.alarmManager;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

public final class FireingTime {

    public static final Calendar getTime(){
        Calendar fireingTime = Calendar.getInstance();
        fireingTime.set(Calendar.MINUTE, fireingTime.get(Calendar.MINUTE) + 1);
        fireingTime.set(Calendar.SECOND, 0);
        Log.d("MyAlarm", "Fireing Time :"+ fireingTime.getTime().toString());
        return fireingTime;
    }
}
