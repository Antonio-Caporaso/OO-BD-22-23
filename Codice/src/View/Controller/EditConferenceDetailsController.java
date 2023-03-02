package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditConferenceDetailsController implements Initializable {
    private Conferenza conferenza;
    private EditConferenceController editConferenceController;
    @FXML
    private Button annullaButton;
    @FXML
    private TextField descrizioneTF;
    @FXML
    private TextField nomeTF;
    @FXML
    private Button okButton;

    public EditConferenceController getEditConferenceController() {
        return editConferenceController;
    }

    public void setEditConferenceController(EditConferenceController editConferenceController) {
        this.editConferenceController = editConferenceController;
    }

    @FXML
    void annullaOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void okOnAction(ActionEvent event) {
        conferenza.setNome(nomeTF.getText());
        conferenza.setDescrizione(descrizioneTF.getText());
        editConferenceController.setConferenza(conferenza);
        editConferenceController.setDetails();
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeTF.setText(conferenza.getNome());
        descrizioneTF.setText(conferenza.getDescrizione());
    }
}
