package de.s.j.vorsorge_james.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class dbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = dbHelper.class.getSimpleName();
    public static final String DB_NAME = "vorsorge-james.db";
    public static final int DB_VERSION = 1;

    public dbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Versuche Query auf DB auszuführen");
            db.execSQL(Typ.KIND.getString());
            db.execSQL(Typ.UNTERSUCHUNG.getString());
            db.execSQL(Typ.KING_HAT_UNTERSUCHUNG.getString());
        }
        catch (Exception e){
            Log.e(LOG_TAG, "Ein Fehler ist aufgetreten: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
