package Model.Utilities;

import Model.DAO.ComitatoDao;
import Model.DAO.ConferenzaDao;
import Model.DAO.OrganizzatoreDao;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.organizzazione.Comitato;
import Model.Entities.organizzazione.Ente;
import Model.Entities.organizzazione.Organizzatore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class MembriComitato {
    private final Conferenza conferenza;
    private final ObservableList<Organizzatore> membriComitatoLocale;
    private final ObservableList<Organizzatore> membriComitatoScientifico;
    private final ObservableList<Organizzatore> organizzatoriEnti;

    public MembriComitato(Conferenza c) {
        this.conferenza = c;
        this.membriComitatoLocale = FXCollections.observableArrayList();
        this.membriComitatoScientifico = FXCollections.observableArrayList();
        this.organizzatoriEnti = FXCollections.observableArrayList();
    }

    public void addMembroComitatoLocale(Organizzatore organizzatore, Comitato comitato) throws SQLException {
        ComitatoDao comitatoDao = new ComitatoDao();
        comitatoDao.addMembroComitato(organizzatore, comitato);
        membriComitatoLocale.add(organizzatore);
    }

    public void addMembroComitatoScientifico(Organizzatore organizzatore, Comitato comitato) throws SQLException {
        ComitatoDao comitatoDao = new ComitatoDao();
        comitatoDao.addMembroComitato(organizzatore, comitato);
        membriComitatoScientifico.add(organizzatore);
    }

    public ObservableList<Organizzatore> getMembriComitatoLocale() {
        return membriComitatoLocale;
    }

    public ObservableList<Organizzatore> getMembriComitatoScientifico() {
        return membriComitatoScientifico;
    }

    public void loadMembriComitatoLocale() throws SQLException {
        OrganizzatoreDao dao = new OrganizzatoreDao();
        membriComitatoLocale.clear();
        membriComitatoLocale.addAll(dao.retrieveOrganizzatoriComitato(conferenza.getComitato_l().getId_comitato()));
    }

    public void loadMembriComitatoScientifico() throws SQLException {
        OrganizzatoreDao dao = new OrganizzatoreDao();
        membriComitatoScientifico.clear();
        membriComitatoScientifico.addAll(dao.retrieveOrganizzatoriComitato(conferenza.getComitato_s().getId_comitato()));
    }

    public void loadOrganizzatoriEnte(ObservableList<Ente> enti) throws SQLException {
        OrganizzatoreDao dao = new OrganizzatoreDao();
        organizzatoriEnti.clear();
        for (Ente ente : enti) {
            organizzatoriEnti.addAll(dao.retrieveOrganizzatoreByEnte(ente.getId_ente()));
        }
    }

    public void removeMembroComitatoLocale(Organizzatore organizzatore, Comitato comitato) throws SQLException {
        ComitatoDao comitatoDao = new ComitatoDao();
        comitatoDao.removeMembroComitato(organizzatore, comitato);
        membriComitatoLocale.remove(organizzatore);
    }

    public void removeMembroComitatoScientifico(Organizzatore organizzatore, Comitato comitato) throws SQLException {
        ComitatoDao comitatoDao = new ComitatoDao();
        comitatoDao.removeMembroComitato(organizzatore, comitato);
        membriComitatoScientifico.remove(organizzatore);
    }

    public Conferenza retreiveComitati(Conferenza conferenza) throws SQLException {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenza = conferenzaDao.retrieveConferenzaByID(conferenza.getId_conferenza());
        return conferenza;
    }
}
