package Controller.Create;

import Controller.AlertWindowController;
import Exceptions.BlankFieldException;
import Interfaces.FormChecker;
import Model.Entities.Intervento;
import Model.Entities.Programma;
import Model.Entities.Speaker;
import Model.Utilities.Speakers;
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
import java.util.ResourceBundle;

public class AddIntervento_Controller extends AlertWindowController implements Initializable, FormChecker {
    @FXML
    private TextArea abstractTextArea;
    @FXML
    private Button cancelButton;
    @FXML
    private Spinner<Integer> minutiSpinner;
    @FXML
    private Spinner<Integer> oreSpinner;
    private Programma programma;
    @FXML
    private ChoiceBox<Speaker> speakerChoiceBox;
    private Speakers speakers = new Speakers();
    @FXML
    private TextField titoloTextField;
    private double x, y;

    public AddIntervento_Controller(Programma programma) {
        this.programma = programma;
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (titoloTextField.getText().isBlank()
                || abstractTextArea.getText().isBlank()
                || speakerChoiceBox.getValue() == null
                || (oreSpinner.getValue() == 0 & minutiSpinner.getValue() == 0))
            throw new BlankFieldException();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpeakerChoiceBox();
        loadSpinners();
    }

    private Intervento getIntervento() throws BlankFieldException {
        checkFieldsAreBlank();
        Intervento intervento = new Intervento();
        intervento.setSpeaker(speakerChoiceBox.getSelectionModel().getSelectedItem());
        intervento.setProgramma(programma);
        intervento.setEstratto(abstractTextArea.getText());
        intervento.setTitolo(titoloTextField.getText());
        return intervento;
    }

    private void loadAddSpeaker() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddSpeaker.fxml"));
            AddSpeaker_Controller controller = new AddSpeaker_Controller();
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 527, 498);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
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
    private void setSpeakerChoiceBox() {
        try {
            speakers.loadSpeakers();
            speakerChoiceBox.setItems(speakers.getSpeakers());
        } catch (SQLException e) {
            showAlertWindow(Alert.AlertType.WARNING,"Attenzione",e.getMessage());
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmButtonOnAction(ActionEvent event) {
        try {
            Intervento intervento = getIntervento();
            PGInterval durata = new PGInterval(0, 0, 0, oreSpinner.getValue(), minutiSpinner.getValue(), 0);
            programma.addIntervento(intervento, durata);
            programma.loadProgramaSessione();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (BlankFieldException | SQLException eb) {
            showAlertWindow(Alert.AlertType.ERROR,"Errore",eb.getMessage());
        }
    }

    @FXML
    void addSpeakerButtonOnAction(ActionEvent event) {
        loadAddSpeaker();
        setSpeakerChoiceBox();
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
