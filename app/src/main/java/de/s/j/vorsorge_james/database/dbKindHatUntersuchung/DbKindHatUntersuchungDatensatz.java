package de.s.j.vorsorge_james.database.dbKindHatUntersuchung;

import java.util.Date;

public class DbKindHatUntersuchungDatensatz {
    private long idKind;
    private long idUnterschung;
    private String termin;
    private String arzt;

    public DbKindHatUntersuchungDatensatz(long idKind, long idUnterschung, String termin, String arzt) {
        this.idKind = idKind;
        this.idUnterschung = idUnterschung;
        this.termin = termin;
        this.arzt = arzt;
    }

    public long getIdKind() {
        return idKind;
    }

    public void setIdKind(long idKind) {
        this.idKind = idKind;
    }

    public long getIdUnterschung() {
        return idUnterschung;
    }

    public void setIdUnterschung(long idUnterschung) {
        this.idUnterschung = idUnterschung;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public String getArzt() {
        return arzt;
    }
}
