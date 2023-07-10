package View.Controller;

import Exceptions.BlankFieldException;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sede;
import Persistence.Entities.Utente;
import Utilities.Conferenze;
import Utilities.Sedi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddConferenceController implements Initializable,FormChecker{
    private Utente user;
    private Sedi sedi = new Sedi();
    private String nome;
    private Conferenza conferenza;
    private Conferenze conference = new Conferenze();
    @FXML
    private Button annullaButton;
    @FXML
    private DateTimePicker dataFineDP;
    @FXML
    private DateTimePicker dataInizioDP;
    @FXML
    private Button avantiButton;
    @FXML
    private TextField nomeConferenzaTF;
    @FXML
    private ChoiceBox<Sede> sedeChoice;
    @FXML
    private TextArea descrizioneTextArea;
    @FXML
    private SubScene subscene;

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }
    @FXML
    void annullaOnAction(ActionEvent event) {
        try {
            goToLandingWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void goToLandingWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Landing.fxml"));
        LandingController controller = new LandingController();
        loader.setController(controller);
        Parent root = loader.load();
        controller.setUser(user);
        Scene landingScene = new Scene(root);
        Stage stage = (Stage) avantiButton.getScene().getWindow();
        stage.setScene(landingScene);
    }
    @FXML
    void showSedi(MouseEvent event)  {
        try{
            Timestamp inizio = Timestamp.valueOf(dataInizioDP.getDateTimeValue());
            Timestamp fine = Timestamp.valueOf(dataFineDP.getDateTimeValue());
            sedi.loadSediLibere(inizio,fine);
            sedeChoice.setItems(sedi.getSedi());
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inserire delle date per visualizzare le sedi libere");
            alert.showAndWait();
        } catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    public void avantiButtonOnAction(ActionEvent event){
        try {
            checkFieldsAreBlank();
            conferenza = retrieveConferenza();
            conference.addConferenza(conferenza);
            openAddedConferenceDialogWindow();
            loadAddEnti(conferenza);
        }catch (BlankFieldException e){
            showAlert(e);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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
        return new Conferenza(nome, dataI, dataF, descrizione,sede, user);
    }

    private void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(e.getMessage());
        alert.showAndWait();
    }

    public void openAddedConferenceDialogWindow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialog");
        alert.setHeaderText("Conferenza aggiunta correttamente");
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.showAndWait();
    }

    private void loadAddEnti(Conferenza c){
        try {
            goToAddEntiWindow();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void goToAddEntiWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddEnti.fxml"));
        AddEntiController controller = new AddEntiController();
        controller.setAddConferenceController(this);
        controller.setSubscene(subscene);
        controller.setConferenza(conferenza);
        controller.setUtente(user);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
            if (nomeConferenzaTF.getText().isBlank()
                    || descrizioneTextArea.getText().isBlank()
                    || dataInizioDP.getValue()==null
                    || dataFineDP.getValue()==null) {
                throw new BlankFieldException();
            }
    }
}

