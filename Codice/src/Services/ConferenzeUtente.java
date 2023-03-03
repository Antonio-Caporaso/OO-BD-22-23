package Services;

import Persistence.DAO.ConferenzaDao;
import Persistence.DAO.EnteDao;
import Persistence.DAO.SessioneDao;
import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Utente;
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
            c.setOrganizzataDa(dao.retrieveEntiOrganizzatori(c));
        }
    }
    public void loadSponsorizzazioni() throws SQLException{
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        for(Conferenza c: conferenzeUtente){
            c.setSponsorizzataDa(dao.retrieveSponsorizzazioni(c));
        }
    }
    public void loadSessioni() throws SQLException {
        SessioneDao sessionedao = new SessioneDao();
        for(Conferenza c: conferenzeUtente){
            c.setSessioni(sessionedao.retrieveSessioni(c));
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
        d.saveConferenza(conferenza);
        conferenzeUtente.add(conferenza);
    }

    public void removeConferenza(Conferenza conferenza) throws SQLException {
        ConferenzaDao d = new ConferenzaDao();
        d.deleteConferenza(conferenza);
        conferenzeUtente.remove(conferenza);
    }
}
