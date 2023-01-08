package entity.conferenza;

import java.util.Date;

public class Sponsorizzazione {
	
	private Date inizioSponsor;
    private Date fineSponsor;
    private double contributo;

    public Date getInizioSponsor() {
        return inizioSponsor;
    }

    public void setInizioSponsor(Date inizioSponsor) {
        this.inizioSponsor = inizioSponsor;
    }

    public Date getFineSponsor() {
        return fineSponsor;
    }

    public void setFineSponsor(Date fineSponsor) {
        this.fineSponsor = fineSponsor;
    }

    public double getContributo() {
        return contributo;
    }

    public void setContributo(double contributo) {
        this.contributo = contributo;
    }
}
