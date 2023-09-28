package Model.Utilities;

import Exceptions.NoConferencesException;
import Model.DAO.ConferenzaDao;
import Model.DAO.EnteDao;
import Model.DAO.SponsorizzazioneDAO;
import Model.Entities.Conferenza;
import Model.Entities.Sede;
import Model.Entities.Ente;
import Model.Entities.Sponsorizzazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;

public class Conferenze {
    private ObservableList<Conferenza> conferenze;

    public Conferenze() {
        this.conferenze = FXCollections.observableArrayList();

    }

    public void addConferenza(Conferenza conferenza) throws SQLException {
        conferenze.add(conferenza);
    }

    public ObservableList<Conferenza> getConferenze() {
        return conferenze;
    }

    public void loadByDateAndSede(Date inizio, Date fine, Sede sede) throws SQLException, NoConferencesException {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenze.clear();
        conferenze.addAll(conferenzaDao.retrieveByDateIntervalAndSede(inizio, fine, sede));
        if(conferenze.isEmpty())
            throw new NoConferencesException();
    }

    public void loadByDateInterval(Date inizio, Date fine) throws SQLException, NoConferencesException {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenze.clear();
        conferenze.addAll(conferenzaDao.retrieveByDateInterval(inizio, fine));
        if(conferenze.isEmpty())
            throw new NoConferencesException();
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

    public void removeConferenza(Conferenza conferenza) throws SQLException {
        ConferenzaDao d = new ConferenzaDao();
        d.deleteConferenza(conferenza);
        conferenze.remove(conferenza);
    }
}
