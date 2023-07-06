package Services;

import Persistence.DAO.IntervalloDao;
import Persistence.Entities.Conferenze.Intervallo;
import Persistence.Entities.Conferenze.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PGInterval;

import java.sql.SQLException;

public class IntervalliSessione {
    private ObservableList<Intervallo> intervalli;
    private Programma programma;

    public IntervalliSessione(Programma programma){
        this.programma = programma;
        intervalli = FXCollections.observableArrayList();
    }
    public void loadIntervalli() throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        intervalli.clear();
        intervalli.addAll(dao.retrieveIntervalliByProgramma(programma));
    }

    public ObservableList<Intervallo> getIntervalli() {
        return intervalli;
    }

    public void setIntervalli(ObservableList<Intervallo> intervalli) {
        this.intervalli = intervalli;
    }

    public void addIntervallo(Intervallo intervallo,PGInterval durata) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        int id = dao.createIntervallo(intervallo,durata);
        intervallo.setId_intervallo(id);
        intervalli.add(intervallo);
    }
    public void removeIntervallo(Intervallo intervallo) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        dao.deleteIntervallo(intervallo);
        intervalli.remove(intervallo);
    }
    public void updateIntervallo(Intervallo intervallo) throws SQLException {
        if(intervalli.contains(intervallo)){
            intervalli.remove(intervallo);
            intervalli.add(intervallo);
            IntervalloDao dao = new IntervalloDao();
            dao.updateIntervallo(intervallo);
        }
    }
    public void addIntervallo2(Intervallo intervallo, PGInterval durata) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        int id = dao.createIntervallo(intervallo,durata);
        intervallo.setId_intervallo(id);
        intervalli.add(intervallo);
    }
}
