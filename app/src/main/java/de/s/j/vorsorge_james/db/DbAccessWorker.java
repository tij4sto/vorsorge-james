package de.s.j.vorsorge_james.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbAccessWorker {
    private static final String LOG_TAG = DbAccessWorker.class.getSimpleName();

    private SQLiteDatabase db;
    private DbAccessHelper dbHelper;

    public DbAccessWorker(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        this.dbHelper = new DbAccessHelper(context);
    }
}
