package View.Controller;

import Exceptions.EntePresenteException;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Ente;
import Services.Enti;
import Services.EntiConferenza;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditEntiController implements Initializable {
    private EditConferenceController editController;
    private SubScene subScene;
    private Conferenza conferenza;
    private EntiConferenza organizzatori;
    @FXML
    private Button addButton;
    @FXML
    private Button annullaButton;
    @FXML
    private Button deleteButton;
    @FXML
    private ChoiceBox<Ente> entiChoice;
    @FXML
    private ListView entiView;
    private Enti enti = new Enti();
    @FXML
    private Button okButton;

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public EditConferenceController getEditController() {
        return editController;
    }

    public void setEditController(EditConferenceController editController) {
        this.editController = editController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enti.loadEnti();
        entiChoice.setItems(enti.getEnti());
        organizzatori = new EntiConferenza(conferenza);
        entiView.setItems(organizzatori.getEnti());
    }
    @FXML
    void aggiungiOnAction(ActionEvent event) {
        Ente e = entiChoice.getSelectionModel().getSelectedItem();
        try{
            conferenza.addEnte(e);
            organizzatori.addEnte(e);
        }catch (EntePresenteException exception){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        loader.setController(editController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        Ente e = (Ente) entiView.getSelectionModel().getSelectedItem();
        organizzatori.removeEnte(e);
    }

    @FXML
    void okOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        loader.setController(editController);
        editController.setConferenza(conferenza);
        editController.setOrganizzatori();
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }
}
