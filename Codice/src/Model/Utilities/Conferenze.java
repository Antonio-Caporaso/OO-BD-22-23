package Model.Utilities;

import Model.DAO.ConferenzaDao;
import Model.DAO.EnteDao;
import Model.DAO.SponsorizzazioneDAO;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Sede;
import Model.Entities.Organizzazione.Ente;
import Model.Entities.Organizzazione.Sponsorizzazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;

public class Conferenze {
    private final ObservableList<Conferenza> conferenze;

    public Conferenze() {
        this.conferenze = FXCollections.observableArrayList();

    }

    public void addConferenza(Conferenza conferenza) throws SQLException {
        conferenze.add(conferenza);
    }

    public ObservableList<Conferenza> getConferenze() {
        return conferenze;
    }

    public void loadByDateAndSede(Date inizio, Date fine, Sede sede) throws SQLException {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenze.clear();
        conferenze.addAll(conferenzaDao.retrieveByDateIntervalAndSede(inizio, fine, sede));
    }

    public void loadByDateInterval(Date inizio, Date fine) throws SQLException {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenze.clear();
        conferenze.addAll(conferenzaDao.retrieveByDateInterval(inizio, fine));
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
