package de.s.j.vorsorge_james.database.dbUntersuchung;

public class DbUntersuchungDatensatz {

    private long id;
    private String name;
    private int vonTage;
    private int bisTage;
    private String beschreibung;


    public DbUntersuchungDatensatz(long id, String name, int vonTage, int bisTage, String beschreibung) {
        this.id = id;
        this.name = name;
        this.vonTage = vonTage;
        this.bisTage = bisTage;
        this.beschreibung = beschreibung;
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

}