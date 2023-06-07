package Persistence.Entities.Conferenze;


public class EventoSociale extends Activity {
    private int id;
    private Programma programma;
    private String tipologia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
