package Controller.Create;

import Controller.Edit.ModificaConferenza_Controller;
import Controller.ExceptionWindow_Controller;
import Exceptions.EntePresenteException;
import Model.DAO.ConferenzaDao;
import Model.DAO.EnteDao;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Utente;
import Model.Entities.organizzazione.Ente;
import Model.Utilities.Enti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AddEnti_Controller implements Initializable {
    private Enti enti = new Enti();
    private Conferenza conferenza;
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
            EnteDao enteDao = new EnteDao();
            ObservableList<Ente> enti = FXCollections.observableArrayList();
            enti.addAll(enteDao.retrieveEntiOrganizzatori(conferenza));
            entiListView.setItems(enti);
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
            entiListView.getItems().add(enteSelezionato);
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
        try {
            saveEnti();
            goToAddComitatiWindow();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void saveEnti() throws SQLException {
        EnteDao dao = new EnteDao();
        for(Ente ente : conferenza.getEnti()){
            System.out.println("ente:"+ente.getId_ente()+" conference: "+conferenza.getId_conferenza());
            dao.saveEnteOrganizzatore(ente,conferenza);
        }
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
