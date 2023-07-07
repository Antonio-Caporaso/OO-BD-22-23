package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.partecipanti.Speaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChooseKeynoteController implements Initializable {
    private Programma programma;
    @FXML
    private Button annullaButton;

    @FXML
    private Button confermaButton;

    @FXML
    private ChoiceBox<Speaker> speakerChoice;

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpeakersChoiceBox();
    }

    private void setSpeakersChoiceBox() {
        ObservableList<Speaker> speakers = FXCollections.observableArrayList();
        for (Intervento i : programma.getInterventi()){
            speakers.add(i.getSpeaker());
        }
        speakerChoice.setItems(speakers);
    }

    @FXML
    void annullaButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) {
        Speaker speaker = speakerChoice.getValue();
        programma.setKeynote(speaker);
        ProgrammaDao dao = new ProgrammaDao();
        try {
            dao.saveKeynote(programma);
            Stage stage = (Stage) annullaButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
