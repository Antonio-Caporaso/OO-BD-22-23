package Model.partecipanti;

import Model.Conferenze.Intervento;

import java.util.LinkedList;

public class Speaker extends Partecipante{
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
