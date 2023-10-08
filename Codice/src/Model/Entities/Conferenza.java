package Model.Entities;

import Exceptions.DateMismatchException;
import Exceptions.EntePresenteException;
import Exceptions.SessionePresenteException;
import Exceptions.SponsorizzazionPresenteException;
import Model.DAO.EnteDao;
import Model.DAO.SessioneDao;
import Model.DAO.SponsorizzazioneDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

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

    public void addEnte(Ente ente) throws EntePresenteException {
        if (!enti.contains(ente)) {
            try {
                EnteDao enteDao = new EnteDao();
                ente.setID(enteDao.saveEnteOrganizzatore(ente, this));
                enti.add(ente);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } else
            throw new EntePresenteException();
    }

    public void addSessione(Sessione sessione) throws SessionePresenteException, DateMismatchException, SQLException {
        if (!(sessioni.contains(sessione))) {
            if (!(sessione.getInizio().before(inizio) || sessione.getFine().after(fine))) {
                SessioneDao sessioneDao = new SessioneDao();
                sessione.setId_sessione(sessioneDao.saveSessione(sessione));
                sessioni.add(sessione);
            } else throw new DateMismatchException();
        } else throw new SessionePresenteException();
    }

    public void addSponsorizzazione(Sponsorizzazione sponsorizzazione) throws SponsorizzazionPresenteException, SQLException {
        if (!(sponsorizzazioni.contains(sponsorizzazione))) {
            SponsorizzazioneDAO sponsorizzazioneDAO = new SponsorizzazioneDAO();
            sponsorizzazioneDAO.saveSponsorizzazione(sponsorizzazione);
            sponsorizzazioni.add(sponsorizzazione);
        } else throw new SponsorizzazionPresenteException();
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

    public void loadEnti() throws SQLException {
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

    public void removeEnte(Ente e) {
        if (enti.contains(e)) {
            try {
                EnteDao dao = new EnteDao();
                dao.removeEnteOrganizzatore(e, this);
                enti.remove(e);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void removeSessione(Sessione sessione) {
        if (sessioni.contains(sessione)) {
            try {
                SessioneDao sessioneDao = new SessioneDao();
                sessioneDao.removeSessione(sessione);
                sessioni.remove(sessione);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void removeSponsorizzazione(Sponsorizzazione s) {
        if (sponsorizzazioni.contains(s)) {
            try {
                SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
                dao.removeSponsorizzazione(s);
                sponsorizzazioni.remove(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conferenza that = (Conferenza) o;
        return Objects.equals(enti, that.enti) && Objects.equals(fine, that.fine) && Objects.equals(inizio, that.inizio) && Objects.equals(proprietario, that.proprietario) && Objects.equals(sede, that.sede) && Objects.equals(titolo, that.titolo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enti, fine, inizio, proprietario, sede, titolo);
    }

    @Override
    public String toString() {
        return titolo;
    }
}