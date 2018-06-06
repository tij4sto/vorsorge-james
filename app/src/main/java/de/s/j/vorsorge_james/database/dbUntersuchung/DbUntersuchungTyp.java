package de.s.j.vorsorge_james.database.dbUntersuchung;

import android.util.Log;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.hilfsklassen.DateFormatter;

public enum DbUntersuchungTyp {

    //Quelle: https://www.bundesgesundheitsministerium.de/themen/praevention/kindergesundheit/frueherkennungsuntersuchung-bei-kindern/?L=0

    U1(1, "U1","Erkennen von lebensbedrohlichen Komplikationen und sofort behandlungsbedürftigen Erkrankungen und Fehlbildungen, Schwangerschafts-, Geburts- und Familienanamnese, Kontrolle von Atmung, Herzschlag, Hautfarbe, Reifezeichen", 0, 1 ),
    U2(2, "U2", "Erkennen von angeborenen Erkrankungen und wesentlichen Gesundheitsrisiken, Vermeidung von Komplikationen: Anamnese und eingehende Untersuchung von Organen, Sinnesorganen und Reflexen", 3, 10),
    U3(3, "U3", "Prüfung der altersgemäßen Entwicklung der Reflexe, der Motorik, des Gewichts und der Reaktionen, Untersuchung der Organe, Abfrage des Trink-, Verdauungs- und Schlafverhaltens, Untersuchung der Hüftgelenke auf Hüftgelenksdysplasie und -luxation", 28, 35),
    U4(4, "U4", "Untersuchung der altersgerechten Entwicklung und Beweglichkeit des Säuglings, der Organe, Sinnesorgane, Geschlechtsorgane und der Haut, Untersuchung von Wachstum, Motorik und Nervensystem", 90, 120),
    U5(5, "U5", "Untersuchung der altersgerechten Entwicklung und Beweglichkeit, der Organe, Sinnesorgane, Geschlechtsorgane und der Haut, Untersuchung von Wachstum, Motorik und Nervensystem", 180, 210),
    U6(6, "U6", "Untersuchung der altersgemäßen Entwicklung, der Organe, Sinnesorgane (insb. der Augen), Kontrolle des Bewegungsapparates, der Motorik, der Sprache und der Interaktion", 300, 360),
    U7(7, "U7", "Untersuchung der altersgemäßen Entwicklung, Erkennen von Sehstörungen, Test der sprachlichen Entwicklung, Feinmotorik und Körperbeherrschung", 640, 732),
    U7a(8, "U7a", "Schwerpunkt auf altersgerechter Sprachentwicklung, frühzeitige Erkennung von Sehstörungen", 1037, 1098),
    U8(9, "U8", "Intensive Prüfung der Entwicklung von Sprache, Aussprache und Verhalten, Untersuchung von Beweglichkeit und Koordinationsfähigkeit, Reflexen, Muskelkraft und Zahnstatus", 1403, 1464),
    U9(10, "U9", "Prüfung der Motorik, des Hör- und Sehvermögens und der Sprachentwicklung, um eventuelle Krankheiten und Fehlentwicklungen vor dem Schuleintritt zu erkennen und gegenzuwirken", 1830, 1952),
    J1(11, "J1", "Untersuchung des allgemeinen Gesundheitszustands und der Wachstumsentwicklung, der Organe und des Skelettsystems, Erhebung des Impfstatus, Untersuchung des Stands der Pubertätsentwicklung, der seelischen Entwicklung und des Auftretens von psychischen Auffälligkeiten, von Schulleistungsproblemen und gesundheitsgefährdendem Verhalten (Rauchen, Alkohol- und Drogenkonsum), Beratung auf Grundlage des individuellen Risikoprofils des Jugendlichen zu Möglichkeiten und Hilfen zur Vermeidung gesundheitsschädigender Verhaltensweisen und Tipps für eine gesunde Lebensführung", 4745, 5110);

    private int id;
    private String n;
    private String s;
    private int tageVon;
    private int tageBis;

    DbUntersuchungTyp(int id, String n, String s, int tageVon, int tageBis){
        this.id = id;
        this.n = n;
        this.s = s;
        this.tageVon = tageVon;
        this.tageBis = tageBis;
    }

    public int getId(){ return this.id; }
    public String getBeschreibung(){
        return this.s;
    }
    public int getTageVon() { return this.tageVon; }
    public int getTageBis() { return this.tageBis; }
    public String getName() { return this.n; }

    public static List<DbUntersuchungDatensatz> getAlleUntersuchungen(){
        List<DbUntersuchungDatensatz> untersuchungen = new ArrayList<>();

        for(DbUntersuchungTyp typ : DbUntersuchungTyp.values()){
            DbUntersuchungDatensatz untersuchung = new DbUntersuchungDatensatz(typ.getId(), typ.getName(), typ.getTageVon(), typ.getTageBis(), typ.getBeschreibung());
            untersuchungen.add(untersuchung);
        }

        return untersuchungen;
    }

    /**
     * Returns all Untersuchungen that are due within the next 40 days for a specified child.
     * @param kind Child that is expected to attend Untersuchungen
     * @return A list of untersuchungen that are due within the next 40 days
     */
    public static List<DbUntersuchungDatensatz> getBenoetigteUntersuchungenByKind(DbKindDatensatz kind){
        List<DbUntersuchungDatensatz> untersuchungen = new ArrayList<>();

        Date geburtstag = kind.getDatum();
        Date heute = new Date();
        long alter = heute.getTime() - geburtstag.getTime();
        alter = alter/ 1000 / 60 / 60 / 24;

        Log.d("Liste", "" + alter);

        for(DbUntersuchungTyp typ : DbUntersuchungTyp.values()){
            if(alter <= typ.tageVon){
                if((alter + 40) >= typ.tageVon){
                    DbUntersuchungDatensatz untersuchung = new DbUntersuchungDatensatz(typ.getId(), typ.getName(), typ.getTageVon(), typ.getTageBis(), typ.getBeschreibung());
                    untersuchungen.add(untersuchung);
                }
            }
        }

        return untersuchungen;
    }

    public static DbUntersuchungDatensatz getUntersuchungDatensatzById(int id){
        for(DbUntersuchungTyp t : DbUntersuchungTyp.values()){
            if((int) t.getId() == id) return new DbUntersuchungDatensatz(t.getId(), t.getName(), t.getTageVon(), t.getTageBis(), t.getBeschreibung());
        }

        return null;
    }

    public static String getZeitraumString(DbUntersuchungDatensatz typ, DbKindDatensatz kind){
        Calendar c = Calendar.getInstance();
        c.setTime(kind.getDatum());
        c.add(Calendar.DAY_OF_YEAR, typ.getVonTage());
        Date von = c.getTime();
        c.setTime(kind.getDatum());
        c.add(Calendar.DAY_OF_YEAR, typ.getBisTage());
        Date bis = c.getTime();

        return DateFormatter.formatDate(von) + " und " + DateFormatter.formatDate(bis);

    }
}
