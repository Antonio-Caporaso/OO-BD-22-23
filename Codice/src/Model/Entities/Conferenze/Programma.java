package Model.Entities.Conferenze;

import Model.DAO.EventoSocialeDao;
import Model.DAO.IntervalloDao;
import Model.DAO.InterventoDao;
import Model.DAO.ProgrammaDao;
import Model.Entities.Partecipanti.Speaker;
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

    private void retreiveProgrammaID(Sessione sessione) throws SQLException {
        ProgrammaDao programmaDao = new ProgrammaDao();
        setProgrammaID(programmaDao.retrieveIDProgramma(sessione));
    }

    private void setEventiActivities() throws SQLException {
        loadEventiSociali();
        for (EventoSociale eventoSociale : eventi) {
//            ActivityModel activity = eventoSociale;
//            activity.setType("Evento");
//            activity.setDescrizione(eventoSociale.getTipologia());
//            programmaSessione.add(activity);
            programmaSessione.add(eventoSociale);
        }
    }

    private void setIntervalliActivities() throws SQLException {
        loadIntervalli();
        for (Intervallo intervallo : intervalli) {
//            ActivityModel activity = intervallo;
//            activity.setType("Intervallo");
//            programmaSessione.add(activity);
            programmaSessione.add(intervallo);
        }
    }

    private void setInterventiActivities() throws SQLException {
        loadInterventi();
        for (Intervento intervento : interventi) {
//            ActivityModel activity = intervento;
//            activity.setType(intervento.getTitolo());
//            programmaSessione.add(activity);
            programmaSessione.add(intervento);
        }
    }

    public Programma(Sessione sessione) {
        this.sessione = sessione;
        programmaSessione = FXCollections.observableArrayList();
        interventi = FXCollections.observableArrayList();
        intervalli = FXCollections.observableArrayList();
        eventi = FXCollections.observableArrayList();
    }

    public Programma() {
        programmaSessione = FXCollections.observableArrayList();
        interventi = FXCollections.observableArrayList();
        intervalli = FXCollections.observableArrayList();
        eventi = FXCollections.observableArrayList();
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

    public void addNewIntervallo(Intervallo intervallo, PGInterval durata) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        dao.createNewIntervallo(intervallo, durata);
        intervalli.add(intervallo);
    }

    public ObservableList<EventoSociale> getEventi() {
        return eventi;
    }

    public ObservableList<Intervallo> getIntervalli() {
        return intervalli;
    }

    public ObservableList<Intervento> getInterventi() {
        return interventi;
    }

    public Speaker getKeynote() {
        return keynote;
    }

    public int getProgrammaID() {
        return programmaID;
    }

    public ObservableList<ActivityModel> getProgrammaSessione() {
        return programmaSessione;
    }

    public Sessione getSessione() {
        return sessione;
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
                if (activityModel instanceof Intervento)
                    removeIntervento((Intervento) activityModel);
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

    public void setEventi(ObservableList<EventoSociale> eventi) {
        this.eventi = eventi;
    }

    public void setIntervalli(ObservableList<Intervallo> intervalli) {
        this.intervalli = intervalli;
    }

    public void setInterventi(ObservableList<Intervento> interventi) {
        this.interventi = interventi;
    }

    public void setKeynote(Speaker keynote) {
        this.keynote = keynote;
    }

    public void setProgrammaID(int programmaID) {
        this.programmaID = programmaID;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    public void updateEvento(EventoSociale e) throws SQLException {
        if (eventi.contains(e)) {
            eventi.remove(e);
            eventi.add(e);
            EventoSocialeDao dao = new EventoSocialeDao();
            dao.updateEvento(e);
        }
    }

    public void updateIntervallo(Intervallo intervallo) throws SQLException {
        if (intervalli.contains(intervallo)) {
            intervalli.remove(intervallo);
            intervalli.add(intervallo);
            IntervalloDao dao = new IntervalloDao();
            dao.updateIntervallo(intervallo);
        }
    }

    public void updateIntervento(Intervento i) throws SQLException {
        if (interventi.contains(i)) {
            interventi.remove(i);
            interventi.add(i);
            InterventoDao dao = new InterventoDao();
            dao.updateIntervento(i);
        }
    }
}
