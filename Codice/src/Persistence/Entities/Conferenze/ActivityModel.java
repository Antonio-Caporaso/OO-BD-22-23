package Persistence.Entities.Conferenze;

import Persistence.Entities.partecipanti.Speaker;

import java.sql.Timestamp;

public class ActivityModel {
    private String appuntamento;
    private Timestamp inizio;
    private Timestamp fine;
    private String descrizione;
    private Speaker speaker;
    //Contructor senza Speaker e descrizione
    public ActivityModel(String appuntamento, Timestamp inizio, Timestamp fine) {
        this.appuntamento = appuntamento;
        this.inizio = inizio;
        this.fine = fine;
    }
    //Contructor completo
    public ActivityModel(String appuntamento, Timestamp inizio, Timestamp fine, String descrizione, Speaker speaker) {
        this.appuntamento = appuntamento;
        this.inizio = inizio;
        this.fine = fine;
        this.descrizione = descrizione;
        this.speaker = speaker;
    }

    //Getter and Setters
    public String getAppuntamento() {
        return appuntamento;
    }
    public void setAppuntamento(String appuntamento) {
        this.appuntamento = appuntamento;
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
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public Speaker getSpeaker() {
        return speaker;
    }
    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
}
