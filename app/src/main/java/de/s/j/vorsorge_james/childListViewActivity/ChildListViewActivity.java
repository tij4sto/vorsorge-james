package de.s.j.vorsorge_james.childListViewActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Frieza on 03.05.2018.
 */

public class ChildListViewActivity extends AppCompatActivity {

    private DbAccess dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_list_view);
        this.dataSource = new DbAccess(this);
        showChildren();
    }

    private void showChildren(){
        List<DbKindDatensatz> kinder = dataSource.getKindListe();

        ListView lv = (ListView) findViewById(R.id.children_list);
        int i = lv.getId();

        ArrayAdapter<DbKindDatensatz> kinderAdapter = new ArrayAdapter<>(
                this, android.R.layout.activity_list_item, kinder
        );

        lv.setAdapter(kinderAdapter);
    }


}
