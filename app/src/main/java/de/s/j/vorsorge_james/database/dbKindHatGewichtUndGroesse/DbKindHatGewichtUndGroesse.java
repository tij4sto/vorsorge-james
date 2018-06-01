package de.s.j.vorsorge_james.database.dbKindHatGewichtUndGroesse;

import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;

public class DbKindHatGewichtUndGroesse {

    private int id;
    private String date;
    private int cm;
    private int kg;

    public DbKindHatGewichtUndGroesse(int id, String date, int cm, int kg){
        this.id = id;
        this.date = date;
        this.cm = cm;
        this.kg = kg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCm() {
        return cm;
    }

    public void setCm(int cm) {
        this.cm = cm;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }
}
