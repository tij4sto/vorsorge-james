package de.s.j.vorsorge_james;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

import de.s.j.vorsorge_james.database.dbKind.DbKindAccessWorker;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private DbKindAccessWorker dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbKindDatensatz testMemo = new DbKindDatensatz(120, "Jannis", new Date(04/07/1989));
        Log.d(LOG_TAG, "Inhalt der Testmemo: " + testMemo.toString());

        dataSource = new DbKindAccessWorker(this);
    }
}
