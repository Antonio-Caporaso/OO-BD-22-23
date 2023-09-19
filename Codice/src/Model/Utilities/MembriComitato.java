package Model.Utilities;
import Model.DAO.OrganizzatoreDao;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.organizzazione.Organizzatore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class MembriComitato {
    private final Conferenza conferenza;
    private final ObservableList<Organizzatore> membriComitatoLocale;
    private final ObservableList<Organizzatore> membriComitatoScientifico;
    private final ObservableList<Organizzatore> organizzatoriEnti;

    public MembriComitato(Conferenza c) {
        this.conferenza = c;
        this.membriComitatoLocale = FXCollections.observableArrayList();
        this.membriComitatoScientifico = FXCollections.observableArrayList();
        this.organizzatoriEnti = FXCollections.observableArrayList();
    }
    public ObservableList<Organizzatore> getMembriComitatoScientifico() {
        return membriComitatoScientifico;
    }
    public ObservableList<Organizzatore> getMembriComitatoLocale() {
        return membriComitatoLocale;
    }

    public void loadMembriComitatoScientifico() throws SQLException {
        OrganizzatoreDao dao = new OrganizzatoreDao();
        membriComitatoScientifico.clear();
        membriComitatoScientifico.addAll(dao.retrieveOrganizzatoriComitato(conferenza.getComitato_s().getId_comitato()));
    }
    public void loadMembriComitatoLocale() throws SQLException {
        OrganizzatoreDao dao = new OrganizzatoreDao();
        membriComitatoLocale.clear();
        membriComitatoLocale.addAll(dao.retrieveOrganizzatoriComitato(conferenza.getComitato_l().getId_comitato()));
    }
}
