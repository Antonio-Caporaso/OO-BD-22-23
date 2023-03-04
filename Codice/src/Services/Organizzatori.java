package Services;

import Persistence.DAO.EnteDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Ente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Organizzatori {
    private ObservableList<Ente> enti;
    private Conferenza conferenza;
    public Organizzatori(Conferenza conferenza){
        enti = FXCollections.observableArrayList();
        this.conferenza = conferenza;
        enti.addAll(conferenza.getOrganizzataDa());
    }
    public ObservableList<Ente> getEnti() {
        return enti;
    }

    public void setEnti(ObservableList<Ente> enti) {
        this.enti = enti;
    }

    public void addEnte(Ente e) throws SQLException {
        EnteDao dao = new EnteDao();
        dao.saveEnteOrganizzatore(e,conferenza);
        enti.add(e);
    }
    public void removeEnte(Ente e) throws SQLException {
        EnteDao dao = new EnteDao();
        dao.removeEnteOrganizzatore(e,conferenza);
        enti.remove(e);
    }
}
