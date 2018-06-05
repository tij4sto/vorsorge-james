package de.s.j.vorsorge_james.database.dbKindHatGewichtUndGroesse;

import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;

public class DbKindHatGewichtUndGroesse {

    private int id;
    private String date;
    private int cm;
    private float kg;

    public DbKindHatGewichtUndGroesse(int id, String date, int cm, float kg){
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

    public float getKg() {
        return kg;
    }

    public void setKg(float kg) {
        this.kg = kg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
