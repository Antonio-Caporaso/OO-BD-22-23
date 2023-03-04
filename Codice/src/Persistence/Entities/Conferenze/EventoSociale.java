package Persistence.Entities.Conferenze;

import java.sql.Timestamp;

public class EventoSociale {
    private int id;
    private Programma programma;
    private String tipologia;
    private Timestamp orario;

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

    public Timestamp getOrario() {
        return orario;
    }

    public void setOrario(Timestamp orario) {
        this.orario = orario;
    }
}