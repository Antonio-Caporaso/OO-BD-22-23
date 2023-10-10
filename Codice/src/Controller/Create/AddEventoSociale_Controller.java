package Controller.Create;

import Controller.AlertWindowController;
import Exceptions.BlankFieldException;
import Interfaces.FormChecker;
import Model.Entities.EventoSociale;
import Model.Entities.Programma;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.postgresql.util.PGInterval;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEventoSociale_Controller extends AlertWindowController implements Initializable, FormChecker {
    @FXML
    private Button cancelButton;
    @FXML
    private Spinner<Integer> minutiSpinner;
    @FXML
    private Spinner<Integer> oreSpinner;
    private Programma programma;
    @FXML
    private TextField titoloTextField;
    private double x, y;

    public AddEventoSociale_Controller(Programma programma) {
        this.programma = programma;
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (titoloTextField.getText().isBlank() || (oreSpinner.getValue() == 0 & minutiSpinner.getValue() == 0))
            throw new BlankFieldException();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSpinners();
    }

    private void loadSpinners() {
        // Configurazione oreSpinner
        SpinnerValueFactory<Integer> oreValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0);
        oreSpinner.setValueFactory(oreValueFactory);

        // Configurazione minutiSpinner
        SpinnerValueFactory<Integer> minutiValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        minutiSpinner.setValueFactory(minutiValueFactory);
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmButtonOnAction(ActionEvent event) {
        try {
            checkFieldsAreBlank();
            EventoSociale eventoSociale = new EventoSociale();
            eventoSociale.setTipologia(titoloTextField.getText());
            eventoSociale.setProgramma(programma);
            PGInterval durata = new PGInterval(0, 0, 0, oreSpinner.getValue(), minutiSpinner.getValue(), 0);
            programma.addEvento(eventoSociale, durata);
            programma.loadProgramaSessione();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (BlankFieldException eb){
            showAlertWindow(Alert.AlertType.ERROR,"Errore",eb.getMessage());
        } catch (SQLException e){
            showAlertWindow(Alert.AlertType.ERROR, "Impossibile aggiungere nuovo evento","La durata non è compatibile con la sessione");
        }
    }

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

}
