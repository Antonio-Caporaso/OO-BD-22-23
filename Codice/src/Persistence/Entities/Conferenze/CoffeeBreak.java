package Persistence.Entities.Conferenze;

import java.util.Date;

public class CoffeeBreak extends Intervallo{
    private Date durata;
    public CoffeeBreak(Date oraInizio, Date oraFine, Date durata) {
        super(oraInizio, oraFine);
        this.durata = durata;
    }
    public Date getDurata() {
        return durata;
    }
    public void setDurata(Date durata) {
        this.durata = durata;
    }
}
