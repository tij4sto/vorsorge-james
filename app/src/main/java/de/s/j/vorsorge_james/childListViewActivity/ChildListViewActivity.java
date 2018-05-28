package de.s.j.vorsorge_james.childListViewActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.hilfsklassen.KindArrayAdapter;
import de.s.j.vorsorge_james.singleChildView.SingleChildView;
import de.s.j.vorsorge_james.utils.guiUtils.OnClickSetter;

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

        Button addChildButton = findViewById(R.id.addChildButton);
        OnClickSetter.openAddChildActivity(addChildButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showChildren();
    }

    private void showChildren(){
        List<DbKindDatensatz> kinder = dataSource.getKindListe();

        final ListView lv = (ListView) findViewById(R.id.children_list);
        int i = lv.getId();

        /*
        final ArrayAdapter<DbKindDatensatz> kinderAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, kinder
        );

        */

        final KindArrayAdapter kinderAdapter = new KindArrayAdapter(this, kinder);
        lv.setAdapter(kinderAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChildListViewActivity.this, SingleChildView.class);
                intent.putExtra("id", "" + kinderAdapter.getItem(position).getId());
                Log.d("LEL1: ", "" + id);
                ChildListViewActivity.this.startActivity(intent);
                ChildListViewActivity.this.finish();
            }
        });
    }
}
