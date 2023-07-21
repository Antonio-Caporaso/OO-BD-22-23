package View.Controller;

import Persistence.Entities.Conferenze.Programma;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPuntoController implements Initializable {
    @FXML
    private Button annullaButton;
    @FXML
    private AnchorPane popUpWindowAnchor;
    @FXML
    private ComboBox<String> opzioniComboBox;
    private Programma programma;
    @FXML
    private ProgressBar progressBar;
    private double x,y;

    public AddPuntoController(Programma programma) {
        this.programma = programma;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] opzioni = {"Evento", "Intervento", "Intervallo"};
        opzioniComboBox.getItems().addAll(opzioni);
    }

    private void openAddEventoWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddEventoSociale_Create.fxml"));
        Parent root = loader.load();
        AddEventoSocialeController_Create controller = loader.getController();
        controller.setProgramma(programma);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setX(860);
        stage.setY(360);
        stage.showAndWait();
    }

    private void openAddIntervalloWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervallo_Create.fxml"));
        Parent root = loader.load();
        AddIntervalloController_Create controller = loader.getController();
        controller.setProgramma(programma);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setX(860);
        stage.setY(360);
        stage.showAndWait();
    }

    private void openAddInterventoWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervento_Create.fxml"));
        Parent root = loader.load();
        AddInterventoController_Create controller = loader.getController();
        controller.setProgramma(programma);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setX(860);
        stage.setY(360);
        stage.showAndWait();
    }

    @FXML
    void annullaOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void avantiOnAction(ActionEvent event) {
        try {
            String tipologia = opzioniComboBox.getSelectionModel().getSelectedItem();
            if (tipologia.equals("Intervento")) {
                openAddInterventoWindow();
            } else if (tipologia.equals("Evento")) {
                openAddEventoWindow();
            } else if (tipologia.equals("Intervallo")) {
                openAddIntervalloWindow();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selezionare una tipologia per proseguire");
        } catch (IOException e) {
            e.printStackTrace();
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
