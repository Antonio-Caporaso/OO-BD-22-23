package Model.Conferenze;

import Model.Conferenze.Conferenza.Conferenza;
import Model.partecipanti.Chair;

import java.util.Date;
import java.util.Objects;

public class Sessione {
    private int sessioneID;
    private Date dataInizio;
    private Date dataFine;
    private String titolo;
    private Sala locazione;
    private Chair coordinatore;
    private Conferenza conferenza;

    public Sessione(){}
    public Sessione(Date dataInizio, Date dataFine, String titolo) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.titolo = titolo;
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

    public Chair getCoordinatore() {
        return coordinatore;
    }

    public void setCoordinatore(Chair coordinatore) {
        this.coordinatore = coordinatore;
        coordinatore.addSessioneGestita(this);
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

    @Override
    public int hashCode() {
        return titolo != null ? titolo.hashCode() : 0;
    }
}
