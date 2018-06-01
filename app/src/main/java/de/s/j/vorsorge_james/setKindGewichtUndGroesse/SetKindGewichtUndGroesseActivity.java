package de.s.j.vorsorge_james.setKindGewichtUndGroesse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.hilfsklassen.DateFormatter;
import de.s.j.vorsorge_james.singleChildView.SingleChildView;

public class SetKindGewichtUndGroesseActivity extends AppCompatActivity {

    private DbAccess datasource;
    private int idKind;
    private Intent intent;
    private EditText etGewicht;
    private EditText etGroesse;
    private Button speichern;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_gewicht_und_groesse);
        this.datasource = new DbAccess(this);
        this.intent = getIntent();
        this.idKind = Integer.parseInt(this.intent.getStringExtra("idK"));
        init();
    }

    private void init(){
        Calendar c = Calendar.getInstance();
        final String datum = DateFormatter.formatDateToDBCorrect(c.getTime());
        this.etGewicht = findViewById(R.id.gewicht);
        this.etGroesse = findViewById(R.id.groesse);
        this.speichern = findViewById(R.id.gugspeichern);

        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(etGewicht.getText().toString().isEmpty())){
                    if(!(etGroesse.getText().toString().isEmpty())){
                        int cm = Integer.parseInt(etGroesse.getText().toString());
                        int kg = Integer.parseInt(etGewicht.getText().toString());
                        if(saveGewichtUndGroesse(idKind, datum, cm, kg)){
                            SetKindGewichtUndGroesseActivity.this.finish();
                        }

                        else {
                            Toast.makeText(SetKindGewichtUndGroesseActivity.this, "Entweder Sie haben heute bereits Daten eingetragen oder ein anderer Fehler ist aufgetreten!", Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        });
    }

    private boolean saveGewichtUndGroesse(int idK, String date, int cm, int kg){
        return this.datasource.saveGewichtUndGroesseInDb(idK, date, cm, kg);
    }
}
