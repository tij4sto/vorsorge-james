package de.s.j.vorsorge_james.addChildActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.generalActivityElements.FooterHomeOnly;

/**
 * Created by Frieza on 22.05.2018.
 */

public final class AddChildActivity extends AppCompatActivity {

    EditText nameTextField, birthdayTextField;
    private CustomDatePickerDialog datePickerDialog;

    private DbAccess dbAccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        setupFields();
        dbAccess = new DbAccess(this);
        new FooterHomeOnly(this);
    }

    private void setupFields(){
        nameTextField = findViewById(R.id.inputNameOfChild);
        datePickerDialog = new CustomDatePickerDialog(this);
        birthdayTextField = findViewById(R.id.inputBirthdayChild);
        birthdayTextField.setOnClickListener(new EditText.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        Button submitButton = findViewById(R.id.submitChildButton);
        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitChild();
            }
        });
    }

    /**
     * Puts the value of a specified date into the birthday text area.
     * @param calendar Date to put into birthday text field
     */
    void setDate(Calendar calendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.GERMAN);
        birthdayTextField.setText(format.format(calendar.getTime()));
    }

    /**
     * Reads out the text fields and inserts a new dataset into the database.
     * Closes the activity afterwards.
     */
    private void submitChild(){
        dbAccess.open();
        if(textFieldsAreFilled()){
            dbAccess.createKindDatensatz(
                    nameTextField.getText().toString(),
                    birthdayTextField.getText().toString());
            finish();
        }
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
