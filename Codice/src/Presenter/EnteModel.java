package Presenter;

import DAO.EnteDao;
import Model.organizzazione.Ente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EnteModel {
    private ObservableList<String> nomiEnti;
    private ObservableList<Ente> enti;
    public EnteModel(){
        this.nomiEnti = FXCollections.observableArrayList();
        this.enti = FXCollections.observableArrayList();
    }
    public void loadNomiFromDB(){
        EnteDao dao = new EnteDao();
        nomiEnti.clear();
        nomiEnti.addAll(dao.retrieveAllNomiEnti());
    }
    public void loadEnti(){
        EnteDao dao = new EnteDao();
        enti.clear();
        enti.addAll(dao.retrieveEnti());
    }

    public ObservableList<String> getNomiEnti() {
        return nomiEnti;
    }

    public void setNomiEnti(ObservableList<String> nomiEnti) {
        this.nomiEnti = nomiEnti;
    }

    public ObservableList<Ente> getEnti() {
        return enti;
    }

    public void setEnti(ObservableList<Ente> enti) {
        this.enti = enti;
    }
}
