package Model.organizzazione.Sponsor;

import Model.Conferenze.Conferenza.Conferenza;
import Model.organizzazione.Sponsor.Sponsor;

public class Sponsorizzazione {
    private Sponsor sponsor;
    private Conferenza conferenza;
    private double contributo;
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