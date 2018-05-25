package de.s.j.vorsorge_james.singleChildView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

/**
 * Created by Frieza on 03.05.2018.
 */

public class SingleChildView extends AppCompatActivity {

    private DbAccess dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.dataSource = new DbAccess(this);
        setContentView(R.layout.activity_single_child_view);
        Intent intent = getIntent();

        String idString = intent.getStringExtra("id");
        int idInt = Integer.parseInt(idString);



        TextView et = (TextView) findViewById(R.id.name);
        et.setText(idString);


        DbKindDatensatz kind = dataSource.findKindById(idInt);

        if(kind != null){
            ListView lv = findViewById(R.id.kind_untersuchungen);
            List<DbUntersuchungTyp> untersuchungen = DbUntersuchungTyp.checkKindBrauchtUntersuchung(kind);
            ArrayAdapter untersuchungenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, untersuchungen);
            lv.setAdapter(untersuchungenAdapter);
        }

        Log.d("LEL", idString + " " + idInt);
    }
}
