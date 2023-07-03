package Persistence.Entities.Conferenze;


import Persistence.Entities.organizzazione.Indirizzo;

import java.util.LinkedList;

public class Sede {
    private int sedeID;
    private String nomeSede;
    private Indirizzo indirizzo;
    private LinkedList<Sala> sale;
    private LinkedList<Conferenza> conferenze;

    public Sede(int id, String nomeSede, Indirizzo indirizzo) {
        this.sedeID = id;
        this.nomeSede = nomeSede;
        this.indirizzo = indirizzo;

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

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
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

