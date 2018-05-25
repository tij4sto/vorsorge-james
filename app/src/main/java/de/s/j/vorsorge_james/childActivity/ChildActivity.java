package de.s.j.vorsorge_james.childActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;

/**
 * Created by Frieza on 03.05.2018.
 */

public class ChildActivity extends AppCompatActivity {

    private DbAccess dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_selection);
        this.dataSource = new DbAccess(this);
<<<<<<< HEAD

=======
        this.deleteChildButton = findViewById(R.id.deleteButton);
        this.deleteChildButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.deleteKindDatensatz(currentChild.getId());
                finish();
            }
        });
        TextView t = findViewById(R.id.childActivityTitle);
        t.setText(currentChild.getName());
>>>>>>> dev-frieder
    }


}
