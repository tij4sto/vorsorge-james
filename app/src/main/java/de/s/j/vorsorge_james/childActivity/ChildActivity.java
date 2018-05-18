package de.s.j.vorsorge_james.childActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.dbAccess;

/**
 * Created by Frieza on 03.05.2018.
 */

public class ChildActivity extends AppCompatActivity {

    private dbAccess dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_selection);
        this.dataSource = new dbAccess(this);

    }


}
