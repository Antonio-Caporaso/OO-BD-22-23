package Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import Persistence.DAO.InterventoDao;
import Persistence.DAO.EventoSocialeDao;
import Persistence.DAO.IntervalloDao;
import Persistence.DAO.ProgrammaDao;

import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Conferenze.Intervallo;
import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.EventoSociale;

import Persistence.Entities.organizzazione.ActivityModel;
public class ProgrammaSessione {
    private ObservableList<ActivityModel> activityList;
    private Programma programma;
    private Sessione sessione;
    public ProgrammaSessione(Sessione sessione){
        this.sessione = sessione;
        activityList = FXCollections.observableArrayList();
    }
    public ObservableList<ActivityModel> getProgramma() {
        return activityList;
    }
    public void setProgramma(ObservableList<ActivityModel> activityList) {
        this.activityList = activityList;
    }
    public ObservableList loadProgrammi(){
        activityList = FXCollections.observableArrayList();
        retreiveProgramma(sessione);
        try {
            InterventoDao interventoDao = new InterventoDao();
            IntervalloDao intervalloDao = new IntervalloDao();
            EventoSocialeDao eventoSocialeDao = new EventoSocialeDao();

            List<Intervento> interventi = interventoDao.retrieveInterventiByProgramma(programma);
            List<Intervallo> intervalli = intervalloDao.retrieveIntervalliByProgramma(programma);
            List<EventoSociale> eventiSociali = eventoSocialeDao.retrieveEventiByProgramma(programma);
            //for loop per inserire tutti le attività in activityModel
            for (Intervento intervento : interventi) {
                String attivita = intervento.getTitolo();
                Timestamp dataInizio = intervento.getOrario();
                Timestamp dataFine = null;
                ActivityModel activityModel = new ActivityModel(attivita, dataInizio, dataFine);
                activityList.add(activityModel);
            }
            for (Intervallo intervallo : intervalli) {
                String attivita = intervallo.getTipologia();
                Timestamp dataInizio = intervallo.getOrario();
                Timestamp dataFine = null;
                ActivityModel activityModel = new ActivityModel(attivita, dataInizio, dataFine);
                activityList.add(activityModel);
            }
            for (EventoSociale eventoSociale : eventiSociali) {
                String attivita = eventoSociale.getTipologia();
                Timestamp dataInizio = eventoSociale.getOrario();
                Timestamp dataFine = null;
                ActivityModel activityModel = new ActivityModel(attivita, dataInizio, dataFine);
                activityList.add(activityModel);
            }
            //eseguiamo un sorting di activityModel usando la data di inizio
            activityList.sort(Comparator.comparing(ActivityModel::getDataInizio));
            for (int i = 0; i < activityList.size() - 1; i++) {
                ActivityModel currentActivity = activityList.get(i);
                ActivityModel nextActivity = activityList.get(i + 1);
                currentActivity.setDataFine(nextActivity.getDataInizio());
            }
            //Converto data ed ora di fine della sessione in Timestamp e lo assegno come orario di fine dell'ultima attività
            int lastIndex= activityList.size()-1;
            if(lastIndex>=0){
                ActivityModel lastActivity = activityList.get(lastIndex);
                LocalDate fineSessioneData= sessione.getDataFine().toLocalDate();
                LocalTime fineSessioneOrario= sessione.getOrarioFine().toLocalTime();
                LocalDateTime fineSessioneLocalDateTime= LocalDateTime.of(fineSessioneData,fineSessioneOrario);
                Timestamp fineSessioneTimestamp=Timestamp.valueOf(fineSessioneLocalDateTime);
                lastActivity.setDataFine(fineSessioneTimestamp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }return activityList;
    }
    private Programma retreiveProgramma(Sessione sessione){
        ProgrammaDao dao= new ProgrammaDao();
        try {
            programma = dao.retrieveProgrammaBySessione(sessione);
        }catch (SQLException e){
            e.printStackTrace();
        }return programma;
    }
}
