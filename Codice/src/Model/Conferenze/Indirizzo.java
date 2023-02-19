package Model.Conferenze;

public class Indirizzo {
    private String indirizzo;
    private String city;
    private String cap;
    private String telefono;

    public Indirizzo() {}

    public Indirizzo(String indirizzo, String city, String cap, String telefono) {
        this.indirizzo = indirizzo;
        this.city = city;
        this.cap = cap;
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }
}