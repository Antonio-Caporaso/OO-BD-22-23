package View.Controller;

import Persistence.Entities.Conferenze.Sessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class EditSessioneController implements Initializable {
    private SubScene subScene;
    private Sessione sessione;
    @FXML
    private Button annullaButton;

    @FXML
    private Label budgetLabel;

    @FXML
    private Button confermaButton;

    @FXML
    private Label dataFineLabel;

    @FXML
    private Label dataInizioLabel;

    @FXML
    private Label descrizioneLabel;

    @FXML
    private Button editDetailsButton;

    @FXML
    private Button editSessionsButton;

    @FXML
    private Button editSponsorshipsButton;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label sedeLabel;

    @FXML
    private Label titleLabel;

    @FXML
    void annullaButtonOnAction(ActionEvent event) {

    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) {

    }

    @FXML
    void editDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void editSessionsOnAction(ActionEvent event) {

    }

    @FXML
    void editSponsorshipOnAction(ActionEvent event) {

    }
    public void setSubscene(SubScene subscene) {
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
}
