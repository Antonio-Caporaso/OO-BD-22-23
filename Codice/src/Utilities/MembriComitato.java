package Utilities;

import Persistence.DAO.ComitatoDao;
import Persistence.DAO.ConferenzaDao;
import Persistence.DAO.OrganizzatoreDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Comitato;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.organizzazione.Organizzatore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class MembriComitato {
    private Conferenza conferenza;
    private ObservableList<Organizzatore> membriComitatoScientifico;
    private ObservableList<Organizzatore> membriComitatoLocale;
    private ObservableList<Organizzatore> organizzatoriEnti;
    public MembriComitato(Conferenza c){
        this.conferenza = c;
        this.membriComitatoLocale= FXCollections.observableArrayList();
        this.membriComitatoScientifico = FXCollections.observableArrayList();
        this.organizzatoriEnti=FXCollections.observableArrayList();
    }

    public void loadMembriComitatoScientifico() throws SQLException {
        OrganizzatoreDao dao = new OrganizzatoreDao();
        membriComitatoScientifico.clear();
        membriComitatoScientifico.addAll(dao.retrieveOrganizzatoriComitato(conferenza.getComitato_s().getId_comitato()));
    }
    public void loadMembriComitatoLocale() throws SQLException{
        OrganizzatoreDao dao = new OrganizzatoreDao();
        membriComitatoLocale.clear();
        membriComitatoLocale.addAll(dao.retrieveOrganizzatoriComitato(conferenza.getComitato_l().getId_comitato()));
    }
    public void loadOrganizzatoriEnte(ObservableList<Ente> enti)throws SQLException{
        OrganizzatoreDao dao= new OrganizzatoreDao();
        organizzatoriEnti.clear();
        for (Ente ente: enti) {
            organizzatoriEnti.addAll(dao.retrieveOrganizzatoreByEnte(ente.getId_ente()));
        }
    }
    public void loadAllOrganizzatori() throws SQLException{
        OrganizzatoreDao dao=new OrganizzatoreDao();
        membriComitatoScientifico.clear();
        membriComitatoScientifico.addAll(dao.retrieveAllOrganizzatori());
    }
    public ObservableList<Organizzatore> getMembriComitatoScientifico(){
        return membriComitatoScientifico;
    }
    public Conferenza retreiveComitati(Conferenza conferenza) throws SQLException{
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenza=conferenzaDao.retrieveConferenzaByID2(conferenza.getId_conferenza());
        return conferenza;
    }

    public ObservableList<Organizzatore> getMembriComitatoLocale(){
        return membriComitatoLocale;
    }
    public ObservableList<Organizzatore> getOrganizzatoriEnti(){return organizzatoriEnti;}
    public void addMembroComitatoScientifico(Organizzatore organizzatore, Comitato comitato) throws SQLException {
        ComitatoDao comitatoDao= new ComitatoDao();
        comitatoDao.addMembroComitato(organizzatore, comitato);
        membriComitatoScientifico.add(organizzatore);
    }
    public void addMembroComitatoLocale(Organizzatore organizzatore, Comitato comitato) throws SQLException {
        ComitatoDao comitatoDao= new ComitatoDao();
        comitatoDao.addMembroComitato(organizzatore, comitato);
        membriComitatoLocale.add(organizzatore);
    }
}
