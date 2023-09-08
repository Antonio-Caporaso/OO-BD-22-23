package Controller.Create;

import Controller.ExceptionWindow_Controller;
import Model.Entities.Conferenze.Intervallo;
import Model.Entities.Conferenze.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

public class AddIntervallo_Controller implements Initializable {
    @FXML
    private HBox hBox;
    @FXML
    private AnchorPane popUpWindowAnchor;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmaButton;
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
    //Public Setters
    public Programma getProgramma() {
        return programma;
    }
    public void setProgramma(Programma programma) {
        this.programma = programma;
    }
    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTipologiaChoiceBox();
        loadSpinners();
    }
    private void loadExceptionWindow(String message) {
        try{
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
    //Private Methods
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
        Intervallo intervallo = new Intervallo();
        intervallo.setTipologia(tipologiaChoiceBox.getSelectionModel().getSelectedItem());
        intervallo.setProgramma(programma);
        try {
            PGInterval durata = new PGInterval(0, 0, 0, oreSpinner.getValue(), minutiSpinner.getValue(), 0);
            programma.addNewIntervallo(intervallo, durata);
            programma.loadProgramaSessione();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
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
