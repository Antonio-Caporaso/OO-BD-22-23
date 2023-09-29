package Controller.Edit;

import Exceptions.EntePresenteException;
import Model.Entities.Conferenza;
import Model.Entities.Ente;
import Model.Utilities.Enti;
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
import java.util.ResourceBundle;

public class ModificaEntiOrganizzatori_Controller implements Initializable {
    private Conferenza conferenza;
    private ModificaConferenza_Controller editController;
    private Enti enti = new Enti();
    @FXML
    private ChoiceBox<Ente> entiChoice;
    @FXML
    private TableView<Ente> entiTable;
    @FXML
    private TableColumn<Ente, String> nomeEnte;
    @FXML
    private TableColumn<Ente, String> siglaEnte;
    private SubScene subScene;

    public ModificaEntiOrganizzatori_Controller(Conferenza conferenza, SubScene subscene, ModificaConferenza_Controller modificaConferenzaController) {
        this.conferenza = conferenza;
        this.subScene = subscene;
        this.editController = modificaConferenzaController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEntiChoiceBox();
        setEntiTable();
    }

    private void loadEntiChoiceBox() {
        enti.loadEnti();
        entiChoice.setItems(enti.getEnti());
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
            entiTable.getItems().add(e);
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
        conferenza.removeEnte(e);
        entiTable.getItems().remove(e);
    }

    @FXML
    void okOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenza.fxml"));
        loader.setController(editController);
        editController.setConferenza(conferenza);
        editController.setOrganizzatori();
        Parent root = loader.load();
        subScene.setRoot(root);
    }
}
