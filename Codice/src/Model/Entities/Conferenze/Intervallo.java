package Model.Entities.Conferenze;

import java.sql.Timestamp;

public class Intervallo extends ActivityModel {
    private Timestamp fine;
    private int id_intervallo;
    private Timestamp inizio;
    private Programma programma;
    private String tipologia;

    public Intervallo() {
    }

    public Timestamp getFine() {
        return fine;
    }

    public void setFine(Timestamp fine) {
        this.fine = fine;
    }

    public int getId_intervallo() {
        return id_intervallo;
    }

    public void setId_intervallo(int id_intervallo) {
        this.id_intervallo = id_intervallo;
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

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
}
