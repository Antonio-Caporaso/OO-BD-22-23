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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModificaEntiOrganizzatoriController implements Initializable {
    @FXML
    private Button addButton;
    @FXML
    private Button annullaButton;
    private Conferenza conferenza;
    @FXML
    private Button deleteButton;
    private ModificaConferenzaController editController;
    private final Enti enti = new Enti();
    @FXML
    private ChoiceBox<Ente> entiChoice;
    @FXML
    private TableView<Ente> entiTable;
    @FXML
    private TableColumn<Ente, String> nomeEnte;
    @FXML
    private Button okButton;
    @FXML
    private TableColumn<Ente, String> siglaEnte;
    private SubScene subScene;

    public ModificaEntiOrganizzatoriController(Conferenza conferenza, SubScene subscene, ModificaConferenzaController modificaConferenzaController) {
        this.conferenza = conferenza;
        this.subScene = subscene;
        this.editController = modificaConferenzaController;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntiTable();
    }

    private void setEntiTable() {
        entiTable.setEditable(false);
        nomeEnte.setCellValueFactory(new PropertyValueFactory<>("nome"));
        siglaEnte.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        entiTable.getItems().addAll(conferenza.getEnti());
    }

    @FXML
    void aggiungiOnAction(ActionEvent event) {
        Ente e = entiChoice.getSelectionModel().getSelectedItem();
        try {
            conferenza.addEnte(e);
        } catch (SQLException exp) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ente non aggiunto");
            alert.setContentText("Ente già presente");
            alert.showAndWait();
        } catch (EntePresenteException exp) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("EntePresenteException");
            alert.setContentText("Questo organizzatore è già presente!");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        Ente e = entiTable.getSelectionModel().getSelectedItem();
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

}
