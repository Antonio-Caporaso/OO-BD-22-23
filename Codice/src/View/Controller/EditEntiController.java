package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Ente;
import Services.Enti;
import Services.EntiOrganizzatori;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditEntiController implements Initializable {
    private ModificaConferenzaController editController;
    private SubScene subScene;
    private Conferenza conferenza;
    private EntiOrganizzatori entiOrganizzatori;
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

    public ModificaConferenzaController getEditController() {
        return editController;
    }

    public void setEditController(ModificaConferenzaController editController) {
        this.editController = editController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enti.loadEnti();
        entiChoice.setItems(enti.getEnti());
        entiOrganizzatori = new EntiOrganizzatori(conferenza);
        entiView.setItems(entiOrganizzatori.getEnti());
    }
    @FXML
    void aggiungiOnAction(ActionEvent event) throws SQLException {
        Ente e = entiChoice.getSelectionModel().getSelectedItem();
        entiOrganizzatori.addEnte(e);
    }
    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException {
        Ente e = (Ente) entiView.getSelectionModel().getSelectedItem();
        entiOrganizzatori.removeEnte(e);
    }

    @FXML
    void okOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
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

    public EntiOrganizzatori getEntiOrganizzatori() {
        return entiOrganizzatori;
    }

    public void setEntiOrganizzatori(EntiOrganizzatori entiOrganizzatori) {
        this.entiOrganizzatori = entiOrganizzatori;
    }
}
