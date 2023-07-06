package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.DAO.SpeakerDao;
import Persistence.Entities.Conferenze.*;
import Persistence.Entities.partecipanti.Speaker;
import Services.EventiSocialiSessione;
import Services.IntervalliSessione;
import Services.InterventiSessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class SessioneController_Edit implements Initializable {
    private Sessione sessione;
    private Conferenza conferenza;
    private SessioniController_Manage sessioniControllerManage;
    private SubScene subScene;
    private Programma programma;
    private IntervalliSessione intervalli;
    private InterventiSessione interventi;
    private EventiSocialiSessione eventi;
    @FXML
    private TableColumn<Intervento, String> abstractColumn;

    @FXML
    private TableColumn<Speaker, String> cognomeKeynote;

    @FXML
    private Button confermaButton;

    @FXML
    private Label coordinatoreLabel;

    @FXML
    private Label dataFineLabel;

    @FXML
    private Label dataInizioLabel;

    @FXML
    private Button editDetailsButton;

    @FXML
    private Button editProgrammaButton;

    @FXML
    private TableColumn<Speaker, String> emailKeynoteColumn;

    @FXML
    private TableView<EventoSociale> eventiTable;

    @FXML
    private TableView<Intervento> interventiTable;

    @FXML
    private TableColumn<Speaker, String> istituzioneKeynoteColumn;

    @FXML
    private TableView<Intervallo> intervalliTable;

    @FXML
    private TableView<Speaker> keynoteSpeakerTable;

    @FXML
    private TableColumn<Speaker, String> nomeKeynoteColumn;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label oraFineLabel;

    @FXML
    private Label oraInizioLabel;
    @FXML
    private TableColumn<EventoSociale, Timestamp> orarioEventoColumn;

    @FXML
    private TableColumn<Intervallo, Timestamp> orarioIntervalloColumn;

    @FXML
    private TableColumn<Intervento, Timestamp> orarioInterventoColumn;

    @FXML
    private Label salaLabel;

    @FXML
    private TableColumn<Intervento, String> speakerColumn;

    @FXML
    private TableColumn<EventoSociale, String> tipologiaEventoColumn;

    @FXML
    private TableColumn<Intervallo, String> tipologiaIntervalloColumn;

    @FXML
    private Label titleLabel;

    public Conferenza getConferenza() {
        return conferenza;
    }
    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }
    public Sessione getSessione() {
        return sessione;
    }
    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    public SessioniController_Manage getManageSessioniController() {
        return sessioniControllerManage;
    }

    public void setManageSessioniController(SessioniController_Manage sessioniControllerManage) {
        this.sessioniControllerManage = sessioniControllerManage;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }
    public SubScene getSubScene() {
        return subScene;
    }
    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        goToEditSessionsWindow();
    }
    private void goToEditSessionsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessioni.fxml"));
        loader.setController(sessioniControllerManage);
        sessioniControllerManage.reloadSessioni();
        Parent root = loader.load();
        subScene.setRoot(root);
    }
    @FXML
    void editProgrammaOnAction(ActionEvent event) throws IOException {
        goToEditProgrammaWindow();
    }
    private void goToEditProgrammaWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditProgramma.fxml"));
        ProgrammaController_Edit controller = new ProgrammaController_Edit();
        controller.setSubscene(subScene);
        controller.setManageSessioniController(sessioniControllerManage);
        controller.setSessione(sessione);
        controller.setProgramma(programma);
        controller.setEventi(eventi);
        controller.setIntervalli(intervalli);
        controller.setInterventi(interventi);
        loader.setController(controller);
        Parent root = loader.load();
        subScene.setRoot(root);
    }
    @FXML
    void editDetailsOnAction(ActionEvent event) throws IOException {
        goToEditDettagliSessione();
    }
    private void goToEditDettagliSessione() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditDettagliSessione.fxml"));
        DettagliSessioneController_Edit controllerS = new DettagliSessioneController_Edit();
        loader.setController(controllerS);
        controllerS.setSessione(sessione);
        controllerS.setConferenza(conferenza);
        controllerS.setSubscene(subScene);
        controllerS.setEditSessioneController(this);
        Parent root = loader.load();
        subScene.setRoot(root);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        retrieveProgrammaSessione();
        setDettagliSessione();
        setIntervalliTable();
        setEventiTable();
        setInterventiTable();
        setKeynoteTable();

    }

    private void retrieveProgrammaSessione() {
        ProgrammaDao programmaDao = new ProgrammaDao();
        try {
            programma = programmaDao.retrieveProgrammaBySessione(sessione);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDettagliSessione() {
        titleLabel.setText(sessione.getTitolo());
        nomeLabel.setText(sessione.getTitolo());
        dataInizioLabel.setText(String.valueOf(sessione.getDataInizio()));
        dataFineLabel.setText(String.valueOf(sessione.getDataFine()));
        oraInizioLabel.setText(String.valueOf(sessione.getOrarioInizio()));
        oraFineLabel.setText(String.valueOf(sessione.getOrarioFine()));
        salaLabel.setText(sessione.getLocazione().getNomeSala());
        coordinatoreLabel.setText(sessione.getCoordinatore().toString());
    }
    private void setKeynoteTable() {
        ProgrammaDao programmaDao = new ProgrammaDao();
        SpeakerDao dao = new SpeakerDao();
        Speaker keynote;
        try {
            int id = programmaDao.retrieveKeynoteSpeakerID(sessione);
            if(id != 0) {
                keynote = dao.retrieveSpeakerByID(id);
                nomeKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
                cognomeKeynote.setCellValueFactory(new PropertyValueFactory<>("cognome"));
                emailKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
                istituzioneKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("istituzione"));
                keynoteSpeakerTable.getItems().add(keynote);
            }else
                keynoteSpeakerTable.setDisable(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setInterventiTable() {
        try {
            interventi = new InterventiSessione(programma);
            interventi.loadInterventi();
            orarioInterventoColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            speakerColumn.setCellValueFactory(new PropertyValueFactory<>("speaker"));
            abstractColumn.setCellValueFactory(new PropertyValueFactory<>("estratto"));
            interventiTable.setItems(interventi.getInterventi());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEventiTable() {
        eventi = new EventiSocialiSessione(programma);
        try {
            eventi.loadEventiSociali();
            orarioEventoColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            tipologiaEventoColumn.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
            eventiTable.setItems(eventi.getEventi());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setIntervalliTable() {
        intervalli = new IntervalliSessione(programma);
        try{
            intervalli.loadIntervalli();
            orarioIntervalloColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            tipologiaIntervalloColumn.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
            intervalliTable.setItems(intervalli.getIntervalli());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }




}