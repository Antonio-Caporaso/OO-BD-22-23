package Controller.Create;

import Controller.Edit.ModificaConferenza_Controller;
import Controller.Landing_Controller;
import Model.DAO.ConferenzaDao;
import Model.DAO.SponsorizzazioneDAO;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Sala;
import Model.Entities.Conferenze.Sessione;
import Model.Entities.Utente;
import Model.Entities.organizzazione.Organizzatore;
import Model.Entities.organizzazione.Sponsorizzazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jfxtras.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;

public class VisualizzaSessioniConferenza_Controller implements Initializable {
    @FXML
    private Button aggiungiProgrammaButton;
    @FXML
    private Button backButton;
    @FXML
    private TableColumn<Sessione, Organizzatore> chairTableColumn;
    private Conferenza conferenza;
    @FXML
    private TableColumn<Sessione, Time> fineTableColumn;
    @FXML
    private TableColumn<Sessione, Time> inizioTableColumn;
    @FXML
    private Button inserisciButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button rimuoviButton;
    @FXML
    private TableColumn<Sessione, Sala> salaTableColumn;
    @FXML
    private TableView<Sessione> sessioniTableView;
    @FXML
    private SubScene subscene;
    @FXML
    private TableColumn<Sessione, String> titoloTableColumn;
    private AddSponsor_Controller addSponsorController;
    private Utente user;

    public VisualizzaSessioniConferenza_Controller(SubScene subscene, Conferenza conferenza, Utente user) {
        this.subscene = subscene;
        this.conferenza = conferenza;
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSessioni();
        checkAlmenoUnaSessione();
    }

    private void loadAggiungiSponsor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddSponsor.fxml"));
            loader.setController(addSponsorController);
            addSponsorController.setSponsorizzazioniTable();
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEditConferenza() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenza.fxml"));
            ModificaConferenza_Controller controller = new ModificaConferenza_Controller(conferenza,subscene,user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadInserisciSessione() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/InserisciSessione.fxml"));
            InserisciSessione_Controller controller = new InserisciSessione_Controller();
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

    private void loadViewProgramma() {
        try {
            Sessione s = sessioniTableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/ViewProgrammaSessione.fxml"));
            ViewProgramma_Controller controller = new ViewProgramma_Controller();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUser(user);
            controller.setSessione(s);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSessioni() {
        try {
            conferenza.loadSessioni();
            titoloTableColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
            inizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
            fineTableColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
            salaTableColumn.setCellValueFactory(new PropertyValueFactory<>("locazione"));
            chairTableColumn.setCellValueFactory(new PropertyValueFactory<>("coordinatore"));
            sessioniTableView.setItems(conferenza.getSessioni());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Optional<ButtonType> showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler eliminare la seguente sessione?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
    private void checkAlmenoUnaSessione(){
        aggiungiProgrammaButton.setDisable(sessioniTableView.getItems().isEmpty());
    }

    //Button Methods
    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        loadInserisciSessione();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler salvare la conferenza ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
            saveConference();
        else
            deleteConference();
        goToLandingWindow();
    }

    private void deleteConference() throws SQLException {
        ConferenzaDao dao = new ConferenzaDao();
        dao.deleteConferenza(conferenza);
    }

    private void saveConference() throws SQLException {
        saveSponsorships();
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setContentText("Conferenza aggiunta correttamente");
        alert1.showAndWait();
        loadEditConferenza();
    }

    private void saveSponsorships() throws SQLException {
        for(Sponsorizzazione sp : conferenza.getSponsorizzazioni()){
            SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
            dao.saveSponsorizzazione(sp);
        }
    }

    private void goToLandingWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Landing.fxml"));
        Landing_Controller controller = new Landing_Controller(user);
        loader.setController(controller);
        Parent root = loader.load();
        Scene landingScene = new Scene(root);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.setScene(landingScene);
    }

    @FXML
    void aggiungiProgrammaButtonOnAction(ActionEvent event) {
        try {
            Sessione selected = sessioniTableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new NullPointerException();
            } else {
                loadViewProgramma();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Seleziona una sessione prima di procedere");
            alert.showAndWait();
        }
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadAggiungiSponsor();
    }

    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        try {
            Sessione selected = sessioniTableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new NullPointerException();
            }
            Optional<ButtonType> result = showDeleteDialog();
            if (result.get() == ButtonType.OK) {
                    conferenza.removeSessione(selected);
                    setSessioni();
                    checkAlmenoUnaSessione();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna sessione è stata selezionata");
            alert.showAndWait();
        }
    }

    public void setAddSponsorController(AddSponsor_Controller addSponsorController) {
        this.addSponsorController = addSponsorController;
    }
}
