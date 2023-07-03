package Persistence.Entities.Conferenze;

import java.sql.Timestamp;

public abstract class Activity {
    private Timestamp inizio;
    private Timestamp fine;
    public Activity() {
    }

    public Activity(Timestamp inizio, Timestamp fine) {
        this.inizio = inizio;
        this.fine = fine;
    }

    public Timestamp getInizio() {
        return inizio;
    }

    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }

    public Timestamp getFine() {
        return fine;
    }

    public void setFine(Timestamp fine) {
        this.fine = fine;
    }
}


