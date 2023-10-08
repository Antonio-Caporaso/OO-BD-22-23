package Model.Utilities;

import Model.Entities.Programma;

import java.sql.Timestamp;

public abstract class ActivityModel {

    protected Timestamp fine;
    protected int id_entry;
    protected Timestamp inizio;
    protected Programma programma;
    protected String type;

    public Timestamp getFine() {
        return fine;
    }

    public void setFine(Timestamp fine) {
        this.fine = fine;
    }

    public int getId_entry() {
        return id_entry;
    }

    public void setId_entry(int id_entry) {
        this.id_entry = id_entry;
    }

    public Timestamp getInizio() {
        return inizio;
    }

    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    public abstract String getType();

    public void setType(String type) {
        this.type = type;
    }
}
