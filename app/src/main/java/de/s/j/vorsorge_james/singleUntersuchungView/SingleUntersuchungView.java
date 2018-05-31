package de.s.j.vorsorge_james.singleUntersuchungView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.s.j.vorsorge_james.MainActivity;
import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.childListViewActivity.ChildListViewActivity;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;
import de.s.j.vorsorge_james.hilfsklassen.UntersuchungArrayAdapter;

/**
 * Created by Frieza on 03.05.2018.
 */

public class SingleUntersuchungView extends AppCompatActivity {

    private DbAccess dataSource;
    private Intent intent;
    private DbKindDatensatz kind;
    private DbUntersuchungDatensatz untersuchung;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.dataSource = new DbAccess(this);
        setContentView(R.layout.activity_single_untersuchung_view);


        //Handling Event Data
        this.intent = getIntent();
        this.kind = this.dataSource.getKindDatensatzById(Integer.parseInt(intent.getStringExtra("kid")));
        this.untersuchung = DbUntersuchungTyp.getUntersuchungDatensatzById(Integer.parseInt(intent.getStringExtra("uid")));

        //Zeige Daten in TextView an.
        TextView tvName = findViewById(R.id.name);
        tvName.setText(this.untersuchung.getName());

        init();
    }

    private  void init(){
        setCalenderFunctions();
        Button buttonLoeschen = findViewById(R.id.eintragen);
        final EditText datum = findViewById(R.id.datum);
        final EditText arzt = findViewById(R.id.arzt);
        final DbAccess source = this.dataSource;
        final int idK = this.kind.getId();
        final int idU = (int) this.untersuchung.getId();


        buttonLoeschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(datum.getText().toString().isEmpty())){
                    if(!(arzt.getText().toString().isEmpty())){
                        if(checkDatumIsValid(new Date(datum.getText().toString()))){
                            boolean b = source.createKindHatUntersuchungDatensatz(idK, idU, datum.getText().toString(), arzt.getText().toString());
                            Toast.makeText(SingleUntersuchungView.this, "" + b, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
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
}
