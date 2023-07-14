package Persistence.Entities.Conferenze;
import Persistence.DAO.EventoSocialeDao;
import Persistence.DAO.IntervalloDao;
import Persistence.DAO.InterventoDao;
import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.partecipanti.Speaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PGInterval;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;

public class Programma {
    private int programmaID;
    private Sessione sessione;
    private Speaker keynote;
    private ObservableList<EventoSociale> eventi;
    private ObservableList<Intervallo> intervalli;
    private ObservableList<Intervento> interventi;
    private ObservableList<ActivityModel> programmaSessione;
    public Programma(Sessione sessione) {
        this.sessione = sessione;
        programmaSessione = FXCollections.observableArrayList();
        interventi=FXCollections.observableArrayList();
        intervalli=FXCollections.observableArrayList();
        eventi=FXCollections.observableArrayList();
    }
    public Programma(){
        programmaSessione = FXCollections.observableArrayList();
        interventi=FXCollections.observableArrayList();
        intervalli=FXCollections.observableArrayList();
        eventi=FXCollections.observableArrayList();
    }
    public Speaker getKeynote() {
        return keynote;
    }
    public void setKeynote(Speaker keynote) {
        this.keynote = keynote;
    }
    public Sessione getSessione() {
        return sessione;
    }
    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }
    public int getProgrammaID() {
        return programmaID;
    }
    public void setProgrammaID(int programmaID) {
        this.programmaID = programmaID;
    }

    public ObservableList<EventoSociale> getEventi() {
        return eventi;
    }

    public void setEventi(ObservableList<EventoSociale> eventi) {
        this.eventi = eventi;
    }

    public ObservableList<Intervallo> getIntervalli() {
        return intervalli;
    }

    public void setIntervalli(ObservableList<Intervallo> intervalli) {
        this.intervalli = intervalli;
    }

    public ObservableList<Intervento> getInterventi() {
        return interventi;
    }

    public void setInterventi(ObservableList<Intervento> interventi) {
        this.interventi = interventi;
    }
    public ObservableList<ActivityModel> getProgrammaSessione(){
        return programmaSessione;
    }

    public void loadEventiSociali() throws SQLException {
        EventoSocialeDao dao = new EventoSocialeDao();
        eventi.clear();
        eventi.addAll(dao.retrieveEventiByProgramma(this));
    }
    public void addEvento(EventoSociale e, PGInterval durata) throws SQLException {
        EventoSocialeDao dao = new EventoSocialeDao();
        int id = dao.saveEvento(e,durata);
        e.setId_evento(id);
        eventi.add(e);
    }
    public void removeEvento(EventoSociale e) throws SQLException {
        EventoSocialeDao dao = new EventoSocialeDao();
        dao.deleteEvento(e);
        eventi.remove(e);
    }

    public void updateEvento(EventoSociale e) throws SQLException {
        if(eventi.contains(e)){
            eventi.remove(e);
            eventi.add(e);
            EventoSocialeDao dao = new EventoSocialeDao();
            dao.updateEvento(e);
        }
    }
    public void loadIntervalli() throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        intervalli.clear();
        intervalli.addAll(dao.retrieveIntervalliByProgramma(this));
    }

    public void addIntervallo(Intervallo intervallo,PGInterval durata) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        int id= dao.createIntervallo(intervallo,durata);
        intervallo.setId_intervallo(id);
        intervalli.add(intervallo);
    }
    public void addNewIntervallo(Intervallo intervallo,PGInterval durata) throws SQLException {
        IntervalloDao dao = new IntervalloDao();
        dao.createNewIntervallo(intervallo,durata);
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
    public void loadInterventi() throws SQLException {
        InterventoDao dao = new InterventoDao();
        interventi.clear();
        interventi.addAll(dao.retrieveInterventiByProgramma(this));
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
    public void loadProgramaSessione() throws SQLException{
        ProgrammaDao programmaDao=new ProgrammaDao();
        programmaSessione.clear();
        retreiveProgrammaID(sessione);
        loadInterventi();
        loadIntervalli();
        loadEventiSociali();
        for (Intervento intervento: interventi){
//            int id = intervento.getId_intervento();
//            Timestamp inizio=intervento.getInizio();
//            Timestamp fine=intervento.getFine();
//            String descrizione= intervento.getEstratto();
//            Speaker speaker= intervento.getSpeaker();
//            ActivityModel activity=new ActivityModel(id,"Intervento",inizio,fine,descrizione,speaker,this);
            ActivityModel activity = intervento;
            activity.setType("Intervento");
            programmaSessione.add(activity);
        }
        for (Intervallo intervallo: intervalli){
//            int id = intervallo.getId_intervallo();
//            Timestamp inizio=intervallo.getInizio();
//            Timestamp fine=intervallo.getFine();
//            String descrizione=intervallo.getTipologia();
//            ActivityModel activity=new ActivityModel(id,"Intervallo",inizio,fine,descrizione,this);
            ActivityModel activity = intervallo;
            activity.setType("Intervallo");
            programmaSessione.add(activity);
        }
        for (EventoSociale eventoSociale: eventi){
//            int id = eventoSociale.getId_evento();
//            Timestamp inizio=eventoSociale.getInizio();
//            Timestamp fine=eventoSociale.getFine();
//            String descrizione= eventoSociale.getTipologia();
//            ActivityModel activity=new ActivityModel(id,"Evento",inizio,fine,descrizione,this);
            ActivityModel activity = eventoSociale;
            activity.setType("Evento");
            programmaSessione.add(activity);
        }programmaSessione.sort(Comparator.comparing(ActivityModel::getInizio));
    }
    public void removeActivity(ActivityModel activityModel) {
        try {
//            for (Intervento intervento : new ArrayList<>(interventi)) {
//                if (intervento.getTitolo().equals(activityModel.getDescrizione()) && intervento.getInizio().equals(activityModel.getInizio())) {
//                    removeIntervento(intervento);
//                }
//            }
//            for (Intervallo intervallo : new ArrayList<>(intervalli)) {
//                if (intervallo.getTipologia().equals(activityModel.getDescrizione()) && intervallo.getInizio().equals(activityModel.getInizio())) {
//                    removeIntervallo(intervallo);
//                }
//            }
//            for (EventoSociale eventoSociale : new ArrayList<>(eventi)) {
//                if (eventoSociale.getTipologia().equals(activityModel.getDescrizione()) && eventoSociale.getInizio().equals(activityModel.getInizio())) {
//                    removeEvento(eventoSociale);
//                }
//            }
            if(programmaSessione.contains(activityModel)) {
                programmaSessione.remove(activityModel);
                if(activityModel instanceof Intervento)
                    removeIntervento((Intervento) activityModel);
                if(activityModel instanceof  EventoSociale)
                    removeEvento((EventoSociale) activityModel);
                else if(activityModel instanceof Intervallo)
                    removeIntervallo((Intervallo) activityModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retreiveProgrammaID(Sessione sessione) throws SQLException{
        ProgrammaDao programmaDao = new ProgrammaDao();
        setProgrammaID(programmaDao.retrieveIDProgramma(sessione));
    }
}
