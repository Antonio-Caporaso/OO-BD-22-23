package Model.conferenza;

import java.util.Date;

public class Pranzo extends Intervallo {
    private int idIntervallo;
    private Sala location;
    public Pranzo(Date oraInizio, Date oraFine, Sala location) {
        super(oraInizio, oraFine);
        this.location = location;
    }

    @Override
    public int getIdIntervallo() {
        return idIntervallo;
    }

    @Override
    public void setIdIntervallo(int idIntervallo) {
        this.idIntervallo = idIntervallo;
    }

    public Sala getLocation() {
        return location;
    }

    public void setLocation(Sala location) {
        this.location = location;
    }
}
