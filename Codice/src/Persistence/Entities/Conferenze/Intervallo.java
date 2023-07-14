package Persistence.Entities.Conferenze;

import java.sql.Timestamp;

public class Intervallo extends ActivityModel {
    private int id_intervallo;
    private Timestamp inizio;
    private Timestamp fine;
    private String tipologia;
    private Programma programma;
    public Intervallo(){
    }
    public int getId_intervallo() {
        return id_intervallo;
    }
    public void setId_intervallo(int id_intervallo) {
        this.id_intervallo = id_intervallo;
    }
    public String getTipologia() {
        return tipologia;
    }
    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
    public Programma getProgramma() {
        return programma;
    }
    public void setProgramma(Programma programma) {
        this.programma = programma;
    }
    public Timestamp getInizio() {
        return inizio;
    }
    public Timestamp getFine() {
        return fine;
    }
    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }
    public void setFine(Timestamp fine) {
        this.fine = fine;
    }
}
