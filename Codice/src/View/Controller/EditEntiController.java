package View.Controller;

import Exceptions.EntePresenteException;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Ente;
import Utilities.Enti;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditEntiController implements Initializable {
    private ModificaConferenzaController editController;
    private SubScene subScene;
    private Conferenza conferenza;
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
        entiView.setItems(conferenza.getEnti());
    }
    @FXML
    void aggiungiOnAction(ActionEvent event){
        Ente e = entiChoice.getSelectionModel().getSelectedItem();
        try{
            conferenza.addEnte(e);
        }catch (SQLException exp){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ente non aggiunto");
            alert.setContentText("Ente già presente");
            alert.showAndWait();
        }catch (EntePresenteException exp){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("EntePresenteException");
            alert.setContentText("Questo organizzatore è già presente!");
            alert.showAndWait();
        }
    }
    @FXML
    void deleteOnAction(ActionEvent event) {
        Ente e = (Ente) entiView.getSelectionModel().getSelectedItem();
        try {
            conferenza.removeEnte(e);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ente non rimosso");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
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

}
