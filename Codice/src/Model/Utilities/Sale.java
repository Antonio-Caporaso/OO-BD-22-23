package Model.Utilities;

import Model.DAO.SalaDao;
import Model.Entities.Sala;
import Model.Entities.Sede;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Sale {
    private ObservableList<Sala> sale;
    private final Sede sede;

    public Sale(Sede sede) {
        this.sede = sede;
        sale = FXCollections.observableArrayList();
    }
    public ObservableList<Sala> getSale() {
        return sale;
    }

    public void loadSaleDisponibili(Timestamp inizio, Timestamp fine) throws SQLException {
        SalaDao dao = new SalaDao();
        sale.clear();
        sale.addAll(dao.retrieveSaleLibere(sede, inizio, fine));
    }
}
