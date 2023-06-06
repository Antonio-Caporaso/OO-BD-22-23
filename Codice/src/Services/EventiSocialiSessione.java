package Services;

import Persistence.DAO.EventoSocialeDao;
import Persistence.Entities.Conferenze.EventoSociale;
import Persistence.Entities.Conferenze.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class EventiSocialiSessione {
    private ObservableList<EventoSociale> eventi;
    private Programma programma;
    public EventiSocialiSessione(Programma programma){
        eventi = FXCollections.observableArrayList();
        this.programma = programma;
    }
    public void loadEventiSociali() throws SQLException {
        EventoSocialeDao dao = new EventoSocialeDao();
        eventi.clear();
        eventi.addAll(dao.retrieveEventiByProgramma(programma));
    }

    public ObservableList<EventoSociale> getEventi() {
        return eventi;
    }

    public void setEventi(ObservableList<EventoSociale> eventi) {
        this.eventi = eventi;
    }
    public void addEvento(EventoSociale e) throws SQLException {
        EventoSocialeDao dao = new EventoSocialeDao();
        int id = dao.saveEvento(e);
        e.setId(id);
        eventi.add(e);
    }
    public void removeEvento(EventoSociale e) throws SQLException {
        EventoSocialeDao dao = new EventoSocialeDao();
        dao.deleteEvento(e);
        eventi.remove(e);
    }
}
