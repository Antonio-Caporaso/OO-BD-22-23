package Persistence.Entities.organizzazione;

import Persistence.Entities.Conferenze.Conferenza;

public class Sponsorizzazione {
    private Sponsor sponsor;
    private Conferenza conferenza;
    private double contributo;

    public Sponsorizzazione(Sponsor s, Conferenza conferenza, double contributo) {
        this.sponsor = s;
        this.conferenza = conferenza;
        this.contributo = contributo;
    }

    public Sponsorizzazione() {

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

    @Override
    public String toString() {
        return sponsor +", contributo=" + contributo;
    }
}