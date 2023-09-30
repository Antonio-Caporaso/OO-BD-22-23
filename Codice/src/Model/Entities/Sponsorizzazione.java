package Model.Entities;

import java.util.Objects;

public class Sponsorizzazione {
    private Conferenza conferenza;
    private double contributo;
    private Sponsor sponsor;
    private String valuta;

    public Sponsorizzazione(Sponsor s, Conferenza conferenza, double contributo, String valuta) {
        this.sponsor = s;
        this.conferenza = conferenza;
        this.contributo = contributo;
        this.valuta = valuta;
    }

    public Sponsorizzazione() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sponsorizzazione that = (Sponsorizzazione) o;
        return Objects.equals(conferenza, that.conferenza) && Objects.equals(sponsor, that.sponsor);
    }

    public String getCodiceValuta() {
        if (valuta.equals("$"))
            return "USD";
        else if (valuta.equals("€"))
            return "EUR";
        else if (valuta.equals("£"))
            return "GBP";
        else if (valuta.equals("¥"))
            return "JPY";
        else if (valuta.equals("CA$"))
            return "CAD";
        else return null;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
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

    public String getValuta() {
        return valuta;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public void setValuta(String valuta) {
        if(valuta.equals("USD"))
            this.valuta="$";
        else if(valuta.equals("EUR"))
            this.valuta="€";
        else if (valuta.equals("GBP"))
            this.valuta="£";
        else if (valuta.equals("JPY"))
            this.valuta="¥";
        else if(valuta.equals("CAD"))
            this.valuta="CA$";
        else
            this.valuta=valuta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(conferenza, sponsor);
    }

    @Override
    public String toString() {
        return sponsor + "(" + contributo + " " + valuta + ")";
    }
}