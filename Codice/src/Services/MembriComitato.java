package Services;

import Persistence.DAO.OrganizzatoreDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Organizzatore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class MembriComitato {
    private Conferenza conferenza;
    private ObservableList<Organizzatore> membriComitatoScientifico;
    private ObservableList<Organizzatore> membriComitatoLocale;
    public MembriComitato(Conferenza c){
        this.conferenza = c;
        this.membriComitatoLocale= FXCollections.observableArrayList();
        this.membriComitatoScientifico = FXCollections.observableArrayList();
    }

    public void loadMembriComitatoScientifico() throws SQLException {
        OrganizzatoreDao dao = new OrganizzatoreDao();
        membriComitatoScientifico.clear();
        membriComitatoScientifico.addAll(dao.retrieveOrganizzatoriComitato(conferenza.getComitatoScientifico().getComitatoID()));
    }
    public void loadMembriComitatoLocale() throws SQLException{
        OrganizzatoreDao dao = new OrganizzatoreDao();
        membriComitatoLocale.clear();
        membriComitatoLocale.addAll(dao.retrieveOrganizzatoriComitato(conferenza.getComitatoLocale().getComitatoID()));
    }
    public ObservableList<Organizzatore> getMembriComitatoScientifico(){
        return membriComitatoScientifico;
    }

    public ObservableList<Organizzatore> getMembriComitatoLocale(){
        return membriComitatoLocale;
    }
}
