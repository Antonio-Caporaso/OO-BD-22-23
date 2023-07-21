package Controller;

import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Utente;
import Model.Utilities.ConferenzeUtente;
import javafx.collections.ObservableList;
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
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificaConferenzeController implements Initializable {
    private ConferenzeUtente conferenze = new ConferenzeUtente();
    @FXML
    private TableView<Conferenza> tableConferenza;
    @FXML
    private TableColumn<Conferenza, String> descrizioneColumn;
    @FXML
    private TableColumn<Conferenza, Timestamp> fineConferenzaColumn;
    @FXML
    private TableColumn<Conferenza, Timestamp> inizioConferenzaColumn;
    @FXML
    private TableColumn<Conferenza, String> nomeConferenzaColumn;
    @FXML
    private TableColumn<Conferenza, String> sedeColumn;
    @FXML
    private Button deleteConferenzaButton;
    @FXML
    private Button modificaButton;
    private SubScene subscene;
    private Utente user;

    public ModificaConferenzeController(Utente user,SubScene subScene) {
        this.user = user;
        this.subscene = subScene;
    }

    @FXML
    public void deleteOnAction(ActionEvent event) {
        try {
            Conferenza c = tableConferenza.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminare conferenza");
            alert.setHeaderText("Sicuro di voler eliminare la conferenza " + c.getTitolo() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    eliminaConferenza(c);
                    showInformationAlert();
                } catch (SQLException e) {
                    showErrorAlert(e);
                }
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna conferenza selezionata");
            alert.showAndWait();
        }
    }

    @FXML
    public void editOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ModificaConferenza.fxml"));
            try {
                Conferenza c = tableConferenza.getSelectionModel().getSelectedItem();
                if (c == null)
                    throw new NullPointerException();
                ModificaConferenzaController controller = new ModificaConferenzaController(c,subscene,user);
                loader.setController(controller);
                controller.setConferenza(c);
                controller.setSubscene(subscene);
                controller.setGestioneConferenzeController(this);
                controller.setUser(user);
                Parent root = loader.load();
                subscene.setRoot(root);
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Nessuna conferenza selezionata");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminaConferenza(Conferenza c) throws SQLException {
        conferenze.removeConferenza(c);
    }

    public SubScene getSubscene() {
        return subscene;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conferenze.loadConferenzeUtente(user);
            setTableConferenze(conferenze.getConferenzeUtente());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTableConferenze(ObservableList<Conferenza> c) throws SQLException {
        tableConferenza.setEditable(false);
        nomeConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        inizioConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
        fineConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        sedeColumn.setCellValueFactory(new PropertyValueFactory<>("sede"));
        tableConferenza.setItems(c);
    }

    public void showErrorAlert(SQLException e) {
        Alert errorOnDelete = new Alert(Alert.AlertType.ERROR);
        errorOnDelete.setTitle("Eliminazione conferenza");
        errorOnDelete.setContentText(e.getMessage());
        errorOnDelete.showAndWait();
    }

    private void showInformationAlert() {
        Alert noErrorOnDelete = new Alert(Alert.AlertType.INFORMATION);
        noErrorOnDelete.setTitle("Eliminazione conferenza");
        noErrorOnDelete.setContentText("Eliminazione effettuata correttamente");
        noErrorOnDelete.showAndWait();
    }
}
