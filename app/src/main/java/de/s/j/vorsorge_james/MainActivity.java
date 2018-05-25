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

import java.util.Calendar;
import java.util.List;

<<<<<<< HEAD
import de.s.j.vorsorge_james.childListViewActivity.ChildListViewActivity;
=======
import de.s.j.vorsorge_james.addChildActivity.AddChildActivity;
import de.s.j.vorsorge_james.childActivity.ChildActivity;
import de.s.j.vorsorge_james.childSelectionActivity.ChildSelectionActivity;
>>>>>>> dev-frieder
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

public class MainActivity extends AppCompatActivity {
    
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private DbAccess dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        dataSource = new DbAccess(this);
<<<<<<< HEAD
        this.init();
    }

    private void init(){
        this.setCalenderFunctions();
=======
        this.initDateSetter();

>>>>>>> dev-frieder
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeKindIntoDbAfterButtonClick();
            }
        });

<<<<<<< HEAD
        Button button2 = findViewById(R.id.uebersicht);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivityToAuswahlKind();
            }
        });
    }

    public void setCalenderFunctions(){
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
=======
        ////

        kindAuswahlActivity();

        ///


    }

    public void initDateSetter(){
        final EditText alter = findViewById(R.id.geburtstag);
        int year, month, day;
        final Calendar kalendar = Calendar.getInstance();

        alter.setOnClickListener(new View.OnClickListener() {
>>>>>>> dev-frieder

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        alter.setText(dayOfMonth + "/" + month + "/" + year);
                    }

                }, kalendar.get(Calendar.YEAR) , kalendar.get(Calendar.MONTH), kalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    public void writeKindIntoDbAfterButtonClick(){
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
                changeActivityToAuswahlKind();
            }
        }
    }

<<<<<<< HEAD
    public void changeActivityToAuswahlKind(){
        Intent intent = new Intent(MainActivity.this, ChildListViewActivity.class);
=======
    public void kindAuswahlActivity(){
        Intent intent = new Intent(MainActivity.this, ChildSelectionActivity.class);
>>>>>>> dev-frieder
        MainActivity.this.startActivity(intent);

      /*  Intent openIntent = new Intent(MainActivity.this, AddChildActivity.class);
        MainActivity.this.startActivity(openIntent);*/
    }
}
