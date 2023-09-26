package Controller.Create;

import Controller.Edit.ModificaConferenza_Controller;
import Controller.ExceptionWindow_Controller;
import Exceptions.EntePresenteException;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Organizzazione.Ente;
import Model.Entities.Utente;
import Model.Utilities.Enti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddEnti_Controller implements Initializable {
    private Conferenza conferenza;
    private Enti enti = new Enti();
    @FXML
    private ChoiceBox<Ente> entiChoiceBox;
    @FXML
    private ListView<Ente> entiListView;
    @FXML
    private Button nextButton;
    @FXML
    private SubScene subscene;
    private Utente user;

    public AddEnti_Controller(SubScene subscene, Conferenza conferenza, Utente user) {
        this.subscene = subscene;
        this.conferenza = conferenza;
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOrganizzatoriListView();
        setOrganizzatoriChoiceBox();
        checkAlmenoUnEnte();
    }

    private void checkAlmenoUnEnte() {
        nextButton.setDisable(entiListView.getItems().isEmpty());
    }

    private void goBackToEditConferenza() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenza.fxml"));
            ModificaConferenza_Controller controller = new ModificaConferenza_Controller(conferenza, subscene, user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToAddComitatiWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddComitati.fxml"));
            AddComitati_Controller controller = new AddComitati_Controller(subscene, conferenza, user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadErrorWindow(String messaggio) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/ExceptionWindow.fxml"));
            Parent root = loader.load();
            ExceptionWindow_Controller controller = loader.getController();
            controller.setErrorMessageLabel(messaggio);
            Stage stage = new Stage();
            stage.setTitle("Errore");
            Scene scene = new Scene(root, 400, 200);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setOrganizzatoriChoiceBox() {
        try {
            enti.loadEnti();
            entiChoiceBox.setItems(enti.getEnti());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setOrganizzatoriListView() {
        try {
            conferenza.loadOrganizzatori();
            entiListView.setItems(conferenza.getEnti());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Optional<ButtonType> showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler eliminare il seguente ente?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        goBackToEditConferenza();
    }

    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        Ente enteSelezionato = entiChoiceBox.getSelectionModel().getSelectedItem();
        try {
            if (enteSelezionato == null)
                throw new NullPointerException();
            conferenza.addEnte(enteSelezionato);
            checkAlmenoUnEnte();
        } catch (EntePresenteException e) {
            try {
                loadErrorWindow(e.getMessage());
            } catch (IOException ex) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessun ente selezionato");
            alert.showAndWait();
        }
    }

    @FXML
    void nextOnAction(ActionEvent event) {
        goToAddComitatiWindow();
    }

    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        Ente enteSelezionato = entiListView.getSelectionModel().getSelectedItem();
        try {
            if (enteSelezionato == null)
                throw new NullPointerException();
            Optional<ButtonType> result = showDeleteDialog();
            if (result.get() == ButtonType.OK) {
                conferenza.removeEnte(enteSelezionato);
                checkAlmenoUnEnte();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessun ente selezionato");
            alert.showAndWait();
        }
    }
}
