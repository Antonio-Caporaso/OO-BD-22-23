package Services;

import Persistence.DAO.ConferenzaDao;
import Persistence.DAO.EnteDao;
import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.DTO.Conferenze.Conferenza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class Conferenze {
    private ObservableList<Conferenza> conferenze;
    public Conferenze(){
        this.conferenze = FXCollections.observableArrayList();

    }
    public void loadConferenze() throws SQLException {
        ConferenzaDao dao = new ConferenzaDao();
        conferenze.clear();
        conferenze.addAll(dao.getAllConferenze());
        loadOrganizzatori();
        loadSponsorizzazioni();
    }
    public void loadOrganizzatori() throws SQLException {
        EnteDao dao = new EnteDao();
        for(Conferenza c: conferenze){
            c.setOrganizzataDa(dao.retrieveEntiOrganizzatori(c));
        }
    }
    public void loadSponsorizzazioni() throws SQLException{
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        for(Conferenza c: conferenze){
            c.setSponsorizzataDa(dao.retrieveSponsorizzazioni(c));
        }
    }
    public void addConferenza(Conferenza conferenza) throws SQLException {
        conferenze.add(conferenza);
        ConferenzaDao d = new ConferenzaDao();
        d.saveConferenza(conferenza);
    }

    public void removeConferenza(Conferenza conferenza) throws SQLException {
        this. conferenze.remove(conferenza);
        ConferenzaDao d = new ConferenzaDao();
        d.deleteConferenza(conferenza);
    }
    public ObservableList<Conferenza> getConferenze() {
        return conferenze;
    }
}
