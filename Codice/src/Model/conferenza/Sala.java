package Model.conferenza;

public class Sala {
    private int idSala;
    private String nomeSala;
    private int capacity;
    private boolean prenotata;
    private Sede sede;

    public Sala(String nomeSala, int capacity, boolean prenotata, Sede sede) {
        this.nomeSala = nomeSala;
        this.capacity = capacity;
        this.prenotata = prenotata;
        this.sede = sede;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
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
    public boolean isPrenotata() {
        return prenotata;
    }
    public void setPrenotata(boolean prenotata) {
        this.prenotata = prenotata;
    }
    public void setSede(Sede sede) {
        this.sede = sede;
    }


}