package de.s.j.vorsorge_james.database.dbUntersuchung;

public class DbUntersuchungDatensatz {

    private int id;
    private String name;
    private int vonTage;
    private int bisTage;
    private String beschreibung;

    public DbUntersuchungDatensatz(int id, String name, int vonTage, int bisTage, String beschreibung) {
        this.id = id;
        this.name = name;
        this.vonTage = vonTage;
        this.bisTage = bisTage;
        this.beschreibung = beschreibung;
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

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

}