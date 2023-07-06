package Utilities;

import Persistence.DAO.ConferenzaDao;
import Persistence.DAO.EnteDao;
import Persistence.DAO.SessioneDao;
import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class ConferenzeUtente extends Conferenze {
    private ObservableList<Conferenza> conferenzeUtente;
    public ConferenzeUtente(){
        this.conferenzeUtente = FXCollections.observableArrayList();
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
        for(Conferenza c: conferenzeUtente){
            c.setEnti((ObservableList<Ente>) dao.retrieveEntiOrganizzatori(c));
        }
    }
    public void loadSponsorizzazioni() throws SQLException{
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        for(Conferenza c: conferenzeUtente){
            c.setSponsorizzazioni((ObservableList<Sponsorizzazione>) dao.retrieveSponsorizzazioni(c));
        }
    }
    public void loadSessioni() throws SQLException {
        SessioneDao sessionedao = new SessioneDao();
        for(Conferenza c: conferenzeUtente){
            c.setSessioni((ObservableList<Sessione>) sessionedao.retrieveSessioniByConferenza(c));
        }
    }
    public ObservableList<Conferenza> getConferenzeUtente() {
        return conferenzeUtente;
    }
    public void setConferenzeUtente(ObservableList<Conferenza> conferenzeUtente) {
        this.conferenzeUtente = conferenzeUtente;
    }
    public void addConferenza(Conferenza conferenza) throws SQLException {
        ConferenzaDao d = new ConferenzaDao();
        int id = d.saveConferenza(conferenza);
        conferenza.setId_conferenza(id);
        conferenzeUtente.add(conferenza);
    }

    public void removeConferenza(Conferenza conferenza) throws SQLException {
        ConferenzaDao d = new ConferenzaDao();
        d.deleteConferenza(conferenza);
        conferenzeUtente.remove(conferenza);
    }
}
