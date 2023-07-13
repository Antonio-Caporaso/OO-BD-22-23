package Persistence.Entities.Conferenze;

import Persistence.Entities.partecipanti.Speaker;

import java.sql.Timestamp;

public class ActivityModel {
    private int id_entry;
    private String type;
    private Timestamp inizio;
    private Timestamp fine;
    private String descrizione;
    private Programma programma;
    private Speaker speaker;
    //Contructor senza Speaker e descrizione
    public ActivityModel(){}
    public ActivityModel(int id,String type, Timestamp inizio, Timestamp fine,String descrizione,Programma programma) {
        this.id_entry = id;
        this.type = type;
        this.inizio = inizio;
        this.descrizione=descrizione;
        this.fine = fine;
        this.programma = programma;
    }
    //Contructor completo
    public ActivityModel(int id,String type, Timestamp inizio, Timestamp fine, String descrizione, Speaker speaker, Programma programma) {
        this.id_entry = id;
        this.type = type;
        this.inizio = inizio;
        this.fine = fine;
        this.descrizione = descrizione;
        this.speaker = speaker;
        this.programma = programma;
    }

    //Getter and Setters
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
