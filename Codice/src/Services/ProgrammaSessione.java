package Services;

import Persistence.DAO.*;
import Persistence.Entities.Conferenze.*;
import Persistence.Entities.organizzazione.ActivityModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Collections;

public class ProgrammaSessione {
    private ObservableList<ActivityModel> activityList;
    private Programma programma;
    private Sessione sessione;
    private LinkedList<Intervento> interventoLinkedList;
    private LinkedList<Intervallo> intervalloLinkedList;
    private LinkedList<EventoSociale> eventoSocialeLinkedList;
    public ProgrammaSessione(Sessione sessione){
        this.sessione = sessione;
        activityList = FXCollections.observableArrayList();

    }
    private Programma retreiveProgramma(Sessione sessione){
        Programma programma = new Programma();
        try{
        ProgrammaDao programmaDao=new ProgrammaDao();
        programma =programmaDao.retrieveProgrammaBySessione(sessione);
    }catch (SQLException e){
            e.printStackTrace();
        }return programma;
    }

    public ObservableList<ActivityModel> getProgramma() {
        return activityList;
    }
    public void setProgramma(ObservableList<ActivityModel> activityList) {
        this.activityList = activityList;
    }

    public void loadProgrammi() throws SQLException {
//        InterventoDao interventoDao = new InterventoDao();
//        IntervalloDao intervalloDao = new IntervalloDao();
//        EventoSocialeDao eventoSocialeDao=new EventoSocialeDao();
//        activityList.clear();
//        interventoLinkedList=interventoDao.retrieveInterventiByProgramma(programma);
//        intervalloLinkedList= intervalloDao.retrieveIntervalliByProgramma(programma);
//        eventoSocialeLinkedList=eventoSocialeDao.retrieveEventiByProgramma(programma);
//
//        Collections.sort(allActivities, Comparator.comparing(Activity::getOrario));
//        for (int i = 0; i < allActivities.size(); i++) {
//            Activity currentActivity = allActivities.get(i);
//            String attivita = getActivityAttivita(currentActivity);
//            LocalDateTime dataInizio = currentActivity.getOrario().toLocalDateTime();
//
//            LocalDateTime dataFine;
//        }
//        if (i + 1 < allActivities.size()) {
//            Activity nextActivity = allActivities.get(i + 1);
//            dataFine = nextActivity.getOrario();
//        } else {
//            // If there is no next activity, consider it as the end of the sessione
//            dataFine = getSessioneEndDateTime(); // Replace this with the appropriate logic
//        }
//
////        ActivityModel activityModel = new ActivityModel(attivita, dataInizio, dataFine);
//        activityList.add(activityModel);
//
//    }
//    private String getActivityAttivita(Activity activity) {
//        if (activity instanceof Intervento) {
//            return "Intervento";
//        } else if (activity instanceof Intervallo) {
//            return "Intervallo";
//        } else if (activity instanceof EventoSociale) {
//            return "EventoSociale";
//        } else {
//            return "Unknown";
//        }
        ProgrammaSessioneDao dao= new ProgrammaSessioneDao();
        programma=retreiveProgramma(sessione);
//        programma.setProgrammaID(25);
        activityList.clear();
        activityList.addAll(dao.retrieveProgrammaBySessione(programma));


    }
    public void addProgramma(Programma programma) throws SQLException{
        ProgrammaDao dao = new ProgrammaDao();
        dao.retrieveProgrammaBySessione(sessione);
    }
    public void removeProgramma(Programma programma) throws SQLException {
        ProgrammaDao dao = new ProgrammaDao();
//        dao.removeSessione(programma);
//        programmi.remove(programma);
    }
}
