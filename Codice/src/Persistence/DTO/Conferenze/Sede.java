package Persistence.DTO.Conferenze;


import java.util.LinkedList;

public class Sede {
    private int sedeID;
    private String indirizzo;
    private String cap;
    private String city;
    private String nomeSede;
    private double costoAffitto;
    private LinkedList<Sala> sale;
    private LinkedList<Conferenza> conferenze;

    public Sede(int id, String nomeSede, String indirizzo, String cap, String city, double costoAffitto) {
        this.sedeID = id;
        this.nomeSede = nomeSede;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.city = city;
        this.costoAffitto = costoAffitto;
    }

    public Sede() {
        sale = new LinkedList<Sala>();
    }

    public int getSedeID() {
        return sedeID;
    }

    public void setSedeID(int sedeID) {
        this.sedeID = sedeID;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNomeSede() {
        return nomeSede;
    }

    public void setNomeSede(String nomeSede) {
        this.nomeSede = nomeSede;
    }

    public double getCostoAffitto() {
        return costoAffitto;
    }

    public void setCostoAffitto(double costoAffitto) {
        this.costoAffitto = costoAffitto;
    }

    public String getIndirizzo() {
        return indirizzo;
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

    public LinkedList<Sala> getSale() {
        return sale;
    }

    public void setSale(LinkedList<Sala> sale) {
        this.sale = sale;
    }

    public LinkedList<Conferenza> getConferenze() {
        return conferenze;
    }

    public void setConferenze(LinkedList<Conferenza> conferenze) {
        this.conferenze = conferenze;
    }

    @Override
    public String toString() {
        return nomeSede;
    }
}

