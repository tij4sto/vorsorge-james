package de.s.j.vorsorge_james.database.dbKindHatUntersuchung;

import java.util.Date;

public class DbKindHatUntersuchungDatensatz {
    private int idKind;
    private int idUnterschung;
    private String termin;
    private String arzt;

    public DbKindHatUntersuchungDatensatz(int idKind, int idUnterschung, String termin, String arzt) {
        this.idKind = idKind;
        this.idUnterschung = idUnterschung;
        this.termin = termin;
        this.arzt = arzt;
    }

    public int getIdKind() {
        return idKind;
    }

    public void setIdKind(int idKind) {
        this.idKind = idKind;
    }

    public int getIdUnterschung() {
        return idUnterschung;
    }

    public void setIdUnterschung(int idUnterschung) {
        this.idUnterschung = idUnterschung;
    }

    public String getTermin() {
        return termin;
    }

    public String getArzt() { return this.arzt; }

    public void setTermin(String termin) {
        this.termin = termin;
    }
}
