package Persistence.Entities.Conferenze;
import Persistence.Entities.organizzazione.Indirizzo;
public class Sede {
    private int id_sede;
    private String nomeSede;
    private Indirizzo indirizzo;
    public Sede(int id, String nomeSede, Indirizzo indirizzo) {
        this.id_sede = id;
        this.nomeSede = nomeSede;
        this.indirizzo = indirizzo;

    }
    public Sede(){}
    public int getSedeID() {
        return id_sede;
    }
    public void setSedeID(int sedeID) {
        this.id_sede = sedeID;
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
    @Override
    public String toString() {
        return nomeSede;
    }
}

