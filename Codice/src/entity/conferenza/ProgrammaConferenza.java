package entity.conferenza;

import java.sql.Date;
import java.util.Set;

public class ProgrammaConferenza extends Programma {
    private Set<EventoSociale> eventiSociali;
    private Set<Sessione> sessioni;
    private Set<Intervallo> intervalli;
    private Date giorno;
    private Date orarioInizio;
    private Date orarioFine;

    //constructor base
    public ProgrammaConferenza(Set<Sessione> sessioni, Date giorno,Date orarioInizio, Date orarioFine) {
        this.sessioni = sessioni;
        this.giorno = giorno;
        this.orarioInizio= orarioInizio;
        this.orarioFine=orarioFine;
    }

    public void addEventoSociale(EventoSociale eventoSociale) {
        eventiSociali.add(eventoSociale);
    }
    public void addSessione(Sessione sessione) {
        sessioni.add(sessione);
    }
    public void addIntervallo(Intervallo intervallo) {
        intervalli.add(intervallo);
    }

    public Set<EventoSociale> getEventiSociali() {
        return eventiSociali;
    }

    public Set<Sessione> getSessioni() {
        return sessioni;
    }

    public Set<Intervallo> getIntervalli() {
        return intervalli;
    }

    public Date getGiorno() {
        return giorno;
    }

    public void setGiorno(Date giorno) {
        this.giorno = giorno;
    }

    public Date getOrarioInizio() {
        return orarioInizio;
    }

    public void setOrarioInizio(Date orarioInizio) {
        this.orarioInizio = orarioInizio;
    }

    public Date getOrarioFine() {
        return orarioFine;
    }

    public void setOrarioFine(Date orarioFine) {
        this.orarioFine = orarioFine;
    }
}


