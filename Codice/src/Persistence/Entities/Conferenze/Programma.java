package Persistence.Entities.Conferenze;

import Persistence.Entities.partecipanti.Speaker;

import java.util.HashSet;
import java.util.LinkedList;

public class Programma {
    private int programmaID;
    private Sessione sessione;
    private Speaker keynote;
    private HashSet<EventoSociale> eventi;
    private HashSet<Intervallo> intervalli;
    private LinkedList<Intervento> interventi;

    public Programma(Sessione sessione) {
        this.sessione = sessione;
    }

    public Programma() {    }

    public Speaker getKeynote() {
        return keynote;
    }

    public void setKeynote(Speaker keynote) {
        this.keynote = keynote;
    }

    public HashSet<EventoSociale> getEventi() {
        return eventi;
    }

    public void setEventi(HashSet<EventoSociale> eventi) {
        this.eventi = eventi;
    }

    public HashSet<Intervallo> getIntervalli() {
        return intervalli;
    }

    public void setIntervalli(HashSet<Intervallo> intervalli) {
        this.intervalli = intervalli;
    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    public int getProgrammaID() {
        return programmaID;
    }

    public void setProgrammaID(int programmaID) {
        this.programmaID = programmaID;
    }

    public LinkedList<Intervento> getInterventi() {
        return interventi;
    }

    public void setInterventi(LinkedList<Intervento> interventi) {
        this.interventi = interventi;
    }

    public void addIntervento(Intervento intervento){
        if(!interventi.contains(intervento)){
            interventi.add(intervento);
        }
    }

}
