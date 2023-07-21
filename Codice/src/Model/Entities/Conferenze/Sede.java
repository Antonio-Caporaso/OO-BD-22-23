package Model.Entities.Conferenze;

import Model.Entities.organizzazione.Indirizzo;

public class Sede {
    private int id_sede;
    private Indirizzo indirizzo;
    private String nomeSede;

    public Sede(int id, String nomeSede, Indirizzo indirizzo) {
        this.id_sede = id;
        this.nomeSede = nomeSede;
        this.indirizzo = indirizzo;

    }

    public Sede() {
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
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

    public int getSedeID() {
        return id_sede;
    }

    public void setSedeID(int sedeID) {
        this.id_sede = sedeID;
    }

    @Override
    public String toString() {
        return nomeSede;
    }
}

