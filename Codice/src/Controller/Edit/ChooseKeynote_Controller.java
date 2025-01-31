package Controller.Edit;

import Controller.AlertWindowController;
import Model.DAO.ProgrammaDao;
import Model.Entities.Intervento;
import Model.Entities.Programma;
import Model.Entities.Speaker;
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

public class ChooseKeynote_Controller extends AlertWindowController implements Initializable {
    @FXML
    private Button annullaButton;
    private Programma programma;
    @FXML
    private ChoiceBox<Speaker> speakerChoice;

    public ChooseKeynote_Controller(Programma programma) {
        this.programma = programma;
    }

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
        for (Intervento i : programma.getInterventi()) {
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
            showAlertWindow(Alert.AlertType.ERROR,"Errore",e.getMessage());
        }
    }

}
