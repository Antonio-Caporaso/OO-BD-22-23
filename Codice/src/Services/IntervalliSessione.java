package Services;

import Persistence.DAO.IntervalloDao;
import Persistence.Entities.Conferenze.Intervallo;
import Persistence.Entities.Conferenze.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    public void addIntervallo(Intervallo intervallo) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        dao.saveIntervallo(intervallo);
        intervalli.add(intervallo);
    }
    public void removeIntervallo(Intervallo intervallo) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        dao.removeIntervallo(intervallo);
        intervalli.remove(intervallo);

    }
}
