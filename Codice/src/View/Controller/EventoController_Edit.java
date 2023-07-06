package View.Controller;

import Persistence.Entities.Conferenze.EventoSociale;
import Persistence.Entities.Conferenze.Programma;
import Services.EventiSocialiSessione;
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

public class EventoController_Edit implements Initializable {
    private EventoSociale eventoSociale;
    @FXML
    private Button editButton;
    @FXML
    private javafx.scene.control.Button addButton;
    private Programma programma;
    private EventiSocialiSessione eventi;
    @FXML
    private DateTimePicker dateTimePicker;
    @FXML
    private javafx.scene.control.Button annullaButton;
    @FXML
    private ChoiceBox<String> tipologiaChoice;

    public EventoSociale getEventoSociale() {
        return eventoSociale;
    }

    public void setEventoSociale(EventoSociale eventoSociale) {
        this.eventoSociale = eventoSociale;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTipologieChoice();
        setDateTimePicker();
    }

    private void setDateTimePicker() {
        dateTimePicker.setValue(eventoSociale.getOrario().toLocalDateTime().toLocalDate());
    }

    private void setTipologieChoice() {
        LinkedList<String> tipologie = new LinkedList<>();
        tipologie.add("Cena");
        tipologie.add("Gita");
        tipologie.add("Proiezione");
        ObservableList<String> tip = FXCollections.observableArrayList();
        tip.setAll(tipologie);
        tipologiaChoice.setItems(tip);
        tipologiaChoice.setValue(eventoSociale.getTipologia());
    }

    public Programma getProgramma() {
        return programma;
    }
    public void setProgramma(Programma programma) {
        this.programma = programma;
    }
    public EventiSocialiSessione getEventi() {
        return eventi;
    }
    public void setEventi(EventiSocialiSessione eventi) {
        this.eventi = eventi;
    }
    @FXML
    void annullaOnAction(javafx.event.ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    public void editButtonOnAction(ActionEvent event){
        eventoSociale.setOrario(Timestamp.valueOf(dateTimePicker.getDateTimeValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        eventoSociale.setTipologia(tipologiaChoice.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
        try{
            eventi.updateEvento(eventoSociale);
        }catch (SQLException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
