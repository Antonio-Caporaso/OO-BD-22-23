package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.ActivityModel;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
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

public class ModificaSessioneController implements Initializable {
    @FXML
    private TableColumn<ActivityModel, String> appuntamentoTableColumn;
    private Conferenza conferenza;
    @FXML
    private Button confermaButton;
    @FXML
    private Label coordinatoreLabel;
    @FXML
    private TableColumn<ActivityModel, String> descrizioneTableColumn;
    @FXML
    private Button editDetailsButton;
    @FXML
    private Button editProgrammaButton;
    @FXML
    private Label fineLabel;
    @FXML
    private TableColumn<ActivityModel, Timestamp> fineTableColumn;
    @FXML
    private Label inizioLabel;
    @FXML
    private TableColumn<ActivityModel, Timestamp> inizioTableColumn;
    @FXML
    private Label keynoteSpeakerLabel;
    private ModificaSessioniController manageSessioniController;
    @FXML
    private Label nomeLabel;
    private Programma programma;
    @FXML
    private TableView<ActivityModel> programmaTableView;
    @FXML
    private Label salaLabel;
    private Sessione sessione;
    @FXML
    private TableColumn<ActivityModel, Speaker> speakerTableColumn;
    private SubScene subScene;
    @FXML
    private Label titleLabel;

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public ModificaSessioniController getManageSessioniController() {
        return manageSessioniController;
    }

    public void setManageSessioniController(ModificaSessioniController manageSessioniController) {
        this.manageSessioniController = manageSessioniController;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        retrieveProgrammaSessione();
        setDettagliSessione();
        setProgrammaTableView();
    }

    public void setDettagliSessione() {
        titleLabel.setText(sessione.getTitolo());
        nomeLabel.setText(sessione.getTitolo());
        inizioLabel.setText(sessione.getInizio().toString());
        fineLabel.setText(sessione.getFine().toString());
        salaLabel.setText(sessione.getLocazione().getNomeSala());
        coordinatoreLabel.setText(sessione.getCoordinatore().toString());
        keynoteSpeakerLabel.setText(programma.getKeynote().toString());
    }

    private void goToEditDettagliSessione() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaDettagliSessione.fxml"));
        ModificaDettagliSessioneController controllerS = new ModificaDettagliSessioneController();
        controllerS.setSessione(sessione);
        controllerS.setConferenza(conferenza);
        controllerS.setSubscene(subScene);
        loader.setController(controllerS);
        controllerS.setEditSessioneController(this);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    private void goToEditProgrammaWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaProgrammaSessione.fxml"));
        ModificaProgrammaSessioneController controller = new ModificaProgrammaSessioneController();
        controller.setSubscene(subScene);
        controller.setManageSessioniController(manageSessioniController);
        controller.setSessione(sessione);
        controller.setProgramma(programma);
        loader.setController(controller);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    private void goToEditSessionsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessioni.fxml"));
        loader.setController(manageSessioniController);
        manageSessioniController.reloadSessioni();
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    private void retrieveProgrammaSessione() {
        ProgrammaDao programmaDao = new ProgrammaDao();
        try {
            programma = programmaDao.retrieveProgrammaBySessione(sessione);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setProgrammaTableView() {
        try {
            programma.loadProgramaSessione();
            appuntamentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            inizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
            fineTableColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
            speakerTableColumn.setCellValueFactory(new PropertyValueFactory<>("speaker"));
            descrizioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
            programmaTableView.setItems(programma.getProgrammaSessione());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        goToEditSessionsWindow();
    }

    @FXML
    void editProgrammaOnAction(ActionEvent event) throws IOException {
        goToEditProgrammaWindow();
    }

    @FXML
    void editDetailsOnAction(ActionEvent event) throws IOException {
        goToEditDettagliSessione();
    }
}