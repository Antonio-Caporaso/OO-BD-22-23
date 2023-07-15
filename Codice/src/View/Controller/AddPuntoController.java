package View.Controller;

import Persistence.Entities.Conferenze.Programma;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPuntoController implements Initializable {
    @FXML
    private Button annullaButton;
    @FXML
    private ComboBox<String> opzioniComboBox;
    private Programma programma;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private SubScene subScene;

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
        AddEventoSocialeController_Create control = new AddEventoSocialeController_Create();
        control.setProgramma(programma);
        loader.setController(control);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Aggiunta nuovo evento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    private void openAddIntervalloWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervallo_Create.fxml"));
        AddIntervalloController_Create control = new AddIntervalloController_Create();
        control.setProgramma(programma);
        loader.setController(control);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Aggiunta nuovo intervallo");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    private void openAddInterventoWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervento_Create.fxml"));
        AddInterventoController_Create control = new AddInterventoController_Create();
        control.setProgramma(programma);
        loader.setController(control);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Aggiunta nuovo intervento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
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
}
