package Controller;

import Model.Entities.Conferenze.EventoSociale;
import Model.Entities.Conferenze.Programma;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.postgresql.util.PGInterval;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEventoController implements Initializable {
    @FXML
    private Button addButton;
    @FXML
    private Button annullaButton;
    @FXML
    private DateTimePicker durata;
    @FXML
    private Spinner<Integer> minutiSpinner;
    @FXML
    private Spinner<Integer> oraSpinner;
    @FXML
    private DateTimePicker orario;
    private Programma programma;
    @FXML
    private TextField tipologiaTextField;

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private EventoSociale retrieveDettagliEvento() {
        EventoSociale e = new EventoSociale();
        e.setTipologia(tipologiaTextField.getText());
        e.setProgramma(programma);
        return e;
    }

    @FXML
    void addOnAction(ActionEvent event) {
        EventoSociale e = retrieveDettagliEvento();
        try {
            PGInterval durata = new PGInterval(0, 0, 0, oraSpinner.getValue(), minutiSpinner.getValue(), 0);
            programma.addEvento(e, durata);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void annullaOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }
}
