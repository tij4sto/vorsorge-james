package de.s.j.vorsorge_james.singleChildView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;

/**
 * Created by Frieza on 03.05.2018.
 */

public class SingleChildView extends AppCompatActivity {

    private DbAccess dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_child_view);
        Intent i = getIntent();
        String id = i.getStringExtra("id");

        TextView et = (TextView) findViewById(R.id.name);
        et.setText(id);

        Log.d("LEL", id);
    }
}
