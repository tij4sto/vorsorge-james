package de.s.j.vorsorge_james.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbKindHatGewichtUndGroesse.DbKindHatGewichtUndGroesse;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

/**
 * Die Klasse um die Datenbankzugriffe zu ermöglichen.
 */
public class DbAccess {
    private static final String LOG_TAG = DbAccess.class.getSimpleName();

    private SQLiteDatabase db;
    private DbHelper DbHelper;

    public DbAccess(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den DbHelper.");
        this.DbHelper = new DbHelper(context);
        this.db = this.DbHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    public void open() {
        Log.d(LOG_TAG, "Datenbank Instanz wird angefragt.");
        this.db = this.DbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank Referenz erhalten. Pfad: " + this.db.getPath());
    }

    public void close() {
        DbHelper.close();
        Log.d(LOG_TAG, "Verbindung mittels Access-Worker zur Datenbank geschlossen.");
    }

    public boolean deleteKindDatensatzById(int id) {
        try {
            Log.d(LOG_TAG, "Löschen funktioniert " + this.db.delete("kind", "_id=" + id, null));
            return true;
        } catch (Exception e) {
            Log.d(LOG_TAG, "Löschen nicht geklappt! " + e.getMessage());
            return false;
        }

    }

    public DbKindDatensatz createKindDatensatz(String name, String geburtstag) {
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
            Cursor cursor = db.query("Kind", new String[]{"_id", "name", "geburtstag"}, "_id" + "=" + l, null, null, null, null);
            cursor.moveToFirst();
            //Erstelle Kind-Instanz und gebe sie zurück
            DbKindDatensatz kind = getKindDatensatzByCursor(cursor);
            cursor.close();
            return kind;
        } catch (Exception e) {
            Log.d(LOG_TAG, "CreateKindDatensatz() meldet: " + e.getMessage());
            return null;
        }
    }

    public boolean saveGewichtUndGroesseInDb(int idKind, String datum, int cm, float kg) {
        this.open();
        ContentValues values = new ContentValues();
        values.put("_id_kind", idKind);
        values.put("_datum", datum);
        values.put("groesse", cm);
        values.put("gewicht", kg);

        try {
            long l = db.insert("Kind_hat_Gewicht_und_Groesse", null, values);
            if (l >= 0) return true;
        } catch (Exception e) {
            Log.d("SaveGewicht()", e.getMessage());
        }

        return false;
    }

    private DbKindHatGewichtUndGroesse getGewichtUndGroesseByCursor(Cursor c) {
        int idIdK = c.getColumnIndex("_id_kind");
        int idDatum = c.getColumnIndex("_datum");
        int idCm = c.getColumnIndex("groesse");
        int idKg = c.getColumnIndex("gewicht");

        int idK = c.getInt(idIdK);
        String datum = c.getString(idDatum);
        int cm = c.getInt(idCm);
        float kg = c.getFloat(idKg);

        DbKindHatGewichtUndGroesse data = new DbKindHatGewichtUndGroesse(idK, datum, cm, kg);
        return data;
    }

