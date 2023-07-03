package Persistence.Entities.Conferenze;
import Persistence.Entities.organizzazione.Organizzatore;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class Sessione {
    private int sessioneID;
    private Timestamp inizio;
    private Timestamp fine;
    private String titolo;
    private Sala locazione;
    private Organizzatore coordinatore;
    private Conferenza conferenza;
    private Programma programma;


    public Sessione(){}

    public Sessione(int sessioneID, Timestamp inizio, Timestamp fine, String titolo, Sala locazione, Organizzatore coordinatore, Conferenza conferenza, Programma programma) {
        this.sessioneID = sessioneID;
        this.inizio = inizio;
        this.fine = fine;
        this.titolo = titolo;
        this.locazione = locazione;
        this.coordinatore = coordinatore;
        this.conferenza = conferenza;
        this.programma = programma;
    }

    public int getSessioneID() {
        return sessioneID;
    }

    public void setSessioneID(int sessioneID) {
        this.sessioneID = sessioneID;
    }

    public Timestamp getInizio() {
        return inizio;
    }

    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }

    public Timestamp getFine() {
        return fine;
    }

    public void setFine(Timestamp fine) {
        this.fine = fine;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Sala getLocazione() {
        return locazione;
    }

    public void setLocazione(Sala locazione) {
        this.locazione = locazione;
    }

    public Organizzatore getCoordinatore() {
        return coordinatore;
    }

    public void setCoordinatore(Organizzatore coordinatore) {
        this.coordinatore = coordinatore;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sessione sessione = (Sessione) o;

        return Objects.equals(titolo, sessione.titolo);
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    @Override
    public int hashCode() {
        return titolo != null ? titolo.hashCode() : 0;
    }

    @Override
    public String toString() {
        return titolo;
    }
}
