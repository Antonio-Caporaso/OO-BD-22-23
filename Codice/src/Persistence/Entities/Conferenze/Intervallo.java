package Persistence.Entities.Conferenze;

public class Intervallo extends Activity {
    private int id_intervallo;
    private String tipologia;
    private Programma programma;

    public Intervallo(){
    }
    public int getId_intervallo() {
        return id_intervallo;
    }

    public void setId_intervallo(int id_intervallo) {
        this.id_intervallo = id_intervallo;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }
}
