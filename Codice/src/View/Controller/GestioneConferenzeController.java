package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Utente;
import Utilities.ConferenzeUtente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestioneConferenzeController implements Initializable {
    private ConferenzeUtente conferenze = new ConferenzeUtente();
    @FXML
    private ListView<Conferenza> conferenzeView;
    @FXML
    private Button deleteConferenzaButton;
    @FXML
    private Button modificaButton;
    private SubScene subscene;
    private Utente user;

    public GestioneConferenzeController(Utente user) {
        this.user = user;
    }

    public void deleteOnAction(ActionEvent event) {
        try {
            Conferenza c = conferenzeView.getSelectionModel().getSelectedItem();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
            ModificaConferenzaController controller = new ModificaConferenzaController();
            loader.setController(controller);
            try {
                Conferenza c = conferenzeView.getSelectionModel().getSelectedItem();
                if (c == null)
                    throw new NullPointerException();
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
            conferenzeView.setItems(conferenze.getConferenzeUtente());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
