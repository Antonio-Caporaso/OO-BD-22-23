package Persistence.DTO.Conferenze;

import java.time.LocalTime;
import java.util.Objects;

public class Intervento {
    private int interventoID;
    private LocalTime orarioIntervento;
    private String estratto;
    private Speaker speaker;

    public Intervento(LocalTime orario, String estratto, Speaker speaker) {
        this.orarioIntervento = orario;
        this.estratto = estratto;
        this.speaker = speaker;
    }

    public int getInterventoID() {
        return interventoID;
    }

    public void setInterventoID(int interventoID) {
        this.interventoID = interventoID;
    }

    public void setSpeaker(Speaker speaker){
        this.speaker = speaker;
    }

    public Speaker getSpeaker(){
        return speaker;
    }

    public LocalTime getOrarioIntervento() {
        return orarioIntervento;
    }

    public void setOrarioIntervento(LocalTime orarioIntervento) {
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