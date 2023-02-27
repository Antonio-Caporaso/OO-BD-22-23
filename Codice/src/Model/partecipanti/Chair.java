package Model.partecipanti;

import Model.Conferenze.Sessione;
import java.util.LinkedList;

public class Chair extends Partecipante{
    private int idChair;
    private LinkedList<Sessione> sessioniGestite;

    public void setSessioniGestite(LinkedList<Sessione> sessioniGestite) {
        this.sessioniGestite = sessioniGestite;
    }

    public int getIdChair() {
        return idChair;
    }

    public void setIdChair(int idChair) {
        this.idChair = idChair;
    }

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
