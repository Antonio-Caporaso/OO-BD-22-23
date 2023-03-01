package Persistence.Entities.Conferenze;

import Persistence.Entities.partecipanti.Speaker;

import java.time.LocalTime;
import java.util.Objects;

public class Intervento {
    private int interventoID;
    private LocalTime orario;
    private String estratto;
    private Speaker speaker;

    public Intervento(LocalTime orario, String estratto, Speaker speaker) {
        this.orario = orario;
        this.estratto = estratto;
        this.speaker = speaker;
    }

    public void setSpeaker(Speaker speaker){
        this.speaker = speaker;
    }

    public Speaker getSpeaker(){
        return speaker;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
    }

    public String getEstratto() {
        return estratto;
    }

    public void setEstratto(String estratto) {
        this.estratto = estratto;
    }

    public int getInterventoID() {
        return interventoID;
    }

    public void setInterventoID(int interventoID) {
        this.interventoID = interventoID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Intervento that = (Intervento) o;

        if (!Objects.equals(orario, that.orario)) return false;
        return Objects.equals(speaker, that.speaker);
    }

    @Override
    public int hashCode() {
        int result = orario != null ? orario.hashCode() : 0;
        result = 31 * result + (speaker != null ? speaker.hashCode() : 0);
        return result;
    }
}