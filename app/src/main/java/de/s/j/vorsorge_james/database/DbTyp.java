package de.s.j.vorsorge_james.database;

public enum DbTyp {
    KIND(
            "CREATE TABLE Kind(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, geburtstag TEXT NOT NULL);"
    ),
    UNTERSUCHUNG(
            "CREATE TABLE Untersuchung(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, von TEXT NOT NULL, bis TEXT NOT NULL, arzt TEXT NOT NULL);"
    ),
    KING_HAT_UNTERSUCHUNG(
            "CREATE TABLE Kind_hat_Untersuchung(_id_kind INTEGER PRIMARY KEY, _id_untersuchung INTEGER PRIMARY KEY, termin TEXT NOT NULL);"
    );

    private String s;

    DbTyp(String s){
        this.s = s;
    }

    public String getString(){
        return this.s;
    }
}


