package View.Controller;

import Persistence.DAO.SpeakerDao;
import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.partecipanti.Speaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddInterventoController implements Initializable {
    private Programma programma;
    private ObservableList<Speaker> speakers;
    @FXML
    private TextArea abstractTextArea;

    @FXML
    private Button addButton;

    @FXML
    private Button addSpeakerButton;

    @FXML
    private Button annullaButton;

    @FXML
    private DateTimePicker durataDT;

    @FXML
    private ChoiceBox<Speaker> speakerChoice;

    @FXML
    private TextField titleTextField;

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    @FXML
    void addOnAction(ActionEvent event) {
        Intervento i = retrieveDettagliIntervento();
        try {
            programma.addIntervento(i,durata);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private Intervento retrieveDettagliIntervento() {
        Intervento i = new Intervento();
        i.setProgramma(programma);
        i.setSpeaker(speakerChoice.getValue());
        i.setTitolo(titleTextField.getText());
        i.setEstratto(abstractTextArea.getText());
        return i;
    }
    public void setSpeaker(Speaker speaker){
        speakers.add(speaker);
        speakerChoice.setValue(speaker);
    }
    @FXML
    void annullaOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setSpeakerChoice();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setSpeakerChoice() throws SQLException {
        SpeakerDao speakerDao = new SpeakerDao();
        speakers = FXCollections.observableArrayList();
        speakers.addAll(speakerDao.retreiveAllSpeakers());
        speakerChoice.setItems(speakers);
    }
}

