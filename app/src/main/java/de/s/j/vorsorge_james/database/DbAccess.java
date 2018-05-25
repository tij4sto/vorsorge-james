package de.s.j.vorsorge_james.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

public class DbAccess {
    private static final String LOG_TAG = DbAccess.class.getSimpleName();

    private SQLiteDatabase db;
    private DbHelper DbHelper;

    public DbAccess(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den DbHelper.");
        this.DbHelper = new DbHelper(context);
        this.db = this.DbHelper.getWritableDatabase();
    }

    public  void open(){
        Log.d(LOG_TAG, "Datenbank Instanz wird angefragt.");
        this.db = this.DbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank Referenz erhalten. Pfad: " + this.db.getPath());
    }

    public void close(){
        DbHelper.close();
        Log.d(LOG_TAG, "Verbindung mittels Access-Worker zur Datenbank geschlossen.");
    }

    public boolean deleteKindDatensatz(int id){
        try {
            Log.d(LOG_TAG, "Löschen funktioniert "+ this.db.delete("kind",  "_id="+id, null));
            return true;
        }

        catch (Exception e) {
            Log.d(LOG_TAG, "Löschen nicht geklappt! " + e.getMessage());
            return false;
        }

    }

    public DbKindDatensatz createKindDatensatz(String name, String geburtstag){
        //Zugriff auf Datenbank öffnen
        this.open();

        //zu speichernde Werte als Paket zusammenfassen
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("geburtstag", geburtstag);

        Log.d(LOG_TAG, " createKindDatensatz() versucht Insert in DB!");

        try {
            //Versuche Werte in DB eintragen. Gibt _id zurück.
            long l = db.insert("Kind", null, values);
            Log.d(LOG_TAG, "Insert geklappt: " + l);

            //Gibt Zeiger von Eintrag zurück (_id = l)
            Cursor cursor = db.query("Kind", new String[]{"name", "geburtstag"}, "_id" + "=" + l, null, null, null, null);

            //Erstelle Kind-Instanz und gebe sie zurück
            DbKindDatensatz kind = findeKindDatensatz(cursor);
            return kind;
        }

        catch (Exception e){
            Log.d(LOG_TAG, "CreateKindDatensatz() meldet: " + e.getMessage());
            return null;
        }
    }

    private DbKindDatensatz findeKindDatensatz(Cursor c){
        try {
            Log.d(LOG_TAG, "findeKindDatensatz() versucht Eintrag mit Cursor zu finden.");

            //Cursor holt sich ID, Name und Geburtstag aus Tabelle als Indizes
            int idId = c.getColumnIndex("_id");
            int idName = c.getColumnIndex("name");
            int idGeburtstag = c.getColumnIndex("geburtstag");

            //Speichern von Cursor Daten als konkrete Daten
            String name = c.getString(idName);
            String geburtstag = c.getString(idGeburtstag);
            int id = c.getInt(idId);
            Date date = new Date(geburtstag);

            DbKindDatensatz kind = new DbKindDatensatz(id, name, date);
            return kind;
        }

        catch (Exception e){
            Log.d(LOG_TAG, "findeKindDatensatz() meldet : " + e.getMessage());
            return null;
        }
    }

    public DbKindDatensatz findKindById(long i){
        try {
            Log.d(LOG_TAG, "findKindByID: Versuche kind mit ID=" + i + " zu finden");
            Cursor cursor = db.query("Kind", new String[]{"name", "geburtstag"}, "_id" + "=" + i, null, null, null, null);
            DbKindDatensatz kind = findeKindDatensatz(cursor);
            if(kind == null) throw new NullPointerException();
            Log.d(LOG_TAG, "Hat geklappt");
            return kind;
        }

        catch (Exception e){
            Log.d(LOG_TAG, "CreateKindDatensatz() meldet: " + e.getMessage());
            return null;
        }
    }

    public List<DbKindDatensatz> getKindListe(){
        List<DbKindDatensatz> kinder = new ArrayList<>();

        Cursor c = this.db.query("kind", new String[]{"_id", "name", "geburtstag"},
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();

        DbKindDatensatz kind;

        while(!(c.isAfterLast())){
            kind = findeKindDatensatz(c);
            kinder.add(kind);
            Log.d(LOG_TAG, "getKindListe() hat Kind " + kind.toString() +" in Liste eingetragen!");
            c.moveToNext();
        }

        c.close();
        return kinder;
    }
}
