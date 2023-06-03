package View.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import Persistence.DAO.*;
import Persistence.Entities.Conferenze.*;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.ActivityModel;
import Services.ProgrammaSessione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewProgramma implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button aggiungiButton;

    @FXML
    private Button backButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label nomeSessione;
    @FXML
    private Button rimuoviButton;
    @FXML
    private ListView<Programma> programmaListView;
    @FXML
    private TableView<ActivityModel> programmaTableView;
    @FXML
    private TableColumn<ActivityModel, String> attivitaTableColumn;
    @FXML
    private TableColumn<ActivityModel, LocalDateTime> orarioFineTableColumn;

    @FXML
    private TableColumn<ActivityModel, LocalDateTime> orarioInizioTableColumn;
    @FXML
    private SubScene subscene;
    ObservableList<ActivityModel> activityList = FXCollections.observableArrayList();
    private Conferenza conferenza;
    private Sessione sessione;
    private ProgrammaDao programmaDao= new ProgrammaDao();
    private Utente user;
    //Public Setters
    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setUser(Utente utente){
        this.user=utente;
    }
    public void setSessione(Sessione sessione){this.sessione=sessione;}
    //Button Methods
    @FXML
    void aggiungiButtonOnAction(ActionEvent event) {
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadInserisciSessione();
    }
    @FXML
    void confirmButtonOnAction(ActionEvent event) {
    }
    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
    }
    //Private methods
    private void loadInserisciSessione(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaSessione.fxml"));
            VisualizzaSessioneController controller = new  VisualizzaSessioneController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
//            controller.setUtente(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setProgramma() {
        try {
            // Retrieve the required data from the database tables (Intervento, Intervallo, Eventosociale)
            // and populate an ObservableList of ActivityModel objects with the combined data
//            ObservableList<ActivityModel> activityList = FXCollections.observableArrayList();
            // Populate the activityList with data from the database
            sessione.setSessioneID(98);
            ProgrammaDao programmaSessione=new ProgrammaDao();
            Programma programma= new Programma();
            try {
                programma = programmaSessione.retrieveProgrammaBySessione(sessione);
            }catch (SQLException e){
                e.printStackTrace();
            }

//            try {
//                programmaSessione.loadProgrammi();
//                activityList.setAll(programmaSessione.getProgramma());
//
//
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//
//
            attivitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("attivita"));


           orarioInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));


            orarioFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataFine"));


//
//            programmaTableView.setItems(activityList);


            try {
                // Retrieve the required data from the database tables (Intervento, Intervallo, EventoSociale)
                // and populate an ObservableList of ActivityModel objects with the combined data
                ObservableList<ActivityModel> activityList = FXCollections.observableArrayList();

                InterventoDao interventoDao = new InterventoDao();
                IntervalloDao intervalloDao = new IntervalloDao();
                EventoSocialeDao eventoSocialeDao = new EventoSocialeDao();

                List<Intervento> interventi = interventoDao.retrieveInterventiByProgramma(programma);
                List<Intervallo> intervalli = intervalloDao.retrieveIntervalliByProgramma(programma);
                List<EventoSociale> eventiSociali = eventoSocialeDao.retrieveEventiByProgramma(programma);

                // Populate the activityList with data from the database
                for (int i = 0; i < interventi.size(); i++) {
                    Intervento intervento = interventi.get(i);
                    String attivita = "Intervento";
                    Timestamp dataInizio = intervento.getOrario();

                    Timestamp dataFine = null;
                    if (i + 1 < interventi.size()) {
                        Intervento nextIntervento = interventi.get(i + 1);
//                        dataFine = nextIntervento.getOrario().toLocalDateTime();
                    } else if (intervalli.size() > 0) {
                        Intervallo firstIntervallo = intervalli.get(0);
//                        dataFine = firstIntervallo.getOrario().toLocalDateTime();
                    } else if (eventiSociali.size() > 0) {
                        EventoSociale firstEventoSociale = eventiSociali.get(0);
//                        dataFine = firstEventoSociale.getOrario().toLocalDateTime();
                    } else {
                        // If there are no intervals or eventiSociali, consider it as the end of the sessione
//                        dataFine = sessione.getOrarioFine(); // Replace this with the appropriate logic
                    }

                    ActivityModel activityModel = new ActivityModel(attivita, dataInizio, dataFine);
                    activityList.add(activityModel);
                }

                for (Intervallo intervallo : intervalli) {
                    String attivita = "Intervallo";
                    Timestamp dataInizio = intervallo.getOrario();

                    Timestamp dataFine = null;
                    int index = intervalli.indexOf(intervallo);
                    if (index + 1 < intervalli.size()) {
                        Intervallo nextIntervallo = intervalli.get(index + 1);
//                        dataFine = nextIntervallo.getOrario().toLocalDateTime();
                    } else if (eventiSociali.size() > 0) {
                        EventoSociale firstEventoSociale = eventiSociali.get(0);
//                        dataFine = firstEventoSociale.getOrario().toLocalDateTime();
                    } else {
                        // If there are no eventiSociali, consider it as the end of the sessione
//                        dataFine = getSessioneEndDateTime(); // Replace this with the appropriate logic
                    }

                    ActivityModel activityModel = new ActivityModel(attivita, dataInizio, dataFine);
                    activityList.add(activityModel);
                }

                for (EventoSociale eventoSociale : eventiSociali) {
                    String attivita = "EventoSociale";
                    Timestamp dataInizio = eventoSociale.getOrario();

                    Timestamp dataFine = null;
                    int index = eventiSociali.indexOf(eventoSociale);
                    if (index + 1 < eventiSociali.size()) {
                        EventoSociale nextEventoSociale = eventiSociali.get(index + 1);
//                        dataFine = nextEventoSociale.getOrario().toLocalDateTime();
                    } else {
                        // If there are no more eventiSociali, consider it as the end of the sessione
//                        dataFine = getSessioneEndDateTime(); // Replace this with the appropriate logic
                    }

                    ActivityModel activityModel = new ActivityModel(attivita, dataInizio, dataFine);
                    activityList.add(activityModel);
                }
                activityList.sort(Comparator.comparing(ActivityModel::getDataInizio));
                for (int i = 0; i < activityList.size() - 1; i++) {
                    ActivityModel currentActivity = activityList.get(i);
                    ActivityModel nextActivity = activityList.get(i + 1);

                    currentActivity.setDataFine(nextActivity.getDataInizio());
                }
//                programmaTableView.getColumns().addAll(attivitaTableColumn, orarioInizioTableColumn, orarioFineTableColumn);
                programmaTableView.setItems(activityList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProgramma();
    }
    @FXML
    void initialize() {
        assert aggiungiButton != null : "fx:id=\"aggiungiButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert nomeSessione != null : "fx:id=\"nomeSessione\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert rimuoviButton != null : "fx:id=\"rimuoviButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
    }
}
