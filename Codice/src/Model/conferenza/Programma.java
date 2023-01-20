package Model.conferenza;
import java.util.HashSet;
import java.util.LinkedList;

public class Programma {
    private Sessione sessione;
    private HashSet<EventoSociale> eventi;
    private HashSet<Intervallo> intervalli;
    public Programma(Sessione sessione){
        this.sessione = sessione;
    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }
