package Services;

import Persistence.DAO.SedeDao;
import Persistence.DTO.Conferenze.Sede;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sedi {
    private ObservableList<Sede> sedi;
    private ObservableList<String> nomi;
    public Sedi(){
        this.sedi = FXCollections.observableArrayList();
        this.nomi = FXCollections.observableArrayList();
    }
    public void loadSedi(){
        SedeDao dao = new SedeDao();
        sedi.clear();
        sedi.addAll(dao.retrieveAllSedi());
    }
    public void loadNomiFromDB(){
        SedeDao dao = new SedeDao();
        this.nomi.clear();
        this.nomi.addAll(dao.retrieveNomeSedi());
    }

    public ObservableList<Sede> getSedi() {
        return sedi;
    }

    public void setSedi(ObservableList<Sede> sedi) {
        this.sedi = sedi;
    }

    public ObservableList<String> getNomi() {
        return nomi;
    }

    public void setNomi(ObservableList<String> nomi) {
        this.nomi = nomi;
    }
}
