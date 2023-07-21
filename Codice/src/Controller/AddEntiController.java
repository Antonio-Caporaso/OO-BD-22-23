package Controller;

import Exceptions.EntePresenteException;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Utente;
import Model.Entities.organizzazione.Ente;
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
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddEntiController implements Initializable {
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

    public AddEntiController(SubScene subscene, Conferenza conferenza, Utente user) {
        this.subscene = subscene;
        this.conferenza = conferenza;
        this.user = user;
    }

    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOrganizzatoriListView();
        setOrganizzatoriChoiceBox();
        checkAlmenoUnEnte();
    }

    //Public Setters
    public void setConferenza(Conferenza c) {
        this.conferenza = c;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setUtente(Utente utente) {
        this.user = utente;
    }

    private void checkAlmenoUnEnte() {
        nextButton.setDisable(entiListView.getItems().isEmpty());
    }

    //Private Methods
    private void loadAggiungiComitati() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/AddComitati_Create.fxml"));
            AddComitati_Create controller = new AddComitati_Create();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUtente(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEditConferenza() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ModificaConferenza.fxml"));
            ModificaConferenzaController controller = new ModificaConferenzaController(conferenza,subscene,user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadErrorWindow(String messaggio) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ExceptionWindow.fxml"));
        ExceptionWindowController controller = new ExceptionWindowController(messaggio);
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Errore");
        Scene scene = new Scene(root, 400, 200);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
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

    //Button methods
    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadEditConferenza();
    }

    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        Ente enteSelezionato = entiChoiceBox.getSelectionModel().getSelectedItem();
        try {
            if (enteSelezionato == null)
                throw new NullPointerException();
            conferenza.addEnte(enteSelezionato);
            checkAlmenoUnEnte();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Questo organizzatore è già presente!");
            alert.showAndWait();
        } catch (EntePresenteException e) {
            String messaggio = "Questo organizzatore è già presente!";
            try {
                loadErrorWindow(messaggio);
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
        loadAggiungiComitati();
    }

    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        Ente enteSelezionato = entiListView.getSelectionModel().getSelectedItem();
        try {
            if (enteSelezionato == null) {
                throw new NullPointerException();
            }
            Optional<ButtonType> result = showDeleteDialog();
            if (result.get() == ButtonType.OK) {
                try {
                    conferenza.removeEnte(enteSelezionato);
                    checkAlmenoUnEnte();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessun ente selezionato");
            alert.showAndWait();
        }
    }
}
