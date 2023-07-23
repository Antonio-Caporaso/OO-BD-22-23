package Controller.Create;

import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Utente;
import Model.Entities.organizzazione.Comitato;
import Model.Entities.organizzazione.Organizzatore;
import Model.Utilities.MembriComitato;
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

public class AddComitati_Controller implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<Organizzatore> comitatoLocaleChoiceBox;
    @FXML
    private ListView<Organizzatore> comitatoLocaleListView;
    @FXML
    private ChoiceBox<Organizzatore> comitatoScientificoChoiceBox;
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
        retreiveComitati();
        loadComboBoxes();
        loadListView();
        checkAlmenoUnMembro();
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

    private void loadComboBoxes() {
        MembriComitato membriComitato = new MembriComitato(conferenza);
        try {
            membriComitato.loadOrganizzatoriEnte(conferenza.getEnti());
            comitatoScientificoChoiceBox.setItems(membriComitato.getOrganizzatoriEnti());
            comitatoLocaleChoiceBox.setItems(membriComitato.getOrganizzatoriEnti());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadListView() {
        MembriComitato membriComitato = new MembriComitato(conferenza);
        try {
            membriComitato.loadMembriComitatoScientifico();
            membriComitato.loadMembriComitatoLocale();
            comitatoScientificoListView.setItems(membriComitato.getMembriComitatoScientifico());
            comitatoLocaleListView.setItems(membriComitato.getMembriComitatoLocale());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadSelezionaEnti() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Create/AddEnti.fxml"));
            AddEnti_Controller controller = new AddEnti_Controller(subscene,conferenza,user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retreiveComitati() {
        Conferenza conferenza1 = conferenza;
        MembriComitato membriComitato = new MembriComitato(conferenza1);
        try {
            conferenza1 = membriComitato.retreiveComitati(conferenza1);
            conferenza.setComitato_s(conferenza1.getComitato_s());
            conferenza.setComitato_l(conferenza1.getComitato_l());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveMembroComitatoLocale(Organizzatore organizzatore, Comitato comitato) throws SQLException {
        MembriComitato membriComitato = new MembriComitato(conferenza);
        try {
            membriComitato.addMembroComitatoLocale(organizzatore, comitato);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore in Inserimento");
            alert.setHeaderText("L'organizzatore fa già parte del comitato");
            alert.showAndWait();
        }
    }

    private void saveMembroComitatoScientifico(Organizzatore organizzatore, Comitato comitato) {
        MembriComitato membriComitato = new MembriComitato(conferenza);
        try {
            membriComitato.addMembroComitatoScientifico(organizzatore, comitato);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore in Inserimento");
            alert.setHeaderText("L'organizzatore fa già parte del comitato");
            alert.showAndWait();
        }
    }

    //Private methods
    private Optional<ButtonType> showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler eliminare il seguente organizzatore?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    //Button methods
    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadSelezionaEnti();
    }

    @FXML
    void nextOnAction(ActionEvent event) {
        loadAggiungiSponsor();
    }

    @FXML
    void inserisciComitatoScientificoButtonOnAction(ActionEvent event) {
        Organizzatore organizzatoreSelezionato = comitatoScientificoChoiceBox.getSelectionModel().getSelectedItem();
        try {
            if (organizzatoreSelezionato == null)
                throw new NullPointerException();
            saveMembroComitatoScientifico(organizzatoreSelezionato, conferenza.getComitato_s());
            loadListView();
            checkAlmenoUnMembro();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessun organizzatore selezionato");
            alert.showAndWait();
        }
    }

    @FXML
    void inserisciComitatoLocaleButtonOnAction(ActionEvent event) {
        Organizzatore organizzatoreSelezionato = comitatoLocaleChoiceBox.getSelectionModel().getSelectedItem();
        try {
            if (organizzatoreSelezionato == null)
                throw new NullPointerException();
            saveMembroComitatoLocale(organizzatoreSelezionato, conferenza.getComitato_l());
            loadListView();
            checkAlmenoUnMembro();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessun organizzatore selezionato");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Organizzatore già presente");
            alert.showAndWait();
        }
    }

    @FXML
    void rimuoviComitatoScientificoButtonOnAction(ActionEvent event) {
        MembriComitato membriComitato = new MembriComitato(conferenza);
        Organizzatore organizzatoreScientifico = comitatoScientificoListView.getSelectionModel().getSelectedItem();
        try {
            if (organizzatoreScientifico == null) {
                throw new NullPointerException();
            }
            Optional<ButtonType> result = showDeleteDialog();
            if (result.get() == ButtonType.OK) {
                try {
                    membriComitato.removeMembroComitatoScientifico(organizzatoreScientifico, conferenza.getComitato_s());
                    loadListView();
                    checkAlmenoUnMembro();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessun organizzatore selezionato");
            alert.showAndWait();
        }
    }

    @FXML
    void rimuoviComitatoLocaleButtonOnAction(ActionEvent event) {
        MembriComitato membriComitato = new MembriComitato(conferenza);
        Organizzatore organizzatoreLocale = comitatoLocaleListView.getSelectionModel().getSelectedItem();
        try {
            if (organizzatoreLocale == null) {
                throw new NullPointerException();
            }
            Optional<ButtonType> result = showDeleteDialog();
            if (result.get() == ButtonType.OK) {
                try {
                    membriComitato.removeMembroComitatoLocale(organizzatoreLocale, conferenza.getComitato_l());
                    loadListView();
                    checkAlmenoUnMembro();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessun organizzatore selezionato");
            alert.showAndWait();
        }
    }

}
