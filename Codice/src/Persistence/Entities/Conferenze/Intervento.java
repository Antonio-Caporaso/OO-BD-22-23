package Persistence.Entities.Conferenze;

import Persistence.Entities.partecipanti.Speaker;

import java.sql.Timestamp;
import java.util.Objects;

public class Intervento extends ActivityModel {
    private String estratto;
    private Timestamp fine;
    private int id_intervento;
    private Timestamp inizio;
    private Programma programma;
    private Speaker speaker;
    private String titolo;

    public Intervento(int id, Timestamp inizio, Timestamp fine, String estratto, Speaker speaker, Programma programma) {
        this.id_intervento = id;
        this.inizio = inizio;
        this.fine = fine;
        this.estratto = estratto;
        this.programma = programma;
        this.speaker = speaker;
    }

    public Intervento() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Intervento that = (Intervento) o;

        return Objects.equals(speaker, that.speaker);
    }

    public String getEstratto() {
        return estratto;
    }

    public void setEstratto(String estratto) {
        this.estratto = estratto;
    }

    public Timestamp getFine() {
        return fine;
    }

    public void setFine(Timestamp fine) {
        this.fine = fine;
    }

    public int getId_intervento() {
        return id_intervento;
    }

    public void setId_intervento(int id_intervento) {
        this.id_intervento = id_intervento;
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

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speaker);
    }

}