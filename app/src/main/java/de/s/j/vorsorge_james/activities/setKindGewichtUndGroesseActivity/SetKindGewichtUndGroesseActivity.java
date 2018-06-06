package de.s.j.vorsorge_james.activities.setKindGewichtUndGroesseActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbKindHatGewichtUndGroesse.DbKindHatGewichtUndGroesse;
import de.s.j.vorsorge_james.hilfsklassen.DateFormatter;

public class SetKindGewichtUndGroesseActivity extends AppCompatActivity {

    private DbAccess datasource;
    private int idKind;
    private DbKindDatensatz kind;
    private Intent intent;
    private EditText etGewicht;
    private EditText etGroesse;
    private Button speichern;
    private String datum = DateFormatter.formatDateToDBCorrect(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_gewicht_und_groesse);
        this.datasource = new DbAccess(this);
        this.intent = getIntent();
        this.idKind = Integer.parseInt(this.intent.getStringExtra("idK"));
        this.kind = datasource.getKindDatensatzById(idKind);
        try {
            init();
        } catch (Exception e) {
            Log.d("Init()", e.getMessage());
        }
    }

    private void init() throws ParseException {
        Calendar c = Calendar.getInstance();
        EditText etDatum = (EditText) findViewById(R.id.datum);

        //Setze Datum auf heute
        etDatum.setText(DateFormatter.formatDate(new Date(c.getTime().getTime())));
        //Kalenderr Dialog öffnen
        setCalenderFunctions();


        this.etGewicht = findViewById(R.id.gewicht);
        this.etGroesse = findViewById(R.id.groesse);
        this.speichern = findViewById(R.id.gugspeichern);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(this.kind.getName());

        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(etGewicht.getText().toString().isEmpty())){
                    if(!(etGroesse.getText().toString().isEmpty())){
                        try{
                            float cm = Integer.parseInt(etGroesse.getText().toString());
                            String kgs = etGewicht.getText().toString().replace(",", ".");
                            float kg = Float.parseFloat(kgs);
                            if(kg > 300) kg = kg / 1000;
                            if(saveGewichtUndGroesse(idKind, SetKindGewichtUndGroesseActivity.this.datum, (int) cm, (int) kg)){
                                SetKindGewichtUndGroesseActivity.this.finish();
                            }

                            else {
                                Toast.makeText(SetKindGewichtUndGroesseActivity.this, "Entweder Sie haben heute bereits Daten eingetragen oder ein anderer Fehler ist aufgetreten!", Toast.LENGTH_LONG).show();
                            }
                        }

                        catch(Exception e){
                            Toast.makeText(SetKindGewichtUndGroesseActivity.this, "Falsche Eingabe!", Toast.LENGTH_LONG).show();
                        }

                    }
                }

            }
        });
    }

    public void setCalenderFunctions(){
        final Calendar c = Calendar.getInstance();
        final EditText etDatum = (EditText) findViewById(R.id.datum);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                etDatum.setText(DateFormatter.formatDate(new Date(c.getTime().getTime())));
                try {
                    datum = DateFormatter.formatDateFromString(etDatum.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };

        etDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        SetKindGewichtUndGroesseActivity.this, date,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private boolean saveGewichtUndGroesse(int idK, String date, int cm, int kg){
        List<DbKindHatGewichtUndGroesse> list = datasource.getGewichtGroesseListe(idK);
        for(DbKindHatGewichtUndGroesse item : list){
            if(item.getDate().equalsIgnoreCase(date) && item.getId() == idK)
                return this.datasource.updateGewichtUndGroesse(idK, date, cm, kg);
        }
        return this.datasource.saveGewichtUndGroesseInDb(idK, date, cm, kg);
    }
}
