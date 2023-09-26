package Model.Entities;

public class Indirizzo {
    private String cap;
    private String city;
    private String civico;
    private String provincia;
    private String stato;
    private String via;

    public Indirizzo(String via, String civico, String cap, String city, String stato, String provincia) {
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.city = city;
        this.stato = stato;
        this.provincia = provincia;
    }

    public Indirizzo() {

    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    @Override
    public String toString() {
        return via + " " + civico + ", " + city + " (" + provincia + "), " + cap;
    }
}
