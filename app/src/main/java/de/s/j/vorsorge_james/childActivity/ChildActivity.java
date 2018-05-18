package de.s.j.vorsorge_james.childActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Frieza on 03.05.2018.
 */

public class ChildActivity extends AppCompatActivity {

    public static DbKindDatensatz currentChild;

    private DbAccess dataSource;

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        this.dataSource = new DbAccess(this);
        TextView t = findViewById(R.id.ChildSelectionTitle);
        t.setText(currentChild.getName());
    }


}
