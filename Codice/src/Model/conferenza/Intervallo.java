package Model.conferenza;

import java.util.Date;

public class Intervallo {
    private int idIntervallo;
    private Date oraInizio;
    private Date oraFine;

    public Intervallo(Date oraInizio, Date oraFine) {
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
    }

    public int getIdIntervallo() {
        return idIntervallo;
    }

    public void setIdIntervallo(int idIntervallo) {
        this.idIntervallo = idIntervallo;
    }

    public Date getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(Date oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Date getOraFine() {
        return oraFine;
    }

    public void setOraFine(Date oraFine) {
        this.oraFine = oraFine;
    }

}
