package Model.Utilities;

import Model.DAO.SedeDao;
import Model.Entities.Sede;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Sedi {
    private ObservableList<Sede> sedi;

    public Sedi() {
        this.sedi = FXCollections.observableArrayList();
    }
    public ObservableList<Sede> getSedi() {
        return sedi;
    }

    public void loadSedi() throws SQLException {
        SedeDao dao = new SedeDao();
        sedi.clear();
        sedi.addAll(dao.retrieveAllSedi());
    }

}
