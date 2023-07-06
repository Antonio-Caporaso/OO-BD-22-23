package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.DAO.SpeakerDao;
import Persistence.Entities.Conferenze.*;
import Persistence.Entities.partecipanti.Speaker;
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

public class EditSessioneController implements Initializable {
    private Sessione sessione;
    private Conferenza conferenza;
    private ManageSessioniController manageSessioniController;
    private SubScene subScene;
    private Programma programma;
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

    public ManageSessioniController getManageSessioniController() {
        return manageSessioniController;
    }

    public void setManageSessioniController(ManageSessioniController manageSessioniController) {
        this.manageSessioniController = manageSessioniController;
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
        loader.setController(manageSessioniController);
        manageSessioniController.reloadSessioni();
        Parent root = loader.load();
        subScene.setRoot(root);
    }
    @FXML
    void editProgrammaOnAction(ActionEvent event) throws IOException {
        goToEditProgrammaWindow();
    }
    private void goToEditProgrammaWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditProgramma.fxml"));
        EditProgrammaController controller = new EditProgrammaController();
        controller.setSubscene(subScene);
        controller.setManageSessioniController(manageSessioniController);
        controller.setSessione(sessione);
        controller.setProgramma(programma);
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
        ModificaDettagliSessioneController controllerS = new ModificaDettagliSessioneController();
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
        dataInizioLabel.setText(String.valueOf(sessione.getInizio()));
        dataFineLabel.setText(String.valueOf(sessione.getFine()));
        salaLabel.setText(sessione.getLocazione().getNomeSala());
        coordinatoreLabel.setText(sessione.getCoordinatore().toString());
    }
    private void setKeynoteTable() {
        ProgrammaDao programmaDao = new ProgrammaDao();
        SpeakerDao dao = new SpeakerDao();
        Speaker keynote;
        try {
            keynote = dao.retrieveSpeakerByID(programmaDao.retrieveKeynoteSpeakerID(sessione));
            if(!(keynote.equals(null))) {
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
            programma.loadInterventi();
            orarioInterventoColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            speakerColumn.setCellValueFactory(new PropertyValueFactory<>("speaker"));
            abstractColumn.setCellValueFactory(new PropertyValueFactory<>("estratto"));
            interventiTable.setItems(programma.getInterventi());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEventiTable() {
        try {
            programma.loadEventiSociali();
            orarioEventoColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            tipologiaEventoColumn.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
            eventiTable.setItems(programma.getEventi());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setIntervalliTable() {
        try{
            programma.loadIntervalli();
            orarioIntervalloColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            tipologiaIntervalloColumn.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
            intervalliTable.setItems(programma.getIntervalli());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }




}