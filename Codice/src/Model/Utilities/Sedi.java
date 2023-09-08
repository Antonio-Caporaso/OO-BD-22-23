package Model.Utilities;

import Model.DAO.SedeDao;
import Model.Entities.Conferenze.Sede;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Sedi {
    private ObservableList<String> nomi;
    private ObservableList<Sede> sedi;

    public Sedi() {
        this.sedi = FXCollections.observableArrayList();
        this.nomi = FXCollections.observableArrayList();
    }

    public ObservableList<String> getNomi() {
        return nomi;
    }

    public void setNomi(ObservableList<String> nomi) {
        this.nomi = nomi;
    }

    public ObservableList<Sede> getSedi() {
        return sedi;
    }

    public void setSedi(ObservableList<Sede> sedi) {
        this.sedi = sedi;
    }

    public void loadSedi() throws SQLException {
        SedeDao dao = new SedeDao();
        sedi.clear();
        sedi.addAll(dao.retrieveAllSedi());
    }

    public void loadSediLibere(Timestamp inizio, Timestamp fine) throws SQLException {
        SedeDao dao = new SedeDao();
        sedi.clear();
        sedi.addAll(dao.retrieveSediLibere(inizio, fine));
    }
}
