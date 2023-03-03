package Services;

import Persistence.DAO.SessioneDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Sessioni {
    private ObservableList<Sessione> sessioni;
    private Conferenza conferenza;
    private Programma programma;
    public Sessioni(Conferenza conferenza){
        this.conferenza = conferenza;
        sessioni = FXCollections.observableArrayList();
    }
    public ObservableList<Sessione> getSessioni() {
        return sessioni;
    }
    public void setSessioni(ObservableList<Sessione> sessioni) {
        this.sessioni = sessioni;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public void loadSessioni() {
        sessioni.addAll(conferenza.getSessioni());
    }
    public void addSessione(Sessione sessione) throws SQLException{
        SessioneDao sessioneDao = new SessioneDao();
        sessioneDao.saveSessione(sessione);
        sessioni.add(sessione);
    }
    public void removeSessione(Sessione sessione) throws SQLException {
        SessioneDao dao = new SessioneDao();
        dao.removeSessione(sessione);
        sessioni.remove(sessione);
    }
}
