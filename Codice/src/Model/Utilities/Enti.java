package Model.Utilities;

import Model.DAO.EnteDao;
import Model.Entities.Ente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Enti {
    private ObservableList<Ente> enti;

    public Enti() {
        this.enti = FXCollections.observableArrayList();
    }

    public ObservableList<Ente> getEnti() {
        return enti;
    }

    public void loadEnti() {
        EnteDao dao = new EnteDao();
        enti.clear();
        enti.addAll(dao.retrieveEnti());
    }
}
