package Persistence.Entities.Conferenze;

public class Sala {
    private int salaID;
    private String nomeSala;
    private int capacity;
    private Sede sede;

    public Sala(String nomeSala, int capacity, boolean prenotata, Sede sede) {
        this.nomeSala = nomeSala;
        this.capacity = capacity;
        this.sede = sede;
    }

    public Sala() {}

    public int getSalaID() {
        return salaID;
    }

    public void setSalaID(int salaID) {
        this.salaID = salaID;
    }

    public Sede getSede() {
        return sede;
    }

    public String getNomeSala() {
        return nomeSala;
    }
    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setSede(Sede sede) {
        this.sede = sede;
    }
    @Override
    public String toString() {
        return nomeSala;
    }
}