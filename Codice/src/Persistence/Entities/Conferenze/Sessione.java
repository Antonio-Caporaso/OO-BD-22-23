package Persistence.Entities.Conferenze;
import Persistence.Entities.organizzazione.Organizzatore;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class Sessione {
    private int sessioneID;
    private Date dataInizio;
    private Date dataFine;
    private String titolo;
    private Sala locazione;
    private Organizzatore coordinatore;
    private Conferenza conferenza;
    private Programma programma;
    private Time orarioInizio;
    private Time orarioFine;


    public Sessione(){}
    public Sessione(String titolo, Date dataInizio, Date dataFine, Sala sala, Conferenza conferenza, Time orarioInizio, Time orarioFine) {
        this.titolo = titolo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.locazione=sala;
        this.conferenza=conferenza;
        this.orarioInizio=orarioInizio;
        this.orarioFine=orarioFine;
    }

    public Sessione(Date dataInizio, Date dataFine, String titolo, Sala locazione, Organizzatore coordinatore, Conferenza conferenza, Programma programma, Time orarioInizio, Time orarioFine) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.titolo = titolo;
        this.locazione = locazione;
        this.coordinatore = coordinatore;
        this.conferenza = conferenza;
        this.programma = programma;
        this.orarioInizio = orarioInizio;
        this.orarioFine = orarioFine;
    }

    public int getSessioneID() {
        return sessioneID;
    }

    public void setSessioneID(int sessioneID) {
        this.sessioneID = sessioneID;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
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
    public Time getOrarioInizio() {
        return orarioInizio;
    }

    public void setOrarioInizio(Time orarioInizio) {
        this.orarioInizio = orarioInizio;
    }

    public Time getOrarioFine() {
        return orarioFine;
    }

    public void setOrarioFine(Time orarioFine) {
        this.orarioFine = orarioFine;
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
