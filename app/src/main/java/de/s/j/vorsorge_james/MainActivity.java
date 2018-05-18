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

import de.s.j.vorsorge_james.childSelectionActivity.ChildSelectionActivity;
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
        this.initDateSetter();

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



    }

    public void initDateSetter(){
        final EditText alter = findViewById(R.id.geburtstag);
        int year, month, day;
        final Calendar kalendar = Calendar.getInstance();

        alter.setOnClickListener(new View.OnClickListener() {

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
        Intent intent = new Intent(MainActivity.this, ChildSelectionActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
