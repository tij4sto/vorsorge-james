package de.s.j.vorsorge_james.database.dbKind;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

public class DbKindAccessWorker {
    private static final String LOG_TAG = DbKindAccessWorker.class.getSimpleName();

    private SQLiteDatabase db;
    private DbKindAccessHelper dbHelper;

    public DbKindAccessWorker(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        this.dbHelper = new DbKindAccessHelper(context);
    }

    public  void open(){
        Log.d(LOG_TAG, "Datenbank Instanz wird angefragt.");
        this.db = this.dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank Referenz erhalten. Pfad: " + this.db.getPath());
    }

    public void close(){
        dbHelper.close();
        Log.d(LOG_TAG, "Verbindung mittels Access-Worker zur Datenbank geschlossen.");
    }

    public void createKindDatensatz(String name, String geburtstag){
        ContentValues values = new ContentValues();
        values.put(this.dbHelper.COLUMN_NAME, name);
        values.put(this.dbHelper.COLUMN_GEBURTSTAG, geburtstag);

        Log.d(LOG_TAG, "Worker versucht Insert in DB!");
        long l = db.insert(dbHelper.TABLE_KIND, null, values);
        Log.d(LOG_TAG, "Insert geklappt: " + l);
    }
}
