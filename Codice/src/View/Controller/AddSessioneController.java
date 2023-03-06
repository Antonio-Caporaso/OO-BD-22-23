package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sala;
import Services.Sessioni;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddSessioneController implements Initializable {
    private Conferenza conferenza;
    private Sessioni sessioni;
    private EditConferenceController editConferenceController;
    private SubScene subscene;
    @FXML
    private Button annullaButton;

    @FXML
    private DateTimePicker fineDateTimePicker;

    @FXML
    private DateTimePicker inizioDateTimePicker;

    @FXML
    private Button nextButton;

    @FXML
    private TextField nomeTF;

    @FXML
    private ChoiceBox<Sala> saleChoice;

    @FXML
    void nextOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setEditConferenceController(EditConferenceController editConferenceController) {
        this.editConferenceController = editConferenceController;
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        loader.setController(editConferenceController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public Sessioni getSessioni() {
        return sessioni;
    }

    public void setSessioni(Sessioni sessioni) {
        this.sessioni = sessioni;
    }
}
