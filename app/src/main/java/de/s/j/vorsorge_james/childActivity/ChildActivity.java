package de.s.j.vorsorge_james.childActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Frieza on 03.05.2018.
 */

public class ChildActivity extends AppCompatActivity {

    public static DbKindDatensatz currentChild = new DbKindDatensatz(0,"Kind", null);
    private DbAccess dataSource;

    private Button deleteChildButton;

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        this.dataSource = new DbAccess(this);
        this.deleteChildButton = findViewById(R.id.deleteButton);
        this.deleteChildButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.deleteKindDatensatzById(currentChild.getId());
                finish();
            }
        });
        TextView t = findViewById(R.id.childActivityTitle);
        t.setText(currentChild.getName());
    }


}
