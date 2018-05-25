package de.s.j.vorsorge_james.childSelectionActivity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.addChildActivity.AddChildActivity;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Frieza on 03.05.2018.
 */

public class ChildSelectionActivity extends AppCompatActivity {

    private DbAccess dataSource;
    private ListViewChildren listViewChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_selection);
        this.dataSource = new DbAccess(this);

     /*   ArrayList<DbKindDatensatz> listChildren = new ArrayList<>(dataSource.getKindListe());
        CustomAdapter adapter = new CustomAdapter(listChildren);
        ListView listView = findViewById(R.id.sample);
        listView.setAdapter(adapter);*/



      /*  ArrayAdapter<DbKindDatensatz> adapter = new ArrayAdapter<DbKindDatensatz>(this, R.layout.sample, R.id.sample, listChildren);
        ListView listView = findViewById(R.id.sample);
        listView.setAdapter(adapter);
*/

        ConstraintLayout layout = findViewById(R.id.childrenList_Container);
        listViewChildren = new ListViewChildren(dataSource, this, layout);
        listViewChildren.loadChildren();

        Button addChildButton = findViewById(R.id.addChildButton);
        addChildButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent openIntent = new Intent(ChildSelectionActivity.this, AddChildActivity.class);
                ChildSelectionActivity.this.startActivity(openIntent);
            }
        });


      //  zeigeKinder();
    }

    class CustomAdapter extends BaseAdapter {

        private ArrayList<DbKindDatensatz> arrayList;

        CustomAdapter(ArrayList<DbKindDatensatz> list){
            this.arrayList = list;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.sample, null);

            TextView tw1 = convertView.findViewById(R.id.sample_tw);
            TextView tw2 = convertView.findViewById(R.id.sample_tw2);

            tw2.setText(arrayList.get(position).getName());
            return null;
        }
    }


}
