package Model.conferenza;

import Model.organizzazione.Sponsor;

import java.util.Date;

public class Sponsorizzazione {

    private Sponsor sponsor;
    private Conferenza conferenza;
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