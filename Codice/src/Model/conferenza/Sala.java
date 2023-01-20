package Model.conferenza;

public class Sala {

    private String nomeSala;
    private int capacity;
    private boolean prenotata;
    private Sede sede;


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

    public String toString(){
        return this.getNomeSala()+"\n"+
                "Capacità: "+this.getCapacity();
    }
}