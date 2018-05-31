package de.s.j.vorsorge_james.database;

public enum DbTyp {
    KIND(
            "CREATE TABLE Kind(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, geburtstag TEXT NOT NULL);"
    ),
    UNTERSUCHUNG(
            "CREATE TABLE Untersuchung(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, von TEXT NOT NULL, bis TEXT NOT NULL);"
    ),
    KIND_HAT_UNTERSUCHUNG(
            "CREATE TABLE Kind_hat_Untersuchung(" +
                    "_id_kind INTEGER NOT NULL, " +
                    "_id_untersuchung INTEGER NOT NULL, " +
                    "termin TEXT NOT NULL, " +
                    "arzt TEXT NOT NULL, " +
                    "PRIMARY KEY(_id_kind, _id_untersuchung), " +
                    "FOREIGN KEY(_id_kind) REFERENCES Kind (_id) ON DELETE CASCADE ON UPDATE NO ACTION);"
    );

    private String s;

    DbTyp(String s){
        this.s = s;
    }

    public String getString(){
        return this.s;
    }
}