    public DbKindHatGewichtUndGroesse getGewichtUndGrößeByID(int idKind) {
        try {
            Cursor c = db.query("Kind_hat_Gewicht_und_Groesse", new String[]{"_id_kind", "_datum", "cm", "kg"},
                    null,
                    null,
                    null,
                    null,
                    null);
            c.moveToFirst();
            DbKindHatGewichtUndGroesse data = getGewichtUndGroesseByCursor(c);
            c.close();
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public DbKindHatUntersuchungDatensatz getKindHatUntersuchungByPK(int idK, int idU) {
        try {
            Cursor c = db.query(
                    "Kind_hat_Untersuchung", new String[]{"_id_kind", "_id_untersuchung", "termin", "arzt"},
                    "_id_kind=" + idK + " AND _id_untersuchung=" + idU,
                    null,
                    null,
                    null,
                    null);
            c.moveToFirst();
            DbKindHatUntersuchungDatensatz data = getKindHatUntersuchungByCursor(c);
            c.close();
            return data;
        } catch (Exception e) {
            Log.d("GetKindUntersuchungByPK", e.getMessage());
            return null;
        }
    }

    private DbKindHatUntersuchungDatensatz getKindHatUntersuchungByCursor(Cursor c) {
        int ididK = c.getColumnIndex("_id_kind");
        int ididU = c.getColumnIndex("_id_untersuchung");
        int idtermin = c.getColumnIndex("termin");
        int idarzt = c.getColumnIndex("arzt");

        int idK = c.getInt(ididK);
        int idU = c.getInt(ididU);
        String termin = c.getString(idtermin);
        String arzt = c.getString(idarzt);

        DbKindHatUntersuchungDatensatz data = new DbKindHatUntersuchungDatensatz(idK, idU, termin, arzt);
        return data;
    }

    public boolean createKindHatUntersuchungDatensatz(int idKind, int idUntersuchung, String arzt, String datum) {
        this.open();

        ContentValues values = new ContentValues();
        values.put("_id_kind", idKind);
        values.put("_id_untersuchung", idUntersuchung);
        values.put("arzt", arzt);
        values.put("termin", datum);


        try {
            long i = db.insert("Kind_hat_Untersuchung", null, values);
            if (i < 0)
                return false;
            return true;
        } catch (Exception e) {
            Log.d("KindHatUntersuchung", e.getMessage());
            return false;
        }
    }

    private DbKindDatensatz getKindDatensatzByCursor(Cursor c) {
        try {
            Log.d(LOG_TAG, "getKindDatensatzByCursor() versucht Eintrag mit Cursor zu finden.");

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
        } catch (Exception e) {
            Log.d(LOG_TAG, "getKindDatensatzByCursor() meldet : " + e.getMessage());
            return null;
        }
    }

    public DbKindDatensatz getKindDatensatzById(long i) {
        try {
            Log.d(LOG_TAG, "findKindByID: Versuche kind mit ID=" + i + " zu finden");
            Cursor cursor = db.query("Kind", new String[]{"_id", "name", "geburtstag"}, "_id" + "=" + i, null, null, null, null);
            cursor.moveToFirst();
            DbKindDatensatz kind = getKindDatensatzByCursor(cursor);
            cursor.close();
            if (kind == null) throw new NullPointerException();
            Log.d(LOG_TAG, "Hat geklappt");
            return kind;
        } catch (Exception e) {
            Log.d(LOG_TAG, "CreateKindDatensatz() meldet: " + e.getMessage());
            return null;
        }
    }

    /**
     *
     * @param idK id des Kindes
     * @param idU id der Untersuchung
     * @return gibt true, wenn Kind in Untersuchung eingetragen ist.
     */
    public boolean isKindInUntersuchung(int idK, int idU){
        for(DbKindHatUntersuchungDatensatz data : getKindHatUntersuchungListe()){
            if(idK == data.getIdKind() && idU == data.getIdUnterschung()) return true;
        }

        return false;
    }

    public List<DbUntersuchungDatensatz> getBenoetigteUntersuchungenOhneTermine(DbKindDatensatz kind){
        List<DbUntersuchungDatensatz>
                untersuchungen = DbUntersuchungTyp.getBenoetigteUntersuchungenByKind(kind),
                removeUntersucungen = new LinkedList<>();

        DbUntersuchungTyp.getBenoetigteUntersuchungenByKind(kind);

        LinkedList<DbUntersuchungDatensatz> removeUntersuchungen = new LinkedList<>();
        for(DbUntersuchungDatensatz untersuchung : untersuchungen){

            if(isKindInUntersuchung(kind.getId(), untersuchung.getId())){
                removeUntersuchungen.add(untersuchung);
            }
        }
        for (DbUntersuchungDatensatz untersuchung: removeUntersuchungen) {
            untersuchungen.remove(untersuchung);
        }

        return untersuchungen;

    }

    public List<DbKindHatUntersuchungDatensatz> getKindHatUntersuchungListe() {
        List<DbKindHatUntersuchungDatensatz> liste = new ArrayList<>();

        Cursor c = this.db.query("Kind_hat_Untersuchung", new String[]{"_id_kind", "_id_untersuchung", "termin", "arzt"},
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        DbKindHatUntersuchungDatensatz data;

        while (!(c.isAfterLast())) {
            data = getKindHatUntersuchungByCursor(c);
            liste.add(data);
            c.moveToNext();
        }
        c.close();
        return liste;
    }

    public List<DbKindDatensatz> getKindListe() {
        List<DbKindDatensatz> kinder = new ArrayList<>();

        Cursor c = this.db.query("kind", new String[]{"_id", "name", "geburtstag"},
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();

        DbKindDatensatz kind;

        while (!(c.isAfterLast())) {
            kind = getKindDatensatzByCursor(c);
            kinder.add(kind);
            Log.d(LOG_TAG, "getKindListe() hat Kind " + kind.toString() + " in Liste eingetragen!");
            c.moveToNext();
        }

        c.close();
        return kinder;
    }

    public List<DbKindHatGewichtUndGroesse> getGewichtGroesseListe(int idK) {
        List<DbKindHatGewichtUndGroesse> liste = new ArrayList<>();
        Cursor c = db.query("Kind_hat_Gewicht_und_Groesse", new String[]{"_id_kind", "_datum", "gewicht", "groesse"},
                "_id_kind=" + idK,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        DbKindHatGewichtUndGroesse data;

        while (!(c.isAfterLast())) {
            data = getGewichtUndGroesseByCursor(c);
            liste.add(data);
            c.moveToNext();
        }

        return liste;
    }

    public boolean updateGewichtUndGroesse(int idK, String date, int cm, float gewicht) {
        ContentValues values = new ContentValues();
        values.put("_id_kind", idK);
        values.put("_datum", date);
        values.put("groesse", cm);
        values.put("gewicht", gewicht);
        int b = db.update("Kind_hat_Gewicht_und_Groesse", values, "_id_kind=" + idK + " AND _datum=" + DatabaseUtils.sqlEscapeString(date), null);
        if (b > 0)
            return true;
        return false;
    }

    public boolean updateKindHatUntersuchung(int idKind, int idUntersuchung, String arzt, String datum){
        ContentValues values = new ContentValues();
        values.put("_id_kind", idKind);
        values.put("_id_untersuchung", idUntersuchung);
        values.put("arzt", arzt);
        values.put("termin", datum);
        int b = db.update("Kind_hat_Untersuchung", values, "_id_kind=" + idKind + " AND _id_untersuchung=" + idUntersuchung, null);

        if(b > 0)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "DB Access Vorsorge-James " + db.toString();
    }
}
