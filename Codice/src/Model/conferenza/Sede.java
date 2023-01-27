package Model.conferenza;


import java.util.HashSet;

public class Sede {

    private String nomeSede;
    private String indirizzo;
    private String city;
    private String cap;
    private String telefono;
    private int capacity;
    private boolean availability;
    private double costoAffitto;
    private HashSet<Sala> sale;
    private HashSet<Conferenza> conferenze;

    public Sede(String nomeSede, String indirizzo, int capacita, boolean availability, double costoAffitto, HashSet<Sala> sale) {
        this.nomeSede = nomeSede;
        this.indirizzo = indirizzo;
        this.capacity = capacita;
        this.availability = availability;
        this.costoAffitto = costoAffitto;
        this.sale = sale;
    }

    public Sede() {
        sale = new HashSet<Sala>();
    }

    public String getNomeSede() {
        return nomeSede;
    }

    public void setNomeSede(String nomeSede) {
        this.nomeSede = nomeSede;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getCapacita() {
        return capacity;
    }

    public void setCapacita(int capacita) {
        this.capacity = capacita;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getCostoAffitto() {
        return costoAffitto;
    }

    public void setCostoAffitto(double costoAffitto) {
        this.costoAffitto = costoAffitto;
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