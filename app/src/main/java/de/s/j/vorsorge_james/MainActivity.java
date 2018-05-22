package de.s.j.vorsorge_james;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import de.s.j.vorsorge_james.addChildActivity.AddChildActivity;
import de.s.j.vorsorge_james.childActivity.ChildActivity;
import de.s.j.vorsorge_james.childListViewActivity.ChildListViewActivity;
import de.s.j.vorsorge_james.childSelectionActivity.ChildSelectionActivity;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

public class MainActivity extends AppCompatActivity {
    
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private DbAccess dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DbAccess(this);
        this.initDateSetter();
      //  setListener();
        kindAuswahlActivity();


        Log.d("Hallo", dataSource.getKindListe().toString());
    }

    private void setListener(){
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOnButtonClick();
            }
        });

        ////

        kindAuswahlActivity();

        ///


        Button button2 = findViewById(R.id.uebersicht);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kindAuswahlActivity();
            }
        });
    }

    public void initDateSetter(){
        final Calendar c = Calendar.getInstance();
        final EditText et = (EditText) findViewById(R.id.geburtstag);
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
                        MainActivity.this, date,
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

    public void createOnButtonClick(){
        Log.d(LOG_TAG, "Die Datenquelle wird in CreateButton() geöffnet.");
        dataSource.open();

        EditText name = findViewById(R.id.name);
        EditText geburtstag = findViewById(R.id.geburtstag);

        if(!(name.getText().toString().equals(""))){
            if(!(geburtstag.getText().toString().equals(""))){
                Log.d(LOG_TAG, "Main Activity versucht Insert zu machen!");
                dataSource.createKindDatensatz(name.getText().toString(), geburtstag.getText().toString());
                List<DbKindDatensatz> l = dataSource.getKindListe();
                Log.d(LOG_TAG, l.toString());
                kindAuswahlActivity();
            }
        }
    }

    public void kindAuswahlActivity(){
        Intent intent = new Intent(MainActivity.this, ChildListViewActivity.class);
        MainActivity.this.startActivity(intent);

      /*  Intent openIntent = new Intent(MainActivity.this, AddChildActivity.class);
        MainActivity.this.startActivity(openIntent);*/
    }
}
