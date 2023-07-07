package Persistence.Entities.Conferenze;


import java.sql.Timestamp;

public class EventoSociale implements Activity {
    private int id_evento;
    private Timestamp inizio;
    private Timestamp fine;
    private Programma programma;
    private String tipologia;

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
    @Override
    public Timestamp getInizio() {
        return inizio;
    }
    @Override
    public Timestamp getFine() {
        return fine;
    }

    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }

    public void setFine(Timestamp fine) {
        this.fine = fine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventoSociale that = (EventoSociale) o;

        return id_evento == that.id_evento;
    }

    @Override
    public int hashCode() {
        return id_evento;
    }
}
