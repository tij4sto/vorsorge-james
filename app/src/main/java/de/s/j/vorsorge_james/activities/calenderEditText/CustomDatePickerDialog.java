package de.s.j.vorsorge_james.activities.calenderEditText;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Wrapper class containing a DatePickerDialog.
 * It automatically implements OnDateSetListener.
 */
final class CustomDatePickerDialog {

    private DatePickerDialog dialog;

    public CustomDatePickerDialog(@NonNull final Activity activity, @NonNull final CalendarEditTextWrapper editText) {

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editText.setDate(calendar);
            }
        };

        dialog = new DatePickerDialog(
                activity, date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    void show(){
        dialog.show();
    }
}
