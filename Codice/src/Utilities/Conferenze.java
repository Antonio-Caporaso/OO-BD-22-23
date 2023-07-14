package Utilities;

import Persistence.DAO.ConferenzaDao;
import Persistence.DAO.EnteDao;
import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sede;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.sql.SQLException;

public class Conferenze {
    private ObservableList<Conferenza> conferenze;

    public Conferenze() {
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
        for (Conferenza c : conferenze) {
            c.setEnti((ObservableList<Ente>) dao.retrieveEntiOrganizzatori(c));
        }
    }

    public void loadSponsorizzazioni() throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        for (Conferenza c : conferenze) {
            c.setSponsorizzazioni((ObservableList<Sponsorizzazione>) dao.retrieveSponsorizzazioni(c));
        }
    }

    public void addConferenza(Conferenza conferenza) throws SQLException {
        ConferenzaDao d = new ConferenzaDao();
        int id = d.saveConferenza(conferenza);
        conferenza.setId_conferenza(id);
        conferenze.add(conferenza);
    }

    public void removeConferenza(Conferenza conferenza) throws SQLException {
        ConferenzaDao d = new ConferenzaDao();
        d.deleteConferenza(conferenza);
        conferenze.remove(conferenza);
    }

    public void loadBySede(Sede sede) throws SQLException {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenze.clear();
        conferenze.addAll(conferenzaDao.retrieveBySede(sede));
    }
    public void loadByDateInterval(Date inizio, Date fine) throws SQLException {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenze.clear();
        conferenze.addAll(conferenzaDao.retrieveByDateInterval(inizio,fine));
    }
    public void loadByDateAndSede(Date inizio, Date fine, Sede sede) throws SQLException {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenze.clear();
        conferenze.addAll(conferenzaDao.retrieveByDateIntervalAndSede(inizio,fine,sede));
    }
    public ObservableList<Conferenza> getConferenze() {
        return conferenze;
    }
}
