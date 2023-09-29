package Controller.Create;

import Controller.AlertWindowController;
import Controller.ExceptionWindow_Controller;
import Exceptions.BlankFieldException;
import Interfaces.FormChecker;
import Model.Entities.Intervallo;
import Model.Entities.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.postgresql.util.PGInterval;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AddIntervallo_Controller extends AlertWindowController implements Initializable, FormChecker {
    @FXML
    private Button cancelButton;
    @FXML
    private Spinner<Integer> minutiSpinner;
    @FXML
    private Spinner<Integer> oreSpinner;
    private Programma programma;
    @FXML
    private ChoiceBox<String> tipologiaChoiceBox;
    private double x, y;

    public AddIntervallo_Controller(Programma programma) {
        this.programma = programma;
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (tipologiaChoiceBox.getValue() == null || (oreSpinner.getValue() == 0 & minutiSpinner.getValue() == 0))
            throw new BlankFieldException();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTipologiaChoiceBox();
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

    private void setTipologiaChoiceBox() {
        LinkedList<String> tipologieLinkedList = new LinkedList<>();
        tipologieLinkedList.add("pranzo");
        tipologieLinkedList.add("coffee break");
        ObservableList<String> tipologieObservableList = FXCollections.observableArrayList();
        tipologieObservableList.setAll(tipologieLinkedList);
        tipologiaChoiceBox.setItems(tipologieObservableList);
    }

    //ActionEvent Methods
    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmButtonOnAction(ActionEvent event) {
        try {
            checkFieldsAreBlank();
            Intervallo intervallo = new Intervallo();
            intervallo.setTipologia(tipologiaChoiceBox.getSelectionModel().getSelectedItem());
            intervallo.setProgramma(programma);
            PGInterval durata = new PGInterval(0, 0, 0, oreSpinner.getValue(), minutiSpinner.getValue(), 0);
            programma.addIntervallo(intervallo, durata);
            programma.loadProgramaSessione();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (BlankFieldException | SQLException eb) {
            showAlertWindow(Alert.AlertType.ERROR,"Errore",eb.getMessage());
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
