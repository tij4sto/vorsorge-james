package de.s.j.vorsorge_james.addChildActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.childSelectionActivity.ChildSelectionActivity;
import de.s.j.vorsorge_james.database.DbAccess;

/**
 * Created by Frieza on 22.05.2018.
 */

public final class AddChildActivity extends AppCompatActivity {

    private EditText nameTextField, birthdayTextField;

    private DbAccess dbAccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        setupFields();
        dbAccess = new DbAccess(this);
    }

    private void setupFields(){
        birthdayTextField = findViewById(R.id.inputBirthdayChild);
        initDateSetter();
        nameTextField = findViewById(R.id.inputNameOfChild);
        Button submitButton = findViewById(R.id.submitChildButton);
        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOnButtonClick();
            }
        });


        nameTextField.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }

    private void initDateSetter(){
        final Calendar calendar = Calendar.getInstance();
        birthdayTextField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddChildActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthdayTextField.setText(dayOfMonth + "/" + month + "/" + year);
                    }

                }, calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    public void createOnButtonClick(){
        //   Log.d(LOG_TAG, "Die Datenquelle wird in CreateButton() geöffnet.");
        dbAccess.open();



        if(textFieldsAreFilled()){
            //Log.d(LOG_TAG, "Main Activity versucht Insert zu machen!");
            dbAccess.createKindDatensatz(nameTextField.getText().toString(), birthdayTextField.getText().toString());
            // List<DbKindDatensatz> l = dataSource.getKindListe();
            // Log.d(LOG_TAG, l.toString());
            kindAuswahlActivity();
        }

    }

    private void kindAuswahlActivity(){
        Intent intent = new Intent(AddChildActivity.this, ChildSelectionActivity.class);
        AddChildActivity.this.startActivity(intent);
    }

    /**
     * Returns whether the TextFields are filled.
     * @return true, if TextFields are filled
     */
    private boolean textFieldsAreFilled(){
        return
                nameTextField.getText() != null &&
                        nameTextField.getText().toString().length() > 0 &&
                        birthdayTextField.getText() != null &&
                        birthdayTextField.getText().toString().length() > 0;

    }
}
