package Model.partecipanti;

import Model.conferenza.Intervento;

import java.util.LinkedList;

public class Speaker extends Partecipante{
    private int idSpeaker;
    private LinkedList<Intervento> interventi;
    private boolean isKeynote;

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
    public boolean isKeynote() {
        return isKeynote;
    }

    public void setKeynote(boolean flag) {
        isKeynote = flag;
    }
}
