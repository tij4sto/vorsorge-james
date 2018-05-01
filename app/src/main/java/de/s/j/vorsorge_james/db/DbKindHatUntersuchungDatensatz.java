package de.s.j.vorsorge_james.db;

import java.util.Date;

public class DbKindHatUntersuchungDatensatz {
    private long idKind;
    private long idUnterschung;
    private Date termin;

    public DbKindHatUntersuchungDatensatz(long idKind, long idUnterschung, Date termin) {
        this.idKind = idKind;
        this.idUnterschung = idUnterschung;
        this.termin = termin;
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

    public Date getTermin() {
        return termin;
    }

    public void setTermin(Date termin) {
        this.termin = termin;
    }
}
