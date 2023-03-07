package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.*;
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
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddProgrammaController implements Initializable {
    private EditConferenceController editConferenceController;
    private Conferenza conferenza;
    private Sessione sessione;
    private SubScene subScene;
    private InterventiSessione interventi;
    private IntervalliSessione intervalli;
    private AddSessioneController addSessioneController;
    @FXML
    private Button annullaButton;
    @FXML
    private Button editEventiButton;
    @FXML
    private Button editIntervalliButton;
    @FXML
    private Button editInterventiButton;
    @FXML
    private ListView<EventoSociale> eventiList;
    @FXML
    private ListView<Intervallo> intervalliList;
    @FXML
    private ListView<Intervento> interventiList;
    private Programma programma;
    private EventiSocialiSessione eventi;
    @FXML
    private Button fineButton;

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        EditConferenceController controller = new EditConferenceController();
        controller.setConferenza(conferenza);
        loader.setController(controller);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProgrammaDao dao = new ProgrammaDao();
        try {
            programma = dao.retrieveProgrammaBySessione(sessione);
            eventi = new EventiSocialiSessione(programma);
            eventi.loadEventiSociali();
            eventiList.setItems(eventi.getEventi());
            intervalli=new IntervalliSessione(programma);
            intervalli.loadIntervalli();
            intervalliList.setItems(intervalli.getIntervalli());
            interventi = new InterventiSessione(programma);
            intervalli.loadIntervalli();
            intervalliList.setItems(intervalli.getIntervalli());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEditConferenceController(EditConferenceController editConferenceController) {
        this.editConferenceController=editConferenceController;
    }

    @FXML
    void editEventiOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditEventi.fxml"));
        EditEventiController controller = new EditEventiController();
        controller.setEventi(eventi);
        controller.setProgramma(programma);
        controller.setSubScene(subScene);
        loader.setController(controller);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @FXML
    void editIntervalliOnAction(ActionEvent event) {

    }

    @FXML
    void editInterventiOnAction(ActionEvent event) {

    }

    @FXML
    void fineButtonOnAction(ActionEvent event) throws IOException, SQLException {
        ProgrammaDao dao = new ProgrammaDao();
        dao.updateProgramma(programma);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        EditConferenceController controller = new EditConferenceController();
        controller.setConferenza(conferenza);
        loader.setController(controller);
        Parent root = loader.load();
        subScene.setRoot(root);
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

    public AddSessioneController getAddSessioneController() {
        return addSessioneController;
    }

    public void setAddSessioneController(AddSessioneController addSessioneController) {
        this.addSessioneController = addSessioneController;
    }
}
