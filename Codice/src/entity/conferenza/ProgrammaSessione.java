package entity.conferenza;

import java.util.Set;

public class ProgrammaSessione extends Programma {

    private Sessione sessione;
    private Set<Intervento> interventi;
    private Set<Intervallo> intervalli;
    //keynote speaker?
    public Sessione getSessione() {
        return sessione;
    }

    public Set<Intervento> getInterventi() {
        return interventi;
    }

    public Set<Intervallo> getIntervalli() {
        return intervalli;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }
    public void addIntervento(Intervento intervento) {
        interventi.add(intervento);
    }
    public void addIntervallo(Intervallo intervallo) {
        intervalli.add(intervallo);
    }

}
