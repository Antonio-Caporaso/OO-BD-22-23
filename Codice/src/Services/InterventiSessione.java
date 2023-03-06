package Services;

import Persistence.DAO.InterventoDao;
import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}
