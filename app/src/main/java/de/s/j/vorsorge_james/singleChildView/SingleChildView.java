package de.s.j.vorsorge_james.singleChildView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.childListViewActivity.ChildListViewActivity;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

/**
 * Created by Frieza on 03.05.2018.
 */

public class SingleChildView extends AppCompatActivity {

    private DbAccess dataSource;
    private Intent intent;
    int kindID;
    String kindName;
    DbKindDatensatz kind;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.dataSource = new DbAccess(this);
        setContentView(R.layout.activity_single_child_view);

        //Handling Event Data
        this.intent = getIntent();
        kind = this.dataSource.getKindDatensatzById(Integer.parseInt(intent.getStringExtra("id")));

        //Zeige Daten in TextView an.
        this.tv = (TextView) findViewById(R.id.name);
        tv.setText(this.kind.getName());

        init(this.kind);

        Log.d("SingleChild", kindName + " " + this.kindID);
    }

    private  void init(final DbKindDatensatz kind){
        Button buttonLoeschen = findViewById(R.id.loeschen);
        buttonLoeschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loescheKindOnButtonClick(kind)){
                    changeActivityToChildrenList();
                }
                else{
                    Toast t = Toast.makeText(SingleChildView.this, "Kind konnte nicht gelöscht werden.", Toast.LENGTH_SHORT);
                }
            }
        });

        showKindUntersuchungen(kind);
    }

    private boolean loescheKindOnButtonClick(DbKindDatensatz kind){
        try{
            this.dataSource.deleteKindDatensatzById(kind.getId());
            SingleChildView.this.finish();
            return true;
        }
        catch (Exception e){
            Log.d("SingleChild löschen", e.getMessage());
            return false;
        }
    }

    private void changeActivityToChildrenList(){
        Intent intent = new Intent(SingleChildView.this, ChildListViewActivity.class);
        SingleChildView.this.startActivity(intent);
    }

    private void showKindUntersuchungen(DbKindDatensatz kind){
        if(kind != null){
            ListView lv = findViewById(R.id.kind_untersuchungen);
            List<DbUntersuchungDatensatz> noetigeUntersuchungen = DbUntersuchungTyp.getBenoetigteUntersuchungenByKind(kind);
            ArrayAdapter untersuchungenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noetigeUntersuchungen);
            lv.setAdapter(untersuchungenAdapter);
        }
    }
}
