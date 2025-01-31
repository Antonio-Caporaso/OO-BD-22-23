package Model.Entities;

import Model.DAO.EventoSocialeDao;
import Model.DAO.IntervalloDao;
import Model.DAO.InterventoDao;
import Model.DAO.ProgrammaDao;
import Model.Utilities.ActivityModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PGInterval;

import java.sql.SQLException;
import java.util.Comparator;

public class Programma {
    private ObservableList<EventoSociale> eventi;
    private ObservableList<Intervallo> intervalli;
    private ObservableList<Intervento> interventi;
    private Speaker keynote;
    private int programmaID;
    private Sessione sessione;
    private final ObservableList<ActivityModel> programmaSessione;

    public Programma(Sessione sessione) {
        this.sessione = sessione;
        programmaSessione = FXCollections.observableArrayList();
        interventi = FXCollections.observableArrayList();
        intervalli = FXCollections.observableArrayList();
        eventi = FXCollections.observableArrayList();
        keynote = new Speaker();
    }

    public Programma() {
        programmaSessione = FXCollections.observableArrayList();
        interventi = FXCollections.observableArrayList();
        intervalli = FXCollections.observableArrayList();
        eventi = FXCollections.observableArrayList();
        keynote = new Speaker();
    }

    public void addEvento(EventoSociale e, PGInterval durata) throws SQLException {
        EventoSocialeDao dao = new EventoSocialeDao();
        int id = dao.saveEvento(e, durata);
        e.setId_entry(id);
        eventi.add(e);
    }

    public void addIntervallo(Intervallo intervallo, PGInterval durata) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        int id = dao.createIntervallo(intervallo, durata);
        intervallo.setId_entry(id);
        intervalli.add(intervallo);
    }

    public void addIntervento(Intervento intervento, PGInterval durata) throws SQLException {
        InterventoDao dao = new InterventoDao();
        int id = dao.createIntervento(intervento, durata);
        intervento.setId_entry(id);
        interventi.add(intervento);
    }

    public ObservableList<Intervento> getInterventi() {
        return interventi;
    }

    public Speaker getKeynote() {
        return keynote;
    }

    public void setKeynote(Speaker keynote) {
        this.keynote = keynote;
    }

    public int getProgrammaID() {
        return programmaID;
    }

    public void setProgrammaID(int programmaID) {
        this.programmaID = programmaID;
    }

    public ObservableList<ActivityModel> getProgrammaSessione() {
        return programmaSessione;
    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    public void loadEventiSociali() throws SQLException {
        EventoSocialeDao dao = new EventoSocialeDao();
        eventi.clear();
        eventi.addAll(dao.retrieveEventiByProgramma(this));
    }

    public void loadIntervalli() throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        intervalli.clear();
        intervalli.addAll(dao.retrieveIntervalliByProgramma(this));
    }

    public void loadInterventi() throws SQLException {
        InterventoDao dao = new InterventoDao();
        interventi.clear();
        interventi.addAll(dao.retrieveInterventiByProgramma(this));
    }

    public void loadProgramaSessione() throws SQLException {
        programmaSessione.clear();
        retreiveProgrammaID(sessione);
        setInterventiActivities();
        setIntervalliActivities();
        setEventiActivities();
        programmaSessione.sort(Comparator.comparing(ActivityModel::getInizio));
    }

    public void removeActivity(ActivityModel activityModel) {
        try {
            if (programmaSessione.contains(activityModel)) {
                programmaSessione.remove(activityModel);
                if (activityModel instanceof Intervento) {
                    if(activityModel.getProgramma().getKeynote()==this.getKeynote()){
                           removeKeynote();
                    }
                    removeIntervento((Intervento) activityModel);
                }
                if (activityModel instanceof EventoSociale)
                    removeEvento((EventoSociale) activityModel);
                else if (activityModel instanceof Intervallo)
                    removeIntervallo((Intervallo) activityModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeEvento(EventoSociale e) throws SQLException {
        EventoSocialeDao dao = new EventoSocialeDao();
        dao.deleteEvento(e);
        eventi.remove(e);
    }

    public void removeIntervallo(Intervallo intervallo) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        dao.deleteIntervallo(intervallo);
        intervalli.remove(intervallo);
    }

    public void removeIntervento(Intervento intervento) throws SQLException {
        InterventoDao dao = new InterventoDao();
        dao.removeIntervento(intervento);
        interventi.remove(intervento);
    }

    public void removeKeynote()throws SQLException{
        ProgrammaDao programmaDao=new ProgrammaDao();
        programmaDao.removeKeynoteFromProgramma(this);
        this.keynote.setIdSpeaker(0);
    }

    private void retreiveProgrammaID(Sessione sessione) throws SQLException {
        ProgrammaDao programmaDao = new ProgrammaDao();
        setProgrammaID(programmaDao.retrieveIDProgramma(sessione));
    }

    private void setEventiActivities() throws SQLException {
        loadEventiSociali();
        for (EventoSociale eventoSociale : eventi) {
            programmaSessione.add(eventoSociale);
        }
    }

    private void setIntervalliActivities() throws SQLException {
        loadIntervalli();
        for (Intervallo intervallo : intervalli) {
            programmaSessione.add(intervallo);
        }
    }

    private void setInterventiActivities() throws SQLException {
        loadInterventi();
        for (Intervento intervento : interventi) {
            programmaSessione.add(intervento);
        }
    }
}
