package Utilities;

import Persistence.DAO.EnteDao;
import Persistence.Entities.organizzazione.Ente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Enti {
    private ObservableList<Ente> enti;
    public Enti(){
        this.enti = FXCollections.observableArrayList();
    }
    public void loadEnti(){
        EnteDao dao = new EnteDao();
        enti.clear();
        enti.addAll(dao.retrieveEnti());
    }
    public ObservableList<Ente> getEnti() {
        return enti;
    }

    public void setEnti(ObservableList<Ente> enti) {
        this.enti = enti;
    }
}
