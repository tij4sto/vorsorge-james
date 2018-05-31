package de.s.j.vorsorge_james.singleUntersuchungView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

/**
 * Created by Frieza on 03.05.2018.
 */

public class SingleUntersuchungView extends AppCompatActivity {

    private static final String LOG_TAG = SingleUntersuchungView.class.getSimpleName();

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
        TextView tvBeschreibung = findViewById(R.id.beschreibung);
        tvBeschreibung.setText(this.untersuchung.getBeschreibung());

    //    init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        formular.saveDoctorInformation();
    }

    void insertDatensatz(DbKindHatUntersuchungDatensatz datensatz){
        boolean b = dataSource.insertKindHatUntersuchungDatensatz(datensatz);
        Log.d(LOG_TAG, "boolean b = " + b);
        //boolean b = dataSource.createKindHatUntersuchungDatensatz(idK, idU, textFieldDoctor.getText().toString(), textFieldDate.getText().toString());
        Toast.makeText(SingleUntersuchungView.this, "" + b, Toast.LENGTH_SHORT).show();
    }

    private void init(){
    //    setCalenderFunctions();
  /*      Button button1 = findViewById(R.id.eintragen);
        textFieldDate = findViewById(R.id.datum);
        textFieldDoctor = findViewById(R.id.arzt);
        textFieldHouse = findViewById(R.id.textFieldHouse);
        textFieldPLZ = findViewById(R.id.textFieldPLZ);
        textFieldStreet = findViewById(R.id.textFieldStreet);*/
        final DbAccess source = this.dataSource;
        final int idK = this.kind.getId();
        final int idU = (int) this.untersuchung.getId();


   /*     button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(datum.getText().toString().isEmpty())){
                    if(!(arzt.getText().toString().isEmpty())){
                        if(checkDatumIsValid(new Date(datum.getText().toString()))){
                            boolean b = source.createKindHatUntersuchungDatensatz(idK, idU, arzt.getText().toString(), datum.getText().toString());
                            Intent intent = new Intent(SingleUntersuchungView.this, SingleChildView.class);
                            SingleUntersuchungView.this.startActivity(intent);
                        }
                        else{
                            String hinweis = DbUntersuchungTyp.getZeitraumString(untersuchung, kind);
                            Toast.makeText(SingleUntersuchungView.this, "Das ausgewählte Datum muss zwischen " + hinweis + " liegen!" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else Toast.makeText(SingleUntersuchungView.this, "Sie haben keinen Arzt ausgewählt!", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(SingleUntersuchungView.this, "Sie müssen ein Datum eintragen!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }



    private boolean setUntersuchungToKind(int idKind, int idUntersuchung, String arzt, String datum){
        this.dataSource.open();
        try {
            boolean data = this.dataSource.createKindHatUntersuchungDatensatz(idKind, idUntersuchung, datum, arzt);
            return true;
        }

        catch (Exception e){
            return false;
        }
    }

    private boolean checkDatumIsValid(Date datum){
        Calendar c = Calendar.getInstance();
        c.setTime(this.kind.getDatum());
        c.add(Calendar.DAY_OF_YEAR, this.untersuchung.getVonTage());
        Date von = c.getTime();
        if(datum.getTime()-von.getTime() >= 0){
            c.setTime(this.kind.getDatum());
            c.add(Calendar.DAY_OF_YEAR, this.untersuchung.getBisTage());
            Date bis = c.getTime();
            if(datum.getTime()-bis.getTime() <= 0){
                return true;
            }
        }

        return false;
    }
/*
    public void setCalenderFunctions(){
        final Calendar c = Calendar.getInstance();
        final EditText et = (EditText) findViewById(R.id.datum);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(et, c);
            }
        };

        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        SingleUntersuchungView.this, date,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(EditText et, Calendar c) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        et.setText(sdf.format(c.getTime()));
    }

*/
    DbKindDatensatz getKind(){
        return this.kind;
    }

    DbUntersuchungDatensatz getUntersuchung(){
        return this.untersuchung;
    }
}

