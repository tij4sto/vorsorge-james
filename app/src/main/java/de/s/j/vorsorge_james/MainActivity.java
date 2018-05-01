package de.s.j.vorsorge_james;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

import de.s.j.vorsorge_james.db.DbAccessWorker;
import de.s.j.vorsorge_james.db.DbKindDatensatz;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private DbAccessWorker dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbKindDatensatz testMemo = new DbKindDatensatz(120, "Jannis", new Date(04/07/1989));
        Log.d(LOG_TAG, "Inhalt der Testmemo: " + testMemo.toString());

        dataSource = new DbAccessWorker(this);
    }
}
