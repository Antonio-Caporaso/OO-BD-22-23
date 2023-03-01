package View.Controller;

import Persistence.DTO.Conferenze.Sessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class EditSessioneController implements Initializable {
    private Sessione sessione;
    private EditConferenceController editConferenceController;
    @FXML
    private Button addEventoButton;

    @FXML
    private Button addIntervalloButton;

    @FXML
    private Button addInterventoButton;

    @FXML
    private Button annullaButton;

    @FXML
    private Button confermaButton;

    @FXML
    private Label dataFineLabel;

    @FXML
    private Label dataInizioLabel;

    @FXML
    private Button deleteEventoButton;

    @FXML
    private Button deleteIntervalloButton;

    @FXML
    private Button deleteInterventoButton;

    @FXML
    private Label descrizioneLabel;

    @FXML
    private Button editDetailsButton;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label sedeLabel;

    @FXML
    private Label titleLabel;

    @FXML
    void addEventoOnAction(ActionEvent event) {

    }

    @FXML
    void addIntervalloOnAction(ActionEvent event) {

    }

    @FXML
    void addInterventoOnAction(ActionEvent event) {

    }

    @FXML
    void deleteEventoOnAction(ActionEvent event) {

    }

    @FXML
    void deleteIntervalloOnAction(ActionEvent event) {

    }

    @FXML
    void deleteInterventoButton(ActionEvent event) {

    }

    @FXML
    void annullaButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) {

    }

    @FXML
    void editDetailsOnAction(ActionEvent event) {

    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setEditController(EditConferenceController editConferenceController) {
        this.editConferenceController = editConferenceController;
    }
}
