package de.s.j.vorsorge_james.childSelectionActivity;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.dbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Frieza on 03.05.2018.
 */

public class ChildSelectionActivity extends AppCompatActivity {

    private dbAccess dataSource;
    private ListViewChildren listViewChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_selection);
        this.dataSource = new dbAccess(this);

        ConstraintLayout layout = findViewById(R.id.childrenList_Container);
        listViewChildren = new ListViewChildren(dataSource, this, layout);
        listViewChildren.loadChildren();


      //  zeigeKinder();
    }


}
