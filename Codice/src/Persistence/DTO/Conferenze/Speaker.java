package Persistence.DTO.Conferenze;

import Persistence.DTO.Conferenze.Intervento;
import Persistence.DTO.Conferenze.Partecipante;

import java.util.LinkedList;

public class Speaker extends Partecipante {
    private int idSpeaker;
    private LinkedList<Intervento> interventi;
    public LinkedList<Intervento> getInterventi() {
        return interventi;
    }
    public void setInterventi(LinkedList<Intervento> interventi) {
        this.interventi = interventi;
    }
    public void addIntervento(Intervento intervento){
        if(!interventi.contains(intervento)){
            interventi.add(intervento);
            intervento.setSpeaker(this);
        }
    }
    public int getIdSpeaker() {
        return idSpeaker;
    }

    public void setIdSpeaker(int idSpeaker) {
        this.idSpeaker = idSpeaker;
    }
}
