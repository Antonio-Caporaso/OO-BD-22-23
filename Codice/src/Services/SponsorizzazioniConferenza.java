package Services;

import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class SponsorizzazioniConferenza {
    private ObservableList<Sponsorizzazione> sponsorizzazioni;
    private Conferenza conferenza;
    public SponsorizzazioniConferenza(Conferenza conferenza){
        sponsorizzazioni = FXCollections.observableArrayList();
        this.conferenza = conferenza;
        sponsorizzazioni.addAll(conferenza.getSponsorizzataDa());
    }

    public ObservableList<Sponsorizzazione> getSponsorizzazioni() {
        return sponsorizzazioni;
    }
    public void loadSponsorizzazioni() throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        sponsorizzazioni.clear();
        sponsorizzazioni.addAll(dao.retrieveSponsorizzazioni(conferenza));
    }
    public void setSponsorizzazioni(ObservableList<Sponsorizzazione> sponsorizzazioni) {
        this.sponsorizzazioni = sponsorizzazioni;
    }
    public void addSponsorizzazione(Sponsorizzazione s) throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        dao.saveSponsorizzazione(s);
        sponsorizzazioni.add(s);
    }
    public void removeSponsorizzazione(Sponsorizzazione s) throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        dao.removeSponsorizzazione(s);
        sponsorizzazioni.remove(s);
    }
}
