package de.s.j.vorsorge_james.database.dbUntersuchung;

import java.time.LocalDate;
import java.util.List;

import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

public enum DbUntersuchungTyp {

    //Quelle: https://www.bundesgesundheitsministerium.de/themen/praevention/kindergesundheit/frueherkennungsuntersuchung-bei-kindern/?L=0

    U1("Erkennen von lebensbedrohlichen Komplikationen und sofort behandlungsbedürftigen Erkrankungen und Fehlbildungen, Schwangerschafts-, Geburts- und Familienanamnese, Kontrolle von Atmung, Herzschlag, Hautfarbe, Reifezeichen", 0, 0 ),
    U2("Erkennen von angeborenen Erkrankungen und wesentlichen Gesundheitsrisiken, Vermeidung von Komplikationen: Anamnese und eingehende Untersuchung von Organen, Sinnesorganen und Reflexen", 3, 10),
    U3("Prüfung der altersgemäßen Entwicklung der Reflexe, der Motorik, des Gewichts und der Reaktionen, Untersuchung der Organe, Abfrage des Trink-, Verdauungs- und Schlafverhaltens, Untersuchung der Hüftgelenke auf Hüftgelenksdysplasie und -luxation", 28, 35),
    U4("Untersuchung der altersgerechten Entwicklung und Beweglichkeit des Säuglings, der Organe, Sinnesorgane, Geschlechtsorgane und der Haut, Untersuchung von Wachstum, Motorik und Nervensystem", 90, 120),
    U5("Untersuchung der altersgerechten Entwicklung und Beweglichkeit, der Organe, Sinnesorgane, Geschlechtsorgane und der Haut, Untersuchung von Wachstum, Motorik und Nervensystem", 180, 210),
    U6("Untersuchung der altersgemäßen Entwicklung, der Organe, Sinnesorgane (insb. der Augen), Kontrolle des Bewegungsapparates, der Motorik, der Sprache und der Interaktion", 300, 360),
    U7("Untersuchung der altersgemäßen Entwicklung, Erkennen von Sehstörungen, Test der sprachlichen Entwicklung, Feinmotorik und Körperbeherrschung", 640, 732),
    U7a("Schwerpunkt auf altersgerechter Sprachentwicklung, frühzeitige Erkennung von Sehstörungen", 1037, 1098),
    U8("Intensive Prüfung der Entwicklung von Sprache, Aussprache und Verhalten, Untersuchung von Beweglichkeit und Koordinationsfähigkeit, Reflexen, Muskelkraft und Zahnstatus", 1403, 1464),
    U9("Prüfung der Motorik, des Hör- und Sehvermögens und der Sprachentwicklung, um eventuelle Krankheiten und Fehlentwicklungen vor dem Schuleintritt zu erkennen und gegenzuwirken", 1830, 1952),
    J1("Untersuchung des allgemeinen Gesundheitszustands und der Wachstumsentwicklung, der Organe und des Skelettsystems, Erhebung des Impfstatus, Untersuchung des Stands der Pubertätsentwicklung, der seelischen Entwicklung und des Auftretens von psychischen Auffälligkeiten, von Schulleistungsproblemen und gesundheitsgefährdendem Verhalten (Rauchen, Alkohol- und Drogenkonsum), Beratung auf Grundlage des individuellen Risikoprofils des Jugendlichen zu Möglichkeiten und Hilfen zur Vermeidung gesundheitsschädigender Verhaltensweisen und Tipps für eine gesunde Lebensführung", 4745, 5110);

    private String s;
    private int tageVon;
    private int tageBis;

    DbUntersuchungTyp(String s, int tageVon, int tageBis){
        this.s = s;
        this.tageVon = tageVon;
        this.tageBis = tageBis;
    }

    public String getString(){
        return this.s;
    }
    public int getTageVon() { return this.tageVon; }
    public int getTageBis() { return this.tageBis; }

    //public List<DbUntersuchungTyp> checkKindBrauchtUntersuchung(DbKindDatensatz kind){

    //}
}
