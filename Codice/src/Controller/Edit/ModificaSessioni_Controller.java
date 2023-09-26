package Controller.Edit;

import Exceptions.SessioneNotSelectedException;
import Model.Entities.Conferenza;
import Model.Entities.Sessione;
import Model.Entities.Utente;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificaSessioni_Controller implements Initializable {
    private Conferenza conferenza;
    @FXML
    private TableColumn<Sessione, Date> fineSessioneColumn;
    @FXML
    private TableColumn<Sessione, Date> inizioSessioneColumn;
    private final ModificaConferenza_Controller modificaConferenzaController;
    @FXML
    private TableColumn<Sessione, String> nomeSessioneColumn;
    @FXML
    private TableColumn<Sessione, String> salaSessioneColumn;
    private SubScene subscene;
    @FXML
    private TableView<Sessione> table;

    public ModificaSessioni_Controller(Conferenza conferenza, SubScene subscene, ModificaConferenza_Controller modificaConferenzaController) {
        this.conferenza = conferenza;
        this.subscene = subscene;
        this.modificaConferenzaController = modificaConferenzaController;
    }

    private static void showSessioneNotSelectedAlert(SessioneNotSelectedException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    @FXML
    public void addSessioneOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/AddSessione.fxml"));
        AddSessione_Controller controller = new AddSessione_Controller(conferenza, subscene, this);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    public void editSessionsOnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaSessione.fxml"));
            Sessione s = table.getSelectionModel().getSelectedItem();
            if (s == null)
                throw new SessioneNotSelectedException();
            ModificaSessione_Controller controller = new ModificaSessione_Controller(s, conferenza, this, subscene);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (SessioneNotSelectedException e) {
            showSessioneNotSelectedAlert(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conferenza.loadSessioni();
            setTable();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void reloadSessioni() {
        try {
            conferenza.loadSessioni();
            setTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    private void setTable() {
        table.setEditable(false);
        nomeSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione, String>("titolo"));
        inizioSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione, Date>("inizio"));
        fineSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione, Date>("fine"));
        salaSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione, String>("locazione"));
        table.setItems(conferenza.getSessioni());
        nomeSessioneColumn.isSortable();

    }

    private Optional<ButtonType> showConfirmationAlert(Sessione s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler rimuovere la sessione '" + s.getTitolo() + "'?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenza.fxml"));
        loader.setController(modificaConferenzaController);
        modificaConferenzaController.setSubscene(subscene);
        modificaConferenzaController.setConferenza(conferenza);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void rimuoviSessioneOnAction(ActionEvent event) {
        try {
            Sessione s = table.getSelectionModel().getSelectedItem();
            if (s == null) {
                throw new SessioneNotSelectedException();
            }
            Optional<ButtonType> result = showConfirmationAlert(s);
            if (result.get() == ButtonType.OK) {
                conferenza.removeSessione(s);
            }
        } catch (SessioneNotSelectedException e) {
            showSessioneNotSelectedAlert(e);
        }
    }
}
