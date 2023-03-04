package Services;

import Persistence.DAO.SalaDao;
import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.Conferenze.Sede;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Sale {
    private ObservableList<Sala> sale;
    private Sede sede;
    public Sale(Sede sede){
        this.sede = sede;
        sale = FXCollections.observableArrayList();
    }
    public void loadSale() throws SQLException {
        SalaDao dao = new SalaDao();
        sale.clear();
        sale.addAll(dao.retrieveSaleBySede(sede));
    }

    public ObservableList<Sala> getSale() {
        return sale;
    }

    public void setSale(ObservableList<Sala> sale) {
        this.sale = sale;
    }
}
