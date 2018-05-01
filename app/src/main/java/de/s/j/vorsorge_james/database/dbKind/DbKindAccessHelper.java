package de.s.j.vorsorge_james.database.dbKind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbKindAccessHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DbKindAccessHelper.class.getSimpleName();

    public static final String DB_NAME = "vorsorge-james.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_KIND = "Kind";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GEBURTSTAG = "geburtstag";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_KIND +
            "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_GEBURTSTAG +  " TEXT NOT NULL);";


    public DbKindAccessHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


