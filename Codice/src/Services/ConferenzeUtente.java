package Services;

import Persistence.DAO.ConferenzaDao;
import Persistence.DAO.EnteDao;
import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.DTO.Conferenze.Conferenza;
import Persistence.DTO.organizzazione.Utente;
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
    public ObservableList<Conferenza> getConferenzeUtente() {
        return conferenzeUtente;
    }
    public void setConferenzeUtente(ObservableList<Conferenza> conferenzeUtente) {
        this.conferenzeUtente = conferenzeUtente;
    }
}
