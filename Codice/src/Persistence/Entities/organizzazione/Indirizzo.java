package Persistence.Entities.organizzazione;

public class Indirizzo {
    private String via;
    private String civico;
    private String cap;
    private String city;
    private String stato;
    private String provincia;

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

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
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

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
