package View.Controller;

import Persistence.DAO.ProgrammaDao;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class ProgrammaController_Edit implements Initializable {
    private Programma programma;
    private SubScene subscene;
    private SessioniController_Manage sessioniControllerManage;
    private InterventiSessione interventi;
    private IntervalliSessione intervalli;
    private Sessione sessione;
    private EventiSocialiSessione eventi;
    @FXML
    private TableColumn<Intervento, String> abstractColumn;
    @FXML
    private TableColumn<Intervento,String> titleColumn;
    @FXML
    private TableColumn<Speaker, String> cognomeKeynoteColumn;

    @FXML
    private Button editEventiButton;

    @FXML
    private Button editIntervalliButton;

    @FXML
    private Button editInterventiButton;

    @FXML
    private Button editKeynoteButton;

    @FXML
    private TableColumn<Speaker, String> emailKeynoteColumn;

    @FXML
    private TableView<EventoSociale> eventiTable;

    @FXML
    private Button fineButton;

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
    private TableColumn<EventoSociale, Time> orarioEventoColumn;

    @FXML
    private TableColumn<Intervallo, Time> orarioIntervalloColumn;

    @FXML
    private TableColumn<Intervento, Time> orarioInterventoColumn;

    @FXML
    private TableColumn<Intervento,String> speakerColumn;

    @FXML
    private TableColumn<EventoSociale, String> tipologiaEventoColumn;

    @FXML
    private TableColumn<Intervallo, String> tipologiaIntervalloColumn;

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    public InterventiSessione getInterventi() {
        return interventi;
    }

    public void setInterventi(InterventiSessione interventi) {
        this.interventi = interventi;
    }

    public IntervalliSessione getIntervalli() {
        return intervalli;
    }

    public void setIntervalli(IntervalliSessione intervalli) {
        this.intervalli = intervalli;
    }

    public EventiSocialiSessione getEventi() {
        return eventi;
    }

    public void setEventi(EventiSocialiSessione eventi) {
        this.eventi = eventi;
    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    public SubScene getSubscene() {
        return subscene;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setManageSessioniController(SessioniController_Manage sessioniControllerManage) {
        this.sessioniControllerManage = sessioniControllerManage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        retrieveProgramma();
        setIntervalliTable();
        setEventiTable();
        setInterventiTable();
        setKeynoteTable();
    }

    private void retrieveProgramma() {
        ProgrammaDao dao = new ProgrammaDao();
        try {
            programma = dao.retrieveProgrammaBySessione(sessione);
            sessione.setProgramma(programma);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setKeynoteTable() {
        Speaker keynote = programma.getKeynote();
            nomeKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            cognomeKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
            emailKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            istituzioneKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("istituzione"));
            keynoteSpeakerTable.getItems().add(keynote);
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
        try {
            eventi = new EventiSocialiSessione(programma);
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
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
            orarioIntervalloColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            tipologiaIntervalloColumn.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
            intervalliTable.setItems(intervalli.getIntervalli());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessioni.fxml"));
        loader.setController(sessioniControllerManage);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    void editEventiButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditEventi.fxml"));
        EventiController_Edit controller = new EventiController_Edit();
        controller.setManageSessioniController(sessioniControllerManage);
        controller.setEditProgrammaController(this);
        controller.setProgramma(programma);
        controller.setSessione(sessione);
        controller.setSubScene(subscene);
        controller.setEventi(eventi);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void editIntervalliButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditIntervalli.fxml"));
        IntervalliController_Edit controller = new IntervalliController_Edit();
        controller.setEditProgrammaController(this);
        controller.setProgramma(programma);
        controller.setSessione(sessione);
        controller.setSubScene(subscene);
        controller.setIntervalliSessione(intervalli);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void editInterventiButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditInterventi.fxml"));
        InterventiController_Edit controller = new InterventiController_Edit();
        controller.setEditProgrammaController(this);
        controller.setProgramma(programma);
        controller.setSessione(sessione);
        controller.setSubScene(subscene);
        controller.setInterventiSessione(interventi);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    void editKeynoteButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditKeynote.fxml"));
        KeynoteController_Edit controller = new KeynoteController_Edit();
        controller.setEditProgrammaController(this);
        controller.setProgramma(programma);
        controller.setSubScene(subscene);
        controller.setKeynote(programma.getKeynote());
        controller.setInterventiSessione(interventi);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }


}
