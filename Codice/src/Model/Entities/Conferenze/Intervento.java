package Model.Entities.Conferenze;

import Model.Entities.Partecipanti.Speaker;

import java.sql.Timestamp;
import java.util.Objects;

public class Intervento extends ActivityModel {
    private String estratto;
    private Speaker speaker;
    private String titolo;

    public Intervento(Timestamp inizio, Timestamp fine, String estratto, Speaker speaker, Programma programma) {
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
    public String getType(){
        return titolo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speaker);
    }

}