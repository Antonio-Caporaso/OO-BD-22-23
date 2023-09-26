package Controller.Create;

import Controller.ExceptionWindow_Controller;
import Exceptions.BlankFieldException;
import Interfaces.FormChecker;
import Model.Entities.Conferenze.EventoSociale;
import Model.Entities.Conferenze.Programma;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.postgresql.util.PGInterval;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEventoSociale_Controller implements Initializable, FormChecker {
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

    private void loadExceptionWindow(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/ExceptionWindow.fxml"));
            Parent root = loader.load();
            ExceptionWindow_Controller controller = loader.getController();
            controller.setErrorMessageLabel(message);
            Stage stage = new Stage();
            stage.setTitle("Errore");
            Scene scene = new Scene(root, 400, 200);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        EventoSociale eventoSociale = new EventoSociale();
        eventoSociale.setTipologia(titoloTextField.getText());
        eventoSociale.setProgramma(programma);
        try {
            checkFieldsAreBlank();
            PGInterval durata = new PGInterval(0, 0, 0, oreSpinner.getValue(), minutiSpinner.getValue(), 0);
            programma.addEvento(eventoSociale, durata);
            programma.loadProgramaSessione();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (BlankFieldException eb) {
            loadExceptionWindow(eb.getMessage());
        } catch (SQLException e) {
            loadExceptionWindow(e.getMessage());
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
