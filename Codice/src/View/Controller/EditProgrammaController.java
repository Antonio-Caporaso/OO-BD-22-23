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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class EditProgrammaController implements Initializable {
    private Programma programma;
    private SubScene subscene;
    private ManageSessioniController manageSessioniController;
    private InterventiSessione interventi;
    private IntervalliSessione intervalli;
    private Sessione sessione;
    private EventiSocialiSessione eventi;
    @FXML
    private TableColumn<Intervento, String> abstractColumn;

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

    public void setManageSessioniController(ManageSessioniController manageSessioniController) {
        this.manageSessioniController = manageSessioniController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIntervalliTable();
        setEventiTable();
        setInterventiTable();
        setKeynoteTable();
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
                cognomeKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
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
        //eventi = new EventiSocialiSessione(programma);
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

    @FXML
    private void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessioni.fxml"));
        loader.setController(manageSessioniController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    void editEventiButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditEventi.fxml"));
        EditEventiController controller = new EditEventiController();
        controller.setManageSessioniController(manageSessioniController);
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
        EditIntervalliController controller = new EditIntervalliController();
        controller.setManageSessioniController(manageSessioniController);
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
    void editInterventiButtonOnAction(ActionEvent event) {

    }
    @FXML
    void editKeynoteButtonOnAction(ActionEvent event){

    }


}
