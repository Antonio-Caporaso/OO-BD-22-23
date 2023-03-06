package View.Controller;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditSessioneController implements Initializable {
    private Sessione sessione;
    private Conferenza conferenza;
    private EditConferenceController editConferenceController;
    private SubScene subScene;
    @FXML
    private Button annullaButton;
    @FXML
    private Button confermaButton;
    @FXML
    private Label oraFineLabel;
    @FXML
    private Label oraInizioLabel;
    @FXML
    private Label dataFineLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private Button editDetailsButton;
    @FXML
    private Button editProgrammaButton;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label salaLabel;
    @FXML
    private Label titleLabel;
    @FXML
    void editProgrammaOnAction(ActionEvent event) {

    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        loader.setController(editConferenceController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @FXML
    void editDetailsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditDettagliSessione.fxml"));
        EditDettagliSessioneController controllerS = new EditDettagliSessioneController();
        loader.setController(controllerS);
        controllerS.setSessione(sessione);
        controllerS.setSubscene(subScene);
        controllerS.setEditSessioneController(this);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDetails();
    }

    public void setDetails() {
        titleLabel.setText(sessione.getTitolo());
        nomeLabel.setText(sessione.getTitolo());
        dataInizioLabel.setText(String.valueOf(sessione.getDataInizio()));
        dataFineLabel.setText(String.valueOf(sessione.getDataFine()));
        oraInizioLabel.setText(String.valueOf(sessione.getOrarioInizio()));
        oraFineLabel.setText(String.valueOf(sessione.getOrarioFine()));
        salaLabel.setText(sessione.getLocazione().getNomeSala());
    }

    public void setEditController(EditConferenceController editConferenceController) {
        this.editConferenceController = editConferenceController;
    }
}