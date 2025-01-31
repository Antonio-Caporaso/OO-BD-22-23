package Controller.Edit;

import Controller.AlertWindowController;
import Model.Entities.Conferenza;
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

public class ModificaConferenze_Controller extends AlertWindowController implements Initializable {
    private ConferenzeUtente conferenze = new ConferenzeUtente();
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
    private SubScene subscene;
    @FXML
    private TableView<Conferenza> tableConferenza;
    private Utente user;

    public ModificaConferenze_Controller(Utente user, SubScene subScene) {
        this.user = user;
        this.subscene = subScene;
    }

    @FXML
    public void deleteOnAction(ActionEvent event) {
        try {
            Conferenza c = tableConferenza.getSelectionModel().getSelectedItem();
            Optional<ButtonType> result = showConfirmationDialog("Sicuro di voler eliminare la seguente conferenza?");
            if (result.get() == ButtonType.OK) {
                try {
                    eliminaConferenza(c);
                    showInformationAlert();
                } catch (SQLException e) {
                    showErrorAlert(e);
                }
            }
        } catch (NullPointerException e) {
            showAlertWindow(Alert.AlertType.ERROR,"Nessuna conferenza selezionata","Assicurati di aver selezionato una conferenza");
        }
    }

    @FXML
    public void editOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenza.fxml"));
            try {
                Conferenza c = tableConferenza.getSelectionModel().getSelectedItem();
                if (c == null)
                    throw new NullPointerException();
                goToEditConferenceWindow(c, loader);
            } catch (NullPointerException e) {
                showAlertWindow(Alert.AlertType.WARNING,"Attenzione","Assicurati di aver selezionato una conferenza");
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
            loadConferenzeUtente();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showErrorAlert(SQLException e) {
        showAlertWindow(Alert.AlertType.ERROR,"Errore",e.getMessage());
    }

    private void goToEditConferenceWindow(Conferenza c, FXMLLoader loader) throws IOException {
        ModificaConferenza_Controller controller = new ModificaConferenza_Controller(c, subscene, user);
        loader.setController(controller);
        controller.setConferenza(c);
        controller.setSubscene(subscene);
        controller.setGestioneConferenzeController(this);
        controller.setUser(user);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void loadConferenzeUtente() throws SQLException {
        conferenze.loadConferenzeUtente(user);
        setTableConferenze(conferenze.getConferenzeUtente());
    }

    private void setTableConferenze(ObservableList<Conferenza> c) throws SQLException {
        tableConferenza.setEditable(false);
        nomeConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        nomeConferenzaColumn.setReorderable(false);
        inizioConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
        inizioConferenzaColumn.setReorderable(false);
        fineConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
        fineConferenzaColumn.setReorderable(false);
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        descrizioneColumn.setReorderable(false);
        sedeColumn.setCellValueFactory(new PropertyValueFactory<>("sede"));
        sedeColumn.setReorderable(false);
        tableConferenza.setItems(c);
    }

    private void showInformationAlert() {
        showAlertWindow(Alert.AlertType.INFORMATION,"Eliminazione confernza", "Eliminazione effettuata correttamente");
    }
}
