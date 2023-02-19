package Model.organizzazione.Ente;

import DAO.EnteDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EnteModel {
    private ObservableList<String> nomiEnti;
    public EnteModel(){
        this.nomiEnti = FXCollections.observableArrayList();
    }
    public void loadNomiFromDB(){
        EnteDao dao = new EnteDao();
        nomiEnti.clear();
        nomiEnti.addAll(dao.retrieveAllNomiEnti());
    }

    public ObservableList<String> getNomiEnti() {
        return nomiEnti;
    }

    public void setNomiEnti(ObservableList<String> nomiEnti) {
        this.nomiEnti = nomiEnti;
    }
}
