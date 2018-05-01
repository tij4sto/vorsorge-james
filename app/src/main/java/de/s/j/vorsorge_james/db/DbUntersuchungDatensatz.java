package de.s.j.vorsorge_james.db;

import java.util.Date;

public class DbUntersuchungDatensatz {

    private long id;
    private String name;
    private int vonTage;
    private int bisTage;
    private String arzt;


    public DbUntersuchungDatensatz(long id, String name, int vonTage, int bisTage, String arzt) {
        this.id = id;
        this.name = name;
        this.vonTage = vonTage;
        this.bisTage = bisTage;
        this.arzt = arzt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVonTage() {
        return vonTage;
    }

    public void setVonTage(int vonTage) {
        this.vonTage = vonTage;
    }

    public int getBisTage() {
        return bisTage;
    }

    public void setBisTage(int bisTage) {
        this.bisTage = bisTage;
    }

    public String getArzt() {
        return arzt;
    }

    public void setArzt(String arzt) {
        this.arzt = arzt;
    }
}