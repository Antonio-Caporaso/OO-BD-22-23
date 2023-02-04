package Model.organizzazione;

import Model.conferenza.Conferenza;
import Model.organizzazione.Sponsor;

import java.util.Date;

public class Sponsorizzazione {

    private Sponsor sponsor;
    private Conferenza conferenza;
    private Date inizioSponsorizzazione;
    private Date fineSponsorizzazione;
    private double contributo;

    public Date getInizioSponsorizzazione() {
        return inizioSponsorizzazione;
    }

    public void setInizioSponsorizzazione(Date inizioSponsorizzazione) {
        this.inizioSponsorizzazione = inizioSponsorizzazione;
    }

    public Date getFineSponsorizzazione() {
        return fineSponsorizzazione;
    }

    public void setFineSponsorizzazione(Date fineSponsorizzazione) {
        this.fineSponsorizzazione = fineSponsorizzazione;
    }

    public double getContributo() {
        return contributo;
    }

    public void setContributo(double contributo) {
        this.contributo = contributo;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }
}