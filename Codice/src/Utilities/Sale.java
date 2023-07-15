package Utilities;

import Persistence.DAO.SalaDao;
import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.Conferenze.Sede;
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

    public void setSale(ObservableList<Sala> sale) {
        this.sale = sale;
    }

    public void loadSale() throws SQLException {
        SalaDao dao = new SalaDao();
        sale.clear();
        sale.addAll(dao.retrieveSaleBySede(sede));
    }

    public void loadSaleDisponibili(Timestamp inizio, Timestamp fine) throws SQLException {
        SalaDao dao = new SalaDao();
        sale.clear();
        sale.addAll(dao.retrieveSaleLibere(sede, inizio, fine));
    }
}
