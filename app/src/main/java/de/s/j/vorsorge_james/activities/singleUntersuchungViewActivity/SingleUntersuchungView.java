package de.s.j.vorsorge_james.activities.singleUntersuchungViewActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.calenderEditText.CalendarEditTextWrapper;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbKindHatGewichtUndGroesse.DbKindHatGewichtUndGroesse;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

/**
 * Created by Frieza on 03.05.2018.
 */

public class SingleUntersuchungView extends AppCompatActivity {

    private DbAccess dataSource;
    private Intent intent;
    private DbKindDatensatz kind;
    private DbUntersuchungDatensatz untersuchung;

    private SetUntersuchungFormular formular;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_untersuchung_view);
        formular = new SetUntersuchungFormular(this);
        formular.loadDoctorInformation();
        this.dataSource = new DbAccess(this);

        //Handling Event Data
        this.intent = getIntent();
        this.kind = this.dataSource.getKindDatensatzById(Integer.parseInt(intent.getStringExtra("kid")));
        this.untersuchung = DbUntersuchungTyp.getUntersuchungDatensatzById(Integer.parseInt(intent.getStringExtra("uid")));

        //Zeige Daten in TextView an.
        TextView tvName = findViewById(R.id.name);
        tvName.setText(this.untersuchung.getName());
        final TextView tvBeschreibung = findViewById(R.id.textViewDescriptionUntersuchung);
        tvBeschreibung.setText(this.untersuchung.getBeschreibung());
      //  tvBeschreibung.setText(this.untersuchung.getBeschreibung());

        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tvBeschreibung.setVisibility(View.VISIBLE);
                } else {
                    tvBeschreibung.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        formular.saveDoctorInformation();
    }

    void insertDatensatz(String doctor, String date){
        List<DbKindHatUntersuchungDatensatz> liste = dataSource.getKindHatUntersuchungListe();
        for(DbKindHatUntersuchungDatensatz item : liste){
            if(item.getIdKind() == this.kind.getId() && item.getIdUnterschung() == this.untersuchung.getId()){
                boolean b = dataSource.updateKindHatUntersuchung(this.kind.getId(), (int) this.untersuchung.getId(), doctor, date);
                if(b) {
                    finish();
                    return;
                }
                else Toast.makeText(this, "Das hat nicht funktioniert", Toast.LENGTH_SHORT).show();
            }
        }

        boolean b = dataSource.createKindHatUntersuchungDatensatz(this.kind.getId(), (int) this.untersuchung.getId(), doctor, date);
        if(b) finish();
        else Toast.makeText(this, "Das hat nicht funktioniert", Toast.LENGTH_SHORT).show();



    }

    DbKindDatensatz getKind(){
        return this.kind;
    }

    DbUntersuchungDatensatz getUntersuchung(){
        return this.untersuchung;
    }



    static final class SetUntersuchungFormular {

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
                        activity.insertDatensatz(textFieldDoctor.getText().toString(), textFieldDate.getText().toString());
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

            // TODO: REPLACE DATE WITH CALENDAR
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = format.parse(textFieldDate.getText().toString());
                return
                        doctorTextFieldIsFilled() && dateTextFieldIsFilled() &&
                                checkDatumIsValid(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
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
}

