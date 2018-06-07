package de.s.j.vorsorge_james.activities.addChildActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.calenderEditText.CalendarEditTextWrapper;
import de.s.j.vorsorge_james.database.DbAccess;

/**
 * Created by Frieza on 22.05.2018.
 */

public final class AddChildActivity extends AppCompatActivity {

    EditText nameTextField, birthdayTextField;
    CalendarEditTextWrapper calendarTextFieldWrapper_birthday;

    private DbAccess dbAccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        setupFields();
        dbAccess = new DbAccess(this);
    }

    private void setupFields(){
        nameTextField = findViewById(R.id.inputNameOfChild);
        birthdayTextField = findViewById(R.id.inputBirthdayChild);
        calendarTextFieldWrapper_birthday = new CalendarEditTextWrapper(birthdayTextField, this);

        Button submitButton = findViewById(R.id.submitChildButton);
        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitChild();
            }
        });
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
