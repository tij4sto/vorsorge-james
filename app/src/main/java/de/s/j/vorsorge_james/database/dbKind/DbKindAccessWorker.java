package de.s.j.vorsorge_james.database.dbKind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbKindAccessWorker {
    private static final String LOG_TAG = DbKindAccessWorker.class.getSimpleName();

    private SQLiteDatabase db;
    private DbKindAccessHelper dbHelper;

    public DbKindAccessWorker(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        this.dbHelper = new DbKindAccessHelper(context);
    }
}
