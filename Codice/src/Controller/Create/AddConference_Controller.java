package Controller.Create;

import Controller.Landing_Controller;
import Exceptions.BlankFieldException;
import Interfaces.FormChecker;
import Model.DAO.ConferenzaDao;
import Model.Entities.Conferenza;
import Model.Entities.Sede;
import Model.Entities.Utente;
import Model.Utilities.Conferenze;
import Model.Utilities.Sedi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddConference_Controller implements Initializable, FormChecker {
    @FXML
    private Button avantiButton;
    private Conferenze conference = new Conferenze();
    private Conferenza conferenza;
    @FXML
    private DateTimePicker dataFineDP;
    @FXML
    private DateTimePicker dataInizioDP;
    @FXML
    private TextArea descrizioneTextArea;
    @FXML
    private TextField nomeConferenzaTF;
    @FXML
    private ChoiceBox<Sede> sedeChoice;
    private Sedi sedi = new Sedi();
    @FXML
    private SubScene subscene;
    private Utente user;

    public AddConference_Controller(SubScene subscene, Utente user) {
        this.subscene = subscene;
        this.user = user;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            sedi.loadSedi();
            sedeChoice.setItems(sedi.getSedi());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void avantiButtonOnAction(ActionEvent event) {
        try {
            checkFieldsAreBlank();
            conferenza = costruisciConferenza();
            conference.addConferenza(conferenza);
            saveConferenza(conferenza);
            openAddedConferenceDialogWindow();
            loadAddEnti(conferenza);
        } catch (BlankFieldException e) {
            showAlert(e);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (nomeConferenzaTF.getText().isBlank()
                || descrizioneTextArea.getText().isBlank()
                || dataInizioDP.getValue() == null
                || dataFineDP.getValue() == null
                || sedeChoice.getValue()==null) {
            throw new BlankFieldException();
        }
    }

    public void openAddedConferenceDialogWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aggiunta conferenza");
        alert.setHeaderText("Conferenza aggiunta correttamente");
        alert.showAndWait();
    }

    private void goToAddEntiWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddEnti.fxml"));
        AddEnti_Controller controller = new AddEnti_Controller(subscene, conferenza, user);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void goToLandingWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Landing.fxml"));
        Landing_Controller controller = new Landing_Controller(user);
        loader.setController(controller);
        Parent root = loader.load();
        Scene landingScene = new Scene(root);
        Stage stage = (Stage) avantiButton.getScene().getWindow();
        stage.setScene(landingScene);
    }

    private void loadAddEnti(Conferenza c) {
        try {
            goToAddEntiWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Conferenza costruisciConferenza() {
        String nome = nomeConferenzaTF.getText();
        String descrizione = descrizioneTextArea.getText();
        LocalDateTime dataIselected = dataInizioDP.getDateTimeValue();
        LocalDateTime dataFselected = dataFineDP.getDateTimeValue();
        Timestamp dataI = Timestamp.valueOf(dataIselected);
        Timestamp dataF = Timestamp.valueOf(dataFselected);
        Sede sede = sedeChoice.getSelectionModel().getSelectedItem();
        return new Conferenza(nome, dataI, dataF, descrizione, sede, user);
    }

    private void saveConferenza(Conferenza conferenza) throws SQLException {
        ConferenzaDao d = new ConferenzaDao();
        int id = d.saveConferenza(conferenza);
        conferenza.setId_conferenza(id);
    }

    private void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(e.getMessage());
        alert.showAndWait();
    }

    @FXML
    void annullaOnAction(ActionEvent event) {
        try {
            goToLandingWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

