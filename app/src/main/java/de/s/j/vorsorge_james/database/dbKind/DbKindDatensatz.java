package de.s.j.vorsorge_james.database.dbKind;

import java.util.Date;

public class DbKindDatensatz {

    private int id;
    private String name;
    private Date datum;

    public DbKindDatensatz(int id, String name, Date datum){
        this.id = id;
        this.name = name;
        this.datum = datum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String toString(){
        return "\nid: " + this.id + "\nname: " + this.name + "\ndatum: " + this.datum;
    }
}
