package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
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

public class AddSessioneController implements Initializable {
    private Conferenza conferenza;
    private EditConferenceController editConferenceController;
    private SubScene subscene;
    @FXML
    private Label TitleAddSessione;
    @FXML
    private SubScene subsubscene;
    @FXML
    private Button annullaButton;
    @FXML
    private Button okButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load();
            subsubscene.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void avantiButtonOnAction(ActionEvent event) {
        TitleAddSessione.setText("Programma della sessione");
    }

    public void setEditConferenceController(EditConferenceController editConferenceController) {
        this.editConferenceController = editConferenceController;
    }

    @FXML
    void annullaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        loader.setController(editConferenceController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void okButtonOnAction(ActionEvent event) {

    }
    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
}
