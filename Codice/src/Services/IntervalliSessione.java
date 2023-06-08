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

    public ObservableList<Intervallo> getIntervalli() {
        return intervalli;
    }

    public void setIntervalli(ObservableList<Intervallo> intervalli) {
        this.intervalli = intervalli;
    }

    public void addIntervallo(Intervallo intervallo) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        int id = dao.saveInterval(intervallo);
        intervallo.setId(id);
        intervalli.add(intervallo);
    }
    public void removeIntervallo(Intervallo intervallo) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        dao.removeIntervallo(intervallo);
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
}
