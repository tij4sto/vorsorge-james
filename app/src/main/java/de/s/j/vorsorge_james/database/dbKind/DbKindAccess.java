package de.s.j.vorsorge_james.database.dbKind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbKindAccess {
    private static final String LOG_TAG = DbKindAccess.class.getSimpleName();

    private SQLiteDatabase db;
    private DbKindHelper dbHelper;

    private String[] columns = {
            this.dbHelper.COLUMN_ID,
            this.dbHelper.COLUMN_NAME,
            this.dbHelper.COLUMN_GEBURTSTAG
    };

    public DbKindAccess(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        this.dbHelper = new DbKindHelper(context);
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
        long l = db.insert(dbHelper.TABLE_NAME, null, values);
        Log.d(LOG_TAG, "Insert geklappt: " + l);

        Cursor cursor = db.query(this.dbHelper.TABLE_NAME, columns, this.dbHelper.COLUMN_ID + "=" + l, null, null, null, null);
    }
}
