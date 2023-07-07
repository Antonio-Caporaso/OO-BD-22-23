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
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EditIntervalloController implements Initializable {
    private Intervallo intervallo;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    private Programma programma;
    @FXML
    private DateTimePicker dateTimePicker;
    @FXML
    private javafx.scene.control.Button annullaButton;
    @FXML
    private ChoiceBox<String> tipologiaChoice;

    public Intervallo getIntervallo() {
        return intervallo;
    }

    public void setIntervallo(Intervallo intervallo) {
        this.intervallo = intervallo;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTipologieChoice();
        setDateTimePicker();
    }

    private void setDateTimePicker() {

    }

    private void setTipologieChoice() {
        LinkedList<String> tipologie = new LinkedList<>();
        tipologie.add("Coffee Break");
        tipologie.add("Pranzo");
        ObservableList<String> tip = FXCollections.observableArrayList();
        tip.setAll(tipologie);
        tipologiaChoice.setItems(tip);
        tipologiaChoice.setValue(intervallo.getTipologia());
    }

    public Programma getProgramma() {
        return programma;
    }
    public void setProgramma(Programma programma) {
        this.programma = programma;
    }
    @FXML
    void annullaOnAction(javafx.event.ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    public void editButtonOnAction(ActionEvent event){

        intervallo.setTipologia(tipologiaChoice.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
        try{
            programma.updateIntervallo(intervallo);
        }catch (SQLException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
