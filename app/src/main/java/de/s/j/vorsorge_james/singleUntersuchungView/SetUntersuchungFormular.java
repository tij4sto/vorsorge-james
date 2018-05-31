package de.s.j.vorsorge_james.singleUntersuchungView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.calenderEditText.CalendarEditTextWrapper;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

final class SetUntersuchungFormular {

    private static final String LOG_TAG = SingleUntersuchungView.class.getSimpleName();

    private final static String nameOfPreferences = "stored-state";
    private final static String key_Doctor = "doctor";
    private final static String key_House = "house";
    private final static String key_Street = "street";
    private final static String key_PLZ = "plz";

    private SingleUntersuchungView activity;
    EditText textFieldDoctor, textFieldStreet, textFieldHouse, textFieldDate, textFieldPLZ;
    Button submitButton;

    SetUntersuchungFormular(final SingleUntersuchungView activity){
        this.activity = activity;
        this.textFieldDate = activity.findViewById(R.id.datum);
        this.textFieldDoctor = activity.findViewById(R.id.arzt);
        this.textFieldHouse = activity.findViewById(R.id.textFieldHouse);
        this.textFieldPLZ = activity.findViewById(R.id.textFieldPLZ);
        this.textFieldStreet = activity.findViewById(R.id.textFieldStreet);
        this.submitButton = activity.findViewById(R.id.buttonSubmitUntersuchung);

        new CalendarEditTextWrapper(textFieldDate, activity);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFilled()){
                    final int idK = activity.getKind().getId();
                    final int idU = (int) activity.getUntersuchung().getId();
                    DbKindHatUntersuchungDatensatz datensatz
                            = new DbKindHatUntersuchungDatensatz
                            (idK, idU, textFieldDoctor.getText().toString(), textFieldDate.getText().toString());
                    activity.insertDatensatz(datensatz);
                    //Toast.makeText(activity, "" + b, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void loadDoctorInformation(){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(nameOfPreferences, 0);
        textFieldDoctor.setText(sharedPreferences.getString(key_Doctor,""));
        textFieldStreet.setText(sharedPreferences.getString(key_Street, ""));
        textFieldHouse.setText(sharedPreferences.getString(key_House, ""));
        textFieldPLZ.setText(sharedPreferences.getString(key_PLZ, ""));
    }

    void saveDoctorInformation(){
        SharedPreferences.Editor preferencesEditor = activity.getSharedPreferences(nameOfPreferences,0).edit();
        preferencesEditor.putString(key_Doctor, this.textFieldDoctor.getText().toString());
        preferencesEditor.putString(key_House, this.textFieldHouse.getText().toString());
        preferencesEditor.putString(key_Street, this.textFieldStreet.getText().toString());
        preferencesEditor.putString(key_PLZ, this.textFieldPLZ.getText().toString());
        preferencesEditor.apply();
    }

    private boolean isFilled(){
        return
                doctorTextFieldIsFilled() && dateTextFieldIsFilled() &&
                checkDatumIsValid(new Date(textFieldDate.getText().toString()));
    }

    private boolean dateTextFieldIsFilled(){
        boolean filled = !textFieldDate.getText().toString().isEmpty();
        if(!filled){
            Toast.makeText(activity, "Sie müssen ein Datum eintragen!", Toast.LENGTH_SHORT).show();
        }
        return filled;
    }

    private boolean doctorTextFieldIsFilled(){
        boolean filled = !textFieldDoctor.getText().toString().isEmpty();
        if(!filled){
            Toast.makeText(activity, "Sie haben keinen Arzt ausgewählt!", Toast.LENGTH_SHORT).show();
        }
        return filled;
    }

    private boolean checkDatumIsValid(Date datum){
        DbKindDatensatz kind = activity.getKind();
        DbUntersuchungDatensatz untersuchung = activity.getUntersuchung();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(kind.getDatum());
        calendar.add(Calendar.DAY_OF_YEAR, untersuchung.getVonTage());
        Date von = calendar.getTime();
        if(datum.getTime()-von.getTime() >= 0){
            calendar.setTime(kind.getDatum());
            calendar.add(Calendar.DAY_OF_YEAR, untersuchung.getBisTage());
            Date bis = calendar.getTime();
            if(datum.getTime()-bis.getTime() <= 0){
                return true;
            }
        }
        String hinweis = DbUntersuchungTyp.getZeitraumString(untersuchung, kind);
        Toast.makeText(activity, "Das ausgewählte Datum muss zwischen " + hinweis + " liegen!" , Toast.LENGTH_SHORT).show();
        return false;
    }

}
