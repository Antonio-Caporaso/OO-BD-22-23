package View.Controller;

import Persistence.Entities.Conferenze.Intervallo;
import Persistence.Entities.Conferenze.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import org.postgresql.util.PGInterval;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AddIntervalloController implements Initializable {
    @FXML
    private Spinner<Integer> minutiSpinner;

    @FXML
    private Spinner<Integer> oraSpinner;
    @FXML
    private Button addButton;
    private Programma programma;
    @FXML
    private Button annullaButton;
    @FXML
    private ChoiceBox<String> tipologiaChoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LinkedList<String> tipologie = new LinkedList<>();
        tipologie.add("Coffee Break");
        tipologie.add("Pranzo");
        ObservableList<String> tip = FXCollections.observableArrayList();
        tip.setAll(tipologie);
        tipologiaChoice.setItems(tip);
    }

    @FXML
    void addOnAction(ActionEvent event) {
        Intervallo e = new Intervallo();
        e.setTipologia(tipologiaChoice.getSelectionModel().getSelectedItem());
        e.setProgramma(programma);
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
        try{
            PGInterval durata = new PGInterval(0,0,0,oraSpinner.getValue(), minutiSpinner.getValue(),0);
            programma.addIntervallo(e,durata);
        }catch (SQLException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    @FXML
    void annullaOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

}
