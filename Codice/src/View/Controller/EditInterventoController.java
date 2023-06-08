package View.Controller;

import Persistence.DAO.SpeakerDao;
import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.partecipanti.Speaker;
import Services.InterventiSessione;
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

public class EditInterventoController implements Initializable {
    private Intervento intervento;
    private Programma programma;
    private ObservableList<Speaker> speakers;
    private InterventiSessione interventiSessione;
    @FXML
    private TextArea abstractTextArea;

    @FXML
    private Button editButton;

    @FXML
    private Button addSpeakerButton;

    @FXML
    private Button annullaButton;

    @FXML
    private DateTimePicker dateTimePicker;

    @FXML
    private ChoiceBox<Speaker> speakerChoice;

    @FXML
    private TextField titleTextField;

    public Intervento getIntervento() {
        return intervento;
    }

    public void setIntervento(Intervento intervento) {
        this.intervento = intervento;
    }

    public InterventiSessione getInterventiSessione() {
        return interventiSessione;
    }

    public void setInterventiSessione(InterventiSessione interventiSessione) {
        this.interventiSessione = interventiSessione;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    @FXML
    public void editButtonOnAction(ActionEvent event){
        Intervento i = retrieveDettagliIntervento();
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
        try{
            interventiSessione.updateIntervento(i);
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
        i.setOrario(Timestamp.valueOf(dateTimePicker.getDateTimeValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        return i;
    }

    @FXML
    void addSpeakerOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddSpeaker.fxml"));
        AddSpeakerController controller = new AddSpeakerController();
        controller.setEditInterventoController(this);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
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
            titleTextField.setText(intervento.getTitolo());
            setSpeakerChoice();
            speakerChoice.setValue(intervento.getSpeaker());
            abstractTextArea.setText(intervento.getEstratto());
            dateTimePicker.setValue(intervento.getOrario().toLocalDateTime().toLocalDate());
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

