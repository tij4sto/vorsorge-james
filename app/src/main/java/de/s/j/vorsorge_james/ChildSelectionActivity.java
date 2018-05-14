package de.s.j.vorsorge_james;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import de.s.j.vorsorge_james.database.dbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Frieza on 03.05.2018.
 */

public class ChildSelectionActivity extends AppCompatActivity {

    private dbAccess dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_selection);
        this.dataSource = new dbAccess(this);
        List<DbKindDatensatz> kinderListe = dataSource.getKindListe();

        zeigeKinder();
    }

    private void zeigeKinder(){
        List<DbKindDatensatz> kinder = dataSource.getKindListe();
        ArrayAdapter<DbKindDatensatz> kinderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, kinder);

        ListView kinderListe = (ListView) findViewById(R.id.listview_kinder);
        kinderListe.setAdapter(kinderAdapter);
    }


}
