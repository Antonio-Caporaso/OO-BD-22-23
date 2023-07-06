package Persistence.Entities.Conferenze;


public class EventoSociale extends Activity {
    private int id_evento;
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
