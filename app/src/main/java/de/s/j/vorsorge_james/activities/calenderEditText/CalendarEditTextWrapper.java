package de.s.j.vorsorge_james.activities.calenderEditText;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Wrapper class for an EditText-Object.
 * Automatically stes the EditText-Object on click to open a date picker
 * and display the picked date as text.
 */
public final class CalendarEditTextWrapper {

    private EditText editText;
    private CustomDatePickerDialog datePickerDialog;

    public CalendarEditTextWrapper(EditText editText, Activity activity) {
        datePickerDialog = new CustomDatePickerDialog(activity, this);
        this.editText = editText;
        this.editText.setOnClickListener(new EditText.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    /**
     * Puts the value of a specified date into the text area.
     * @param calendar Date to put into the text field
     */
    void setDate(Calendar calendar) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.GERMAN);
        editText.setText(format.format(calendar.getTime()));
    }
}
