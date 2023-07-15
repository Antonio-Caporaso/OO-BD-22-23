package Persistence.Entities.Conferenze;

import Persistence.Entities.partecipanti.Speaker;

import java.sql.Timestamp;
import java.util.Objects;

public class ActivityModel {
    private String descrizione;
    private Timestamp fine;
    private int id_entry;
    private Timestamp inizio;
    private Programma programma;
    private Speaker speaker;
    private String type;

    //Contructor senza Speaker e descrizione
    public ActivityModel() {
    }

    public ActivityModel(int id, String type, Timestamp inizio, Timestamp fine, String descrizione, Programma programma) {
        this.id_entry = id;
        this.type = type;
        this.inizio = inizio;
        this.descrizione = descrizione;
        this.fine = fine;
        this.programma = programma;
    }

    //Contructor completo
    public ActivityModel(int id, String type, Timestamp inizio, Timestamp fine, String descrizione, Speaker speaker, Programma programma) {
        this.id_entry = id;
        this.type = type;
        this.inizio = inizio;
        this.fine = fine;
        this.descrizione = descrizione;
        this.speaker = speaker;
        this.programma = programma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityModel that = (ActivityModel) o;

        if (id_entry != that.id_entry) return false;
        return Objects.equals(type, that.type);
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Timestamp getFine() {
        return fine;
    }

    public void setFine(Timestamp fine) {
        this.fine = fine;
    }

    public Timestamp getInizio() {
        return inizio;
    }

    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    //Getter and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int result = id_entry;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
