package Model.partecipanti;

import Model.conferenza.Sessione;
import java.util.LinkedList;

public class Chair extends Partecipante{
    private int idchair;
    private LinkedList<Sessione> sessioniGestite;
    public LinkedList<Sessione> getSessioniGestite() {
        return sessioniGestite;
    }
    public void setGestisce(LinkedList<Sessione> sessioniGestite) {
        this.sessioniGestite = sessioniGestite;
    }
    public void addSessioneGestita(Sessione sessione){
        if(!sessioniGestite.contains(sessione)){
            sessioniGestite.add(sessione);
        }
    }
}
