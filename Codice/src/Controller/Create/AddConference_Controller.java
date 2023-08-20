package Controller.Create;

import Interfaces.FormChecker;
import Controller.Landing_Controller;
import Exceptions.BlankFieldException;
import Exceptions.SediNonDisponibiliException;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Sede;
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
import javafx.scene.input.MouseEvent;
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
    private Button annullaButton;
    @FXML
    private Button avantiButton;
    private final Conferenze conference = new Conferenze();
    private Conferenza conferenza;
    @FXML
    private DateTimePicker dataFineDP;
    @FXML
    private DateTimePicker dataInizioDP;
    @FXML
    private TextArea descrizioneTextArea;
    private String nome;
    @FXML
    private TextField nomeConferenzaTF;
    @FXML
    private ChoiceBox<Sede> sedeChoice;
    private final Sedi sedi = new Sedi();
    @FXML
    private SubScene subscene;
    private Utente user;

    public AddConference_Controller(SubScene subscene, Utente user) {
        this.subscene = subscene;
        this.user = user;
    }

    @FXML
    public void avantiButtonOnAction(ActionEvent event) {
        try {
            checkFieldsAreBlank();
            conferenza = retrieveConferenza();
            conference.addConferenza(conferenza);
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
                || dataFineDP.getValue() == null) {
            throw new BlankFieldException();
        }
    }

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void openAddedConferenceDialogWindow() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialog");
        alert.setHeaderText("Conferenza aggiunta correttamente");
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.showAndWait();
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    private void goToAddEntiWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddEnti.fxml"));
        AddEnti_Controller controller = new AddEnti_Controller(subscene,conferenza,user);
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

    private Conferenza retrieveConferenza() {
        String nome = nomeConferenzaTF.getText();
        String descrizione = descrizioneTextArea.getText();
        LocalDateTime dataIselected = dataInizioDP.getDateTimeValue();
        LocalDateTime dataFselected = dataFineDP.getDateTimeValue();
        Timestamp dataI = Timestamp.valueOf(dataIselected);
        Timestamp dataF = Timestamp.valueOf(dataFselected);
        Sede sede = sedeChoice.getSelectionModel().getSelectedItem();
        return new Conferenza(nome, dataI, dataF, descrizione, sede, user);
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

    @FXML
    void showSedi(MouseEvent event) {
        try {
            Timestamp inizio = Timestamp.valueOf(dataInizioDP.getDateTimeValue());
            Timestamp fine = Timestamp.valueOf(dataFineDP.getDateTimeValue());
            sedi.loadSediLibere(inizio, fine);
            if (sedi.getSedi().isEmpty())
                throw new SediNonDisponibiliException();
            else
                sedeChoice.setItems(sedi.getSedi());
        } catch (SediNonDisponibiliException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inserire delle date per visualizzare le sedi libere");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

