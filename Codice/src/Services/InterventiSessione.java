package Services;

import Persistence.DAO.InterventoDao;
import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PGInterval;

import java.sql.SQLException;

public class InterventiSessione {
    private ObservableList<Intervento> interventi;
    private Programma programma;



    public InterventiSessione(Programma programma){
        this.programma = programma;
        interventi = FXCollections.observableArrayList();
    }
    public void loadInterventi() throws SQLException {
        InterventoDao dao = new InterventoDao();
        interventi.clear();
        interventi.addAll(dao.retrieveInterventiByProgramma(programma));
    }
    public ObservableList<Intervento> getInterventi() {
        return interventi;
    }

    public void setInterventi(ObservableList<Intervento> interventi) {
        this.interventi = interventi;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }
    public void addIntervento(Intervento intervento, PGInterval durata) throws SQLException {
        InterventoDao dao = new InterventoDao();
            int id = dao.createIntervento(intervento,durata);
            intervento.setId_intervento(id);
            interventi.add(intervento);
    }
    public void removeIntervento(Intervento intervento) throws SQLException {
        InterventoDao dao = new InterventoDao();
        dao.removeIntervento(intervento);
        interventi.remove(intervento);
    }
    public void updateIntervento(Intervento i) throws SQLException {
        if(interventi.contains(i)){
            interventi.remove(i);
            interventi.add(i);
            InterventoDao dao = new InterventoDao();
            dao.updateIntervento(i);
        }
    }
}
