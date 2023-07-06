package Persistence.Entities.organizzazione;

import Persistence.Entities.Conferenze.Conferenza;

public class Sponsorizzazione {
    private Sponsor sponsor;
    private Conferenza conferenza;
    private double contributo;
    private String valuta;

    public Sponsorizzazione(Sponsor s, Conferenza conferenza, double contributo, String valuta) {
        this.sponsor = s;
        this.conferenza = conferenza;
        this.contributo = contributo;
        this.valuta = valuta;
    }
    public Sponsorizzazione() {}

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

    public String getValuta() {
        return valuta;
    }

    public String getCodiceValuta(){
        if(valuta.equals("$"))
            return "USD";
        else if(valuta.equals("€"))
            return "EUR";
        else if(valuta.equals("£"))
            return  "GBP";
        else if(valuta.equals("¥"))
            return "JPY";
        else return null;
    }
    public void setValuta(String valuta){
        this.valuta=valuta;
    }
    @Override
    public String toString() {
        return sponsor +"(" + contributo+" "+valuta+")";
    }
}