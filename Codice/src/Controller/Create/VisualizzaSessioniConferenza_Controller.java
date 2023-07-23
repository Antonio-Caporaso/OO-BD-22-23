package Controller.Create;

import Controller.Edit.ModificaConferenza_Controller;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Sala;
import Model.Entities.Conferenze.Sessione;
import Model.Entities.Utente;
import Model.Entities.organizzazione.Organizzatore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private Button riepilogoButton;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Create/AddSponsor.fxml"));
            AddSponsor_Controller controller = new AddSponsor_Controller(subscene,conferenza,user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEditConferenza() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Edit/ModificaConferenza.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Create/InserisciSessione.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Create/ViewProgrammaSessione.fxml"));
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
    void riepilogoButtonOnAction(ActionEvent event) {
        loadEditConferenza();
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
                try {
                    conferenza.removeSessione(selected);
                    setSessioni();
                    checkAlmenoUnaSessione();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna sessione è stata selezionata");
            alert.showAndWait();
        }
    }

}
