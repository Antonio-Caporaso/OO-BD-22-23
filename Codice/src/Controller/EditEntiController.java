package Controller;

import Model.Conferenze.Conferenza;
import Model.organizzazione.Ente;
import Presenter.EntePresenter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.net.URL;
import java.util.ResourceBundle;

public class EditEntiController implements Initializable {
    private EditConferenceController editController;
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
    private TableView<Ente> entiTable;
    private TableColumn entiOrganizzatori;
    private EntePresenter enti = new EntePresenter();
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
    }
    @FXML
    void aggiungiOnAction(ActionEvent event) {

    }

    @FXML
    void annullaOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void okOnAction(ActionEvent event) {

    }
}
