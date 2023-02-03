package Model.conferenza;


import java.util.LinkedList;

public class Sede {
    private Indirizzo indirizzo;
    private String nomeSede;
    private int capacity;
    private boolean availability;
    private double costoAffitto;
    private LinkedList<Sala> sale;
    private LinkedList<Conferenza> conferenze;

    public Sede(String nomeSede, Indirizzo indirizzo, int capacita, boolean availability, double costoAffitto, LinkedList<Sala> sale) {
        this.nomeSede = nomeSede;
        this.indirizzo = indirizzo;
        this.capacity = capacita;
        this.availability = availability;
        this.costoAffitto = costoAffitto;
        this.sale = sale;
    }

    public Sede() {
        sale = new LinkedList<Sala>();
    }

    public String getNomeSede() {
        return nomeSede;
    }

    public void setNomeSede(String nomeSede) {
        this.nomeSede = nomeSede;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo=indirizzo;
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
        return indirizzo.getTelefono();
    }

    public void setTelefono(String telefono) {
        indirizzo.setTelefono(telefono);
    }

    public String getCity() {
        return indirizzo.getCity();
    }

    public void setCity(String city) {
        indirizzo.setCity(city);
    }

    public String getCap() {
        return indirizzo.getCap();
    }

    public void setCap(String cap) {
        indirizzo.setCap(cap);
    }
}