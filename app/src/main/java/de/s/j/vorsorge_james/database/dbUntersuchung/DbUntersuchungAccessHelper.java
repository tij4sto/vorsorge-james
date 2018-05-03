package de.s.j.vorsorge_james.database.dbUntersuchung;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbUntersuchungAccessHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DbUntersuchungAccessHelper.class.getSimpleName();

    public static final String DB_NAME = "vorsorge-james.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "Untersuchung";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VON = "zeitVon";
    public static final String COLUMN_BIS = "zeitBis";
    public static final String COLUMN_ARZT = "arzt";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME +
            "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_VON +  " TEXT NOT NULL, "
            + COLUMN_BIS + " TEXT NOT NULL, "
            + COLUMN_ARZT + " TEXT NOT NULL);";

    public DbUntersuchungAccessHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE);
            Log.d(LOG_TAG,"\nDie Tabelle mit Befehl: \n" + SQL_CREATE + " \nwurde angelegt");
        }

        catch (Exception e){
            Log.e(LOG_TAG, "Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


