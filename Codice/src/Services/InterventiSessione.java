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
    public void saveIntervento(Intervento intervento)throws SQLException{
        InterventoDao interventoDao=new InterventoDao();
        try{
            interventoDao.saveIntervento(intervento);
            interventi.add(intervento);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void addIntervento(Intervento intervento) throws SQLException {
        InterventoDao dao = new InterventoDao();
            int id = dao.addIntervento(intervento);
            intervento.setInterventoID(id);
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
