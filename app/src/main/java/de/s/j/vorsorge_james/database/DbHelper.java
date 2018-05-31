package de.s.j.vorsorge_james.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DbHelper.class.getSimpleName();
    public static final String DB_NAME = "vorsorge-james.db";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Versuche Query auf DB auszuführen");
            db.execSQL("PRAGMA foreign_keys = ON");
            db.execSQL(DbTyp.KIND.getString());
            db.execSQL(DbTyp.UNTERSUCHUNG.getString());
            db.execSQL(DbTyp.KIND_HAT_UNTERSUCHUNG.getString());

        }
        catch (Exception e){
            Log.e(LOG_TAG, "Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
