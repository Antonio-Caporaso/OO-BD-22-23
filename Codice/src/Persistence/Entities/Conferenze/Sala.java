package Persistence.Entities.Conferenze;

import Persistence.DAO.SalaDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int salaID;
    private String nomeSala;
    private int capacity;
    private Sede sede;
    private SalaDao salaDao;

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
    public List<String> retreiveNomeSalaByIdSede(int sede){
        salaDao= new SalaDao();
        List<String> sale = new ArrayList<>();
        try {
            sale=salaDao.retrieveNomeSalaBySedeID(sede);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return sale;
    }
    public Sala retriveSalaByIdSedeAndNomeSala(int sedeID,String nomeSala){
        salaDao=new SalaDao();
        Sala sala= new Sala();
        try{
            sala=salaDao.retreiveSalaBySedeIdAndNomeSala(sedeID,nomeSala);
        }catch (SQLException e){
            e.printStackTrace();
        }return sala;
    }


}