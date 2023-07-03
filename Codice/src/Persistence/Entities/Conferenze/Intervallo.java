package Persistence.Entities.Conferenze;

import java.sql.Timestamp;

public class Intervallo extends Activity {
    private int id;
    private String tipologia;
    private Programma programma;

    public Intervallo(){
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
