package Persistence.Entities.Conferenze;

import java.util.Date;

public class Pranzo extends Intervallo {
    private Sala location;
    public Pranzo(Date oraInizio, Date oraFine, Sala location) {
        super(oraInizio, oraFine);
        this.location = location;
    }

    public Sala getLocation() {
        return location;
    }

    public void setLocation(Sala location) {
        this.location = location;
    }
}
