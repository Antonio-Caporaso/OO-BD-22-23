package View.Controller;
import Persistence.Entities.Conferenze.EventoSociale;
import Persistence.Entities.Conferenze.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.postgresql.util.PGInterval;
import tornadofx.control.DateTimePicker;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;
public class AddEventoController implements Initializable {
    @FXML
    private Spinner<Integer> minutiSpinner;

    @FXML
    private Spinner<Integer> oraSpinner;
    @FXML
    private Button addButton;
    private Programma programma;
    @FXML
    private DateTimePicker durata;
    @FXML
    private Button annullaButton;
    @FXML
    private DateTimePicker orario;
    @FXML
    private TextField tipologiaTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    void addOnAction(ActionEvent event) {
        EventoSociale e = retrieveDettagliEvento();
        try{
            PGInterval durata = new PGInterval(0,0,0,oraSpinner.getValue(), minutiSpinner.getValue(),0);
            programma.addEvento(e,durata);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }catch (SQLException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
    private EventoSociale retrieveDettagliEvento() {
        EventoSociale e = new EventoSociale();
        e.setTipologia(tipologiaTextField.getText());
        e.setProgramma(programma);
        return e;
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
