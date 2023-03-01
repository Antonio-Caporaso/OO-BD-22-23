package Persistence.Entities.Conferenze;

import Persistence.Entities.partecipanti.Speaker;

import java.util.Date;
import java.util.Objects;

public class Intervento {
    private int interventoID;
    private Date orarioIntervento;
    private String estratto;
    private Speaker speaker;

    public Intervento(Date orarioIntervento, String estratto, Speaker speaker) {
        this.orarioIntervento = orarioIntervento;
        this.estratto = estratto;
        this.speaker = speaker;
    }

    public void setSpeaker(Speaker speaker){
        this.speaker = speaker;
    }

    public Speaker getSpeaker(){
        return speaker;
    }

    public Date getOrarioIntervento() {
        return orarioIntervento;
    }

    public void setOrarioIntervento(Date orarioIntervento) {
        this.orarioIntervento = orarioIntervento;
    }

    public String getEstratto() {
        return estratto;
    }

    public void setEstratto(String estratto) {
        this.estratto = estratto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intervento that = (Intervento) o;
        return Objects.equals(orarioIntervento, that.orarioIntervento) && Objects.equals(estratto, that.estratto) && Objects.equals(speaker, that.speaker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orarioIntervento, estratto, speaker);
    }
}