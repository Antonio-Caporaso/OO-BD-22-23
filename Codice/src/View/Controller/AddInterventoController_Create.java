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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.ResourceBundle;

public class AddInterventoController_Create implements Initializable {
    @FXML
    private HBox hBox;
    @FXML
    private AnchorPane popUpWindowAnchor;
    @FXML
    private TextArea abstractTextArea;
    @FXML
    private Button addSpeakerButton;
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
    private ChoiceBox<Speaker> speakerChoiceBox;
    @FXML
    private TextField titoloTextField;
    private double x, y;

    public AddInterventoController_Create(Programma programma) {
        this.programma = programma;
    }

    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpeakerChoiceBox();
        loadSpinners();
    }

    private void loadAddSpeaker() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddSpeaker_Create.fxml"));
            Parent root = loader.load();
            AddSpeakerController_Create controller = loader.getController();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 527, 498);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadExceptionWindow(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ExceptionWindow.fxml"));
            ExceptionWindowController controller = new ExceptionWindowController(message);
            loader.setController(controller);
            Parent root = loader.load();
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
    private void setSpeakerChoiceBox() {
        SpeakerDao speaker = new SpeakerDao();
        try {
            ObservableList<Speaker> speakers = FXCollections.observableArrayList();
            speakers.setAll(speaker.retreiveAllSpeakers());
            speakerChoiceBox.setItems(speakers);
        } catch (SQLException e) {
            loadExceptionWindow(e.getMessage());
        }
    }

    //ActionEvent Methods
    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmButtonOnAction(ActionEvent event) {
        Intervento intervento = new Intervento();
        intervento.setSpeaker(speakerChoiceBox.getSelectionModel().getSelectedItem());
        intervento.setProgramma(programma);
        intervento.setEstratto(abstractTextArea.getText());
        intervento.setTitolo(titoloTextField.getText());
        try {
            PGInterval durata = new PGInterval(0, 0, 0, oreSpinner.getValue(), minutiSpinner.getValue(), 0);
            programma.addIntervento(intervento, durata);
            programma.loadProgramaSessione();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            loadExceptionWindow(e.getMessage());
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
