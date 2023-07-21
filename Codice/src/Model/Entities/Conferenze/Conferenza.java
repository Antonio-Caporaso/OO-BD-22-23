package Model.Entities.Conferenze;

import Exceptions.EntePresenteException;
import Model.DAO.EnteDao;
import Model.DAO.SessioneDao;
import Model.DAO.SponsorizzazioneDAO;
import Model.Entities.Utente;
import Model.Entities.organizzazione.Comitato;
import Model.Entities.organizzazione.Ente;
import Model.Entities.organizzazione.Sponsorizzazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Conferenza {
    private Comitato comitato_l;
    private Comitato comitato_s;
    private String descrizione;
    private ObservableList<Ente> enti;
    private Timestamp fine;
    private int id_conferenza;
    private Timestamp inizio;
    private Utente proprietario;
    private Sede sede;
    private ObservableList<Sessione> sessioni;
    private ObservableList<Sponsorizzazione> sponsorizzazioni;
    private String titolo;

    public Conferenza(int conferenzaID, String titolo, Utente proprietario, Timestamp inizio, Timestamp fine, String descrizione, Comitato comitato_l, Comitato comitato_s, Sede sede) {
        this.id_conferenza = conferenzaID;
        this.titolo = titolo;
        this.proprietario = proprietario;
        this.inizio = inizio;
        this.fine = fine;
        this.descrizione = descrizione;
        this.comitato_l = comitato_l;
        this.comitato_s = comitato_s;
        this.sede = sede;
        sponsorizzazioni = FXCollections.observableArrayList();
        enti = FXCollections.observableArrayList();
        sessioni = FXCollections.observableArrayList();
    }

    public Conferenza() {
        sponsorizzazioni = FXCollections.observableArrayList();
        enti = FXCollections.observableArrayList();
        sessioni = FXCollections.observableArrayList();
    }

    public Conferenza(String nome, Timestamp dataI, Timestamp dataF, String descrizione, Sede sede, Utente user) {
        this.titolo = nome;
        this.inizio = dataI;
        this.fine = dataF;
        this.descrizione = descrizione;
        this.sede = sede;
        this.proprietario = user;
        sponsorizzazioni = FXCollections.observableArrayList();
        enti = FXCollections.observableArrayList();
        sessioni = FXCollections.observableArrayList();
    }

    public void addEnte(Ente e) throws SQLException, EntePresenteException {
        EnteDao dao = new EnteDao();
        if (!enti.contains(e)) {
            dao.saveEnteOrganizzatore(e, this);
            enti.add(e);
        } else {
            throw new EntePresenteException();
        }
    }

    public void addSessione(Sessione sessione) throws SQLException {
        SessioneDao sessioneDao = new SessioneDao();
        sessione.setId_sessione(sessioneDao.saveSessione(sessione));
        sessioni.add(sessione);
    }

    public void addSponsorizzazione(Sponsorizzazione s) throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        dao.saveSponsorizzazione(s);
        sponsorizzazioni.add(s);
    }

    public Comitato getComitato_l() {
        return comitato_l;
    }

    public void setComitato_l(Comitato comitato_l) {
        this.comitato_l = comitato_l;
    }

    public Comitato getComitato_s() {
        return comitato_s;
    }

    public void setComitato_s(Comitato comitato_s) {
        this.comitato_s = comitato_s;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public ObservableList<Ente> getEnti() {
        return enti;
    }

    public void setEnti(ObservableList<Ente> enti) {
        this.enti = enti;
    }

    public Timestamp getFine() {
        return fine;
    }

    public void setFine(Timestamp fine) {
        this.fine = fine;
    }

    public int getId_conferenza() {
        return id_conferenza;
    }

    public void setId_conferenza(int id_conferenza) {
        this.id_conferenza = id_conferenza;
    }

    public Timestamp getInizio() {
        return inizio;
    }

    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public ObservableList<Sessione> getSessioni() {
        return sessioni;
    }

    public void setSessioni(ObservableList<Sessione> sessioni) {
        this.sessioni = sessioni;
    }

    public ObservableList<Sponsorizzazione> getSponsorizzazioni() {
        return sponsorizzazioni;
    }

    public void setSponsorizzazioni(ObservableList<Sponsorizzazione> sponsorizzazioni) {
        this.sponsorizzazioni = sponsorizzazioni;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void loadOrganizzatori() throws SQLException {
        EnteDao dao = new EnteDao();
        enti.clear();
        enti.addAll(dao.retrieveEntiOrganizzatori(this));
    }

    public void loadSessioni() throws SQLException {
        SessioneDao dao = new SessioneDao();
        sessioni.clear();
        sessioni.addAll(dao.retrieveSessioniByConferenza(this));
    }

    public void loadSponsorizzazioni() throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        sponsorizzazioni.clear();
        sponsorizzazioni.addAll(dao.retrieveSponsorizzazioni(this));
    }

    public void removeEnte(Ente e) throws SQLException {
        EnteDao dao = new EnteDao();
        dao.removeEnteOrganizzatore(e, this);
        enti.remove(e);
    }

    public void removeSessione(Sessione sessione) throws SQLException {
        SessioneDao dao = new SessioneDao();
        dao.removeSessione(sessione);
        sessioni.remove(sessione);
    }

    public void removeSponsorizzazione(Sponsorizzazione s) throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        dao.removeSponsorizzazione(s);
        sponsorizzazioni.remove(s);
    }

    @Override
    public String toString() {
        return titolo;
    }
}