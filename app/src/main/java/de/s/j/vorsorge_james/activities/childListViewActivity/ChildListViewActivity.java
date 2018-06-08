package de.s.j.vorsorge_james.activities.childListViewActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.Footer;
import de.s.j.vorsorge_james.activities.addChildActivity.AddChildActivity;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.hilfsklassen.KindArrayAdapter;
import de.s.j.vorsorge_james.activities.singleChildViewActivity.SingleChildView;
import de.s.j.vorsorge_james.notifications.NotificationAlarmManager;

/**
 * Created by Frieza on 03.05.2018.
 */

public class ChildListViewActivity extends AppCompatActivity {

    private DbAccess dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list_view);
        this.dataSource = new DbAccess(this);
        new Footer(this);
        new NotificationAlarmManager(this).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            showChildren();
        } catch (Exception e) {
            Toast.makeText(this, "Sie müssen ein Kind hinzufügen, bevor Sie die App nutzen können.", Toast.LENGTH_LONG).show();
            Intent openAddChildActivity = new Intent(this, AddChildActivity.class);
            this.startActivity(openAddChildActivity);
        }
    }

    private void showChildren() throws Exception {
        List<DbKindDatensatz> kinder = dataSource.getKindListe();
        if(kinder.isEmpty()){
            throw new Exception();
        }
        final ListView lv = (ListView) findViewById(R.id.children_list);
        int i = lv.getId();

        final KindArrayAdapter kinderAdapter = new KindArrayAdapter(this, kinder);
        lv.setAdapter(kinderAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChildListViewActivity.this, SingleChildView.class);
                intent.putExtra("id", "" + kinderAdapter.getItem(position).getId());
                Log.d("LEL1: ", "" + id);
                String s = intent.getStringExtra("id");
                ChildListViewActivity.this.startActivity(intent);
            }
        });
    }
}
