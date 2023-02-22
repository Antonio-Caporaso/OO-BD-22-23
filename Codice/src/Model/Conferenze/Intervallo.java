package Model.Conferenze;

import java.util.Date;

public class Intervallo {
    private Date oraInizio;
    private Date oraFine;

    public Intervallo(Date oraInizio, Date oraFine) {
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
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
