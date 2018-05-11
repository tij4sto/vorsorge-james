package de.s.j.vorsorge_james.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class dbAccess {
    private static final String LOG_TAG = dbAccess.class.getSimpleName();

    private SQLiteDatabase db;
    private dbHelper dbHelper;

    public dbAccess(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        this.dbHelper = new dbHelper(context);
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
        this.open();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("geburtstag", geburtstag);

        Log.d(LOG_TAG, "Worker versucht Insert in DB!");
        try {
            long l = db.insert("Kind", null, values);
            Log.d(LOG_TAG, "Insert geklappt: " + l);
            Cursor cursor = db.query("Kind", new String[]{"name", "geburtstag"}, "_id" + "=" + l, null, null, null, null);
        }

        catch (Exception e){
            Log.d(LOG_TAG, e.getMessage());
        }
    }
}
