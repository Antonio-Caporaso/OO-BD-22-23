package Model.Utilities;

import Model.DAO.ConferenzaDao;
import Model.DAO.EnteDao;
import Model.DAO.SessioneDao;
import Model.DAO.SponsorizzazioneDAO;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Sessione;
import Model.Entities.Utente;
import Model.Entities.Organizzazione.Ente;
import Model.Entities.Organizzazione.Sponsorizzazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.LinkedList;

public class ConferenzeUtente extends Conferenze {
    private ObservableList<Conferenza> conferenzeUtente;

    public ConferenzeUtente() {
        this.conferenzeUtente = FXCollections.observableArrayList();
    }

    public void addConferenza(Conferenza conferenza) throws SQLException {
        ConferenzaDao d = new ConferenzaDao();
        int id = d.saveConferenza(conferenza);
        conferenza.setId_conferenza(id);
        conferenzeUtente.add(conferenza);
    }

    public ObservableList<Conferenza> getConferenzeUtente() {
        return conferenzeUtente;
    }

    public void setConferenzeUtente(ObservableList<Conferenza> conferenzeUtente) {
        this.conferenzeUtente = conferenzeUtente;
    }

    public void loadConferenzeUtente(Utente user) throws SQLException {
        ConferenzaDao dao = new ConferenzaDao();
        conferenzeUtente.clear();
        conferenzeUtente.addAll(dao.getAllConferenzeByUtente(user));
        loadOrganizzatori();
        loadSponsorizzazioni();
        loadSessioni();
    }

    @Override
    public void loadOrganizzatori() throws SQLException {
        EnteDao dao = new EnteDao();
        for (Conferenza c : conferenzeUtente) {
            LinkedList<Ente> entiList = dao.retrieveEntiOrganizzatori(c);
            ObservableList<Ente> entiObservableList = FXCollections.observableList(entiList);
            c.setEnti(entiObservableList);
        }
    }

    public void loadSessioni() throws SQLException {
        SessioneDao sessionedao = new SessioneDao();
        for (Conferenza c : conferenzeUtente) {
            LinkedList<Sessione> sessioneList = sessionedao.retrieveSessioniByConferenza(c);
            ObservableList<Sessione> sessioneObservableList = FXCollections.observableList(sessioneList);
            c.setSessioni(sessioneObservableList);
        }
    }

    public void loadSponsorizzazioni() throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        for (Conferenza c : conferenzeUtente) {
            LinkedList<Sponsorizzazione> sponsorList = dao.retrieveSponsorizzazioni(c);
            ObservableList<Sponsorizzazione> sponsorObservableList = FXCollections.observableList(sponsorList);
            c.setSponsorizzazioni(sponsorObservableList);
        }
    }

    public void removeConferenza(Conferenza conferenza) throws SQLException {
        ConferenzaDao d = new ConferenzaDao();
        d.deleteConferenza(conferenza);
        conferenzeUtente.remove(conferenza);
    }
}
