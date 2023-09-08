package Controller.Create;

import Model.DAO.ComitatoDao;
import Model.DAO.OrganizzatoreDao;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Utente;
import Model.Entities.organizzazione.Ente;
import Model.Entities.organizzazione.Organizzatore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import jfxtras.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddComitati_Controller implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<Organizzatore> membroComitatoLocaleChoiceBox;
    @FXML
    private ListView<Organizzatore> comitatoLocaleListView;
    @FXML
    private ChoiceBox<Organizzatore> membroComitatoScientificoChoiceBox;
    @FXML
    private ListView<Organizzatore> comitatoScientificoListView;
    private Conferenza conferenza;
    @FXML
    private Button inserisciComitatoLocaleButton;
    @FXML
    private Button inserisciComitatoScientificoButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button rimuoviComitatoScientificoButton;
    @FXML
    private Button rimuovicomitatoLocaleButton;
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

    private void loadChoiceBoxes() throws SQLException {
        ObservableList<Organizzatore> organizzatori = FXCollections.observableArrayList();
        OrganizzatoreDao organizzatoreDao = new OrganizzatoreDao();
        for(Ente e : conferenza.getEnti()){
            organizzatori.addAll(organizzatoreDao.retrieveOrganizzatoreByEnte(e.getId_ente()));
        }
        membroComitatoScientificoChoiceBox.setItems(organizzatori);
        membroComitatoLocaleChoiceBox.setItems(organizzatori);
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

    private void checkAlmenoUnMembro() {
        nextButton.setDisable(comitatoScientificoListView.getItems().isEmpty());
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
    private void loadListView() {
        comitatoLocaleListView.getItems().addAll(conferenza.getComitato_l().getMembri());
        comitatoScientificoListView.getItems().addAll(conferenza.getComitato_s().getMembri());
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

    private void retreiveComitati() throws SQLException {
        ComitatoDao dao = new ComitatoDao();
        conferenza.setComitato_s(dao.retrieveComitatoScientificoByConferenza(conferenza));
        conferenza.setComitato_l(dao.retrieveComitatoLocaleByConferenza(conferenza));
    }

    private Optional<ButtonType> showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler eliminare il seguente organizzatore?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        goBackToSelezionaEntiWindow();
    }

    @FXML
    void nextOnAction(ActionEvent event) {
        try {
            saveComitati();
            goToAddSponsorshipsWindow();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void saveComitati() throws SQLException {
        saveMembriComitatoLocale();
        saveMembriComitatoScientifico();
    }

    private void saveMembriComitatoScientifico() throws SQLException {
        ComitatoDao comitatoDao = new ComitatoDao();
        for (Organizzatore org : conferenza.getComitato_s().getMembri()){
            comitatoDao.addMembroComitato(org,conferenza.getComitato_s());
        }
    }

    private void saveMembriComitatoLocale() throws SQLException {
        ComitatoDao comitatoDao = new ComitatoDao();
        for (Organizzatore org : conferenza.getComitato_l().getMembri()){
            comitatoDao.addMembroComitato(org,conferenza.getComitato_l());
        }
    }

    @FXML
    void inserisciMembroComitatoScientificoButtonOnAction(ActionEvent event) {
        Organizzatore org = membroComitatoScientificoChoiceBox.getSelectionModel().getSelectedItem();
        conferenza.getComitato_l().addMembro(org);
        comitatoScientificoListView.getItems().add(org);
        checkAlmenoUnMembro();
    }

    @FXML
    void inserisciMembroComitatoLocaleButtonOnAction(ActionEvent event) {
        Organizzatore org = membroComitatoLocaleChoiceBox.getSelectionModel().getSelectedItem();
        conferenza.getComitato_s().addMembro(org);
        comitatoLocaleListView.getItems().add(org);
        checkAlmenoUnMembro();
    }

    @FXML
    void rimuoviMembroComitatoScientificoButtonOnAction(ActionEvent event) {
        Organizzatore org = comitatoScientificoListView.getSelectionModel().getSelectedItem();
        Optional<ButtonType> result = showDeleteDialog();
        if(result.get() == ButtonType.OK) {
            conferenza.getComitato_s().removeMembro(org);
        }
    }

    @FXML
    void rimuoviMembroComitatoLocaleButtonOnAction(ActionEvent event) {
        Organizzatore org = comitatoLocaleListView.getSelectionModel().getSelectedItem();
        Optional<ButtonType> result = showDeleteDialog();
        if(result.get() == ButtonType.OK){
            conferenza.getComitato_l().removeMembro(org);
        }
    }

}
