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
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AddEventoController implements Initializable {
    @FXML
    private Button addButton;
    private Programma programma;
    private EditEventiController editEventiController;
    private EventiSocialiSessione eventi;
    @FXML
    private Button annullaButton;

    @FXML
    private DateTimePicker orario;
    @FXML
    private ChoiceBox<String> tipologiaChoice;

    public EditEventiController getEditEventiController() {
        return editEventiController;
    }

    public void setEditEventiController(EditEventiController editEventiController) {
        this.editEventiController = editEventiController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LinkedList<String> tipologie = new LinkedList<>();
        tipologie.add("Cena");
        tipologie.add("Gita");
        tipologie.add("Proiezione");
        ObservableList<String> tip = FXCollections.observableArrayList();
        tip.setAll(tipologie);
        tipologiaChoice.setItems(tip);
    }

    @FXML
    void addOnAction(ActionEvent event) {
        EventoSociale e = new EventoSociale();
        e.setOrario(Timestamp.valueOf(orario.getDateTimeValue()));
        e.setProgramma(programma);
        e.setTipologia(tipologiaChoice.getSelectionModel().getSelectedItem());
        try{
            eventi.addEvento(e);
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

    public EventiSocialiSessione getEventi() {
        return eventi;
    }

    public void setEventi(EventiSocialiSessione eventi) {
        this.eventi = eventi;
    }

    @FXML
    void annullaOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

}
