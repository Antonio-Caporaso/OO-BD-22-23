package Persistence.Entities.Conferenze;

import Persistence.DAO.ConferenzaDao;
import Persistence.DAO.EnteDao;
import Persistence.DAO.SessioneDao;
import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Comitato;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

public class Conferenza {
    private int id_conferenza;
    private String titolo;
    private Utente proprietario;
    private Timestamp inizio;
    private Timestamp fine;
    private String descrizione;
    private Comitato comitato_l;
    private Comitato comitato_s;
    private Sede sede;
    private ObservableList<Ente> enti;
    private ObservableList<Sponsorizzazione> sponsorizzazioni;
    private ObservableList<Sessione> sessioni;

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

    public Conferenza() {}

    public Conferenza(String nome, Timestamp dataI, Timestamp dataF, String descrizione, Sede sede, Utente user) {
        this.titolo=nome;
        this.inizio=dataI;
        this.fine=dataF;
        this.descrizione=descrizione;
        this.sede=sede;
        this.proprietario=user;
    }

    public int getId_conferenza() {
        return id_conferenza;
    }

    public void setId_conferenza(int id_conferenza) {
        this.id_conferenza = id_conferenza;
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Utente getProprietario() {
        return proprietario;
    }
    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }
    public Timestamp getInizio() {
        return inizio;
    }
    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }
    public Timestamp getFine() {
        return fine;
    }
    public void setFine(Timestamp fine) {
        this.fine = fine;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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
    public Sede getSede() {
        return sede;
    }
    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public ObservableList<Ente> getEnti() {
        return enti;
    }
    public ObservableList<Sponsorizzazione> getSponsorizzazioni() {
        return sponsorizzazioni;
    }
    public ObservableList<Sessione> getSessioni() {
        return sessioni;
    }
    public void setEnti(ObservableList<Ente> enti) {
        this.enti = enti;
    }

    public void setSponsorizzazioni(ObservableList<Sponsorizzazione> sponsorizzazioni) {
        this.sponsorizzazioni = sponsorizzazioni;
    }

    public void setSessioni(ObservableList<Sessione> sessioni) {
        this.sessioni = sessioni;
    }

    @Override
    public String toString() {
        return titolo;
    }
    public void loadSessioni() throws SQLException {
        SessioneDao dao = new SessioneDao();
        sessioni.clear();
        sessioni.addAll(dao.retrieveSessioniByConferenza(this));
    }
    public void addSessione(Sessione sessione) throws SQLException{
        SessioneDao sessioneDao = new SessioneDao();
        sessioni.add(sessione);
    }
    public void removeSessione(Sessione sessione) throws SQLException {
        SessioneDao dao = new SessioneDao();
        sessioni.remove(sessione);
    }
    public void loadSponsorizzazioni() throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        sponsorizzazioni.clear();
        sponsorizzazioni.addAll(dao.retrieveSponsorizzazioni(this));
    }
    public void addSponsorizzazione(Sponsorizzazione s) throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        sponsorizzazioni.add(s);
    }
    public void removeSponsorizzazione(Sponsorizzazione s) throws SQLException {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        sponsorizzazioni.remove(s);
    }
    public void loadOrganizzatori() throws SQLException {
        EnteDao dao = new EnteDao();
        enti.clear();
        enti.addAll(dao.retrieveEntiOrganizzatori(this));
    }
    public void addEnte(Ente e) throws SQLException {
        EnteDao dao = new EnteDao();
        dao.saveEnteOrganizzatore(e,this);
        enti.add(e);
    }
    public void removeEnte(Ente e) throws SQLException {
        EnteDao dao = new EnteDao();
        dao.removeEnteOrganizzatore(e, this);
        enti.remove(e);
    }
}