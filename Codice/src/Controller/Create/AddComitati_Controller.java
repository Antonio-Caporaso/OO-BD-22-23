package Controller.Create;

import Controller.AlertWindowController;
import Exceptions.ExistingMemberException;
import Model.DAO.ComitatoDao;
import Model.DAO.OrganizzatoreDao;
import Model.Entities.Conferenza;
import Model.Entities.Utente;
import Model.Entities.Ente;
import Model.Entities.Organizzatore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddComitati_Controller extends AlertWindowController implements Initializable {
    private Conferenza conferenza;
    @FXML
    private ListView<Organizzatore> membriComitatoLocaleListView;
    @FXML
    private ListView<Organizzatore> membriComitatoScientificoListView;
    @FXML
    private ChoiceBox<Organizzatore> membroComitatoLocaleChoiceBox;
    @FXML
    private ChoiceBox<Organizzatore> membroComitatoScientificoChoiceBox;
    @FXML
    private Button nextButton;
    @FXML
    private SubScene subscene;
    private Utente user;

    public AddComitati_Controller(SubScene subscene, Conferenza conferenza, Utente user) {
        this.subscene = subscene;
        this.conferenza = conferenza;
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            retreiveComitati();
            loadChoiceBoxes();
            loadListView();
            checkAlmenoUnMembro();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkAlmenoUnMembro() {
        nextButton.setDisable(membriComitatoScientificoListView.getItems().isEmpty());
    }

    private void goBackToSelezionaEntiWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddEnti.fxml"));
            AddEnti_Controller controller = new AddEnti_Controller(subscene, conferenza, user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToAddSponsorshipsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddSponsor.fxml"));
            AddSponsor_Controller controller = new AddSponsor_Controller(subscene, conferenza, user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadChoiceBoxes() throws SQLException {
        ObservableList<Organizzatore> organizzatori = FXCollections.observableArrayList();
        OrganizzatoreDao organizzatoreDao = new OrganizzatoreDao();
        for (Ente e : conferenza.getEnti()) {
            organizzatori.addAll(organizzatoreDao.retrieveOrganizzatoreByEnte(e.getID()));
        }
        membroComitatoScientificoChoiceBox.setItems(organizzatori);
        membroComitatoLocaleChoiceBox.setItems(organizzatori);
    }

    private void loadListView() {
        try {
            conferenza.getComitato_l().loadMembri();
            conferenza.getComitato_s().loadMembri();
            membriComitatoLocaleListView.setItems(conferenza.getComitato_l().getMembri());
            membriComitatoScientificoListView.setItems(conferenza.getComitato_s().getMembri());
        } catch (SQLException e){
            showAlertWindow(Alert.AlertType.ERROR,"Errore",e.getMessage());
        }
    }

    private void retreiveComitati() throws SQLException {
        ComitatoDao dao = new ComitatoDao();
        conferenza.setComitato_s(dao.retrieveComitatoScientificoByConferenza(conferenza));
        conferenza.setComitato_l(dao.retrieveComitatoLocaleByConferenza(conferenza));
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        goBackToSelezionaEntiWindow();
    }

    @FXML
    void nextOnAction(ActionEvent event) {
        try {
            goToAddSponsorshipsWindow();
        } catch (Exception e) {
            showAlertWindow(Alert.AlertType.ERROR,"Errore in fase di aggiunta dei comitati", e.getMessage());
        }
    }

    @FXML
    void inserisciMembroComitatoScientificoButtonOnAction(ActionEvent event) {
        Organizzatore org = membroComitatoScientificoChoiceBox.getSelectionModel().getSelectedItem();
        try {
            conferenza.getComitato_s().addMembro(org);
            checkAlmenoUnMembro();
        } catch (ExistingMemberException e){
            showAlertWindow(Alert.AlertType.WARNING,"Attenzione",e.getMessage());
        }catch (SQLException e){
            showAlertWindow(Alert.AlertType.ERROR,"Errore",e.getMessage());
        }
    }

    @FXML
    void inserisciMembroComitatoLocaleButtonOnAction(ActionEvent event) {
        Organizzatore org = membroComitatoLocaleChoiceBox.getSelectionModel().getSelectedItem();
        try {
            conferenza.getComitato_l().addMembro(org);
        } catch (ExistingMemberException e){
            showAlertWindow(Alert.AlertType.WARNING,"Attenzione",e.getMessage());
        }catch (SQLException e){
            showAlertWindow(Alert.AlertType.ERROR,"Errore",e.getMessage());
        }
    }

    @FXML
    void rimuoviMembroComitatoScientificoButtonOnAction(ActionEvent event) {
        Organizzatore org = membriComitatoScientificoListView.getSelectionModel().getSelectedItem();
        if(org==null){
            showAlertWindow(Alert.AlertType.WARNING,"Attenzione","Non hai selezionato nessun membro");
        }
        else {
            Optional<ButtonType> result = showConfirmationDialog("Sicuro di voler eliminare il seguente organizzatore?");
            if (result.get() == ButtonType.OK) {
                try {
                    conferenza.getComitato_s().removeMembro(org);
                    checkAlmenoUnMembro();
                } catch (SQLException e) {
                    showAlertWindow(Alert.AlertType.ERROR,"Errore",e.getMessage());
                }
            }
        }
    }

    @FXML
    void rimuoviMembroComitatoLocaleButtonOnAction(ActionEvent event) {
        Organizzatore org = membriComitatoLocaleListView.getSelectionModel().getSelectedItem();
        if(org==null){
            showAlertWindow(Alert.AlertType.WARNING,"Attenzione","Non hai selezionato nessun membro");
        }
        else {
            Optional<ButtonType> result =showConfirmationDialog("Sicuro di voler eliminare il seguente organizzatore?");
            if (result.get() == ButtonType.OK) {
                try {
                    conferenza.getComitato_l().removeMembro(org);
                } catch (SQLException e) {
                    showAlertWindow(Alert.AlertType.ERROR,"Errore",e.getMessage());
                }
            }
        }
    }
}
