package View.Controller;

import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Sponsor;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import Utilities.Sponsors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.postgresql.util.PSQLException;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddSponsorController implements Initializable {
    private Conferenza conferenza;
    @FXML
    private HBox hBox;
    @FXML
    private TableColumn<Sponsorizzazione, Float> contributoColumn;
    @FXML
    private TextField importoTextField;
    @FXML
    private Button inserisciButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button rimuoviButton;
    @FXML
    private ChoiceBox<Sponsor> selezionaSponsorChoiceBox;
    @FXML
    private TableColumn<Sponsorizzazione, String> sponsorColumn;
    @FXML
    private TableView<Sponsorizzazione> sponsorTable;
    private Sponsors sponsors = new Sponsors();
    @FXML
    private SubScene subscene;
    private Utente user;
    @FXML
    private ChoiceBox<String> valutaChoiceBox;
    @FXML
    private TableColumn<Sponsorizzazione, String> valutaColumn;

    public AddSponsorController(SubScene subscene, Conferenza conferenza, Utente user) {
        this.subscene = subscene;
        this.conferenza = conferenza;
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sponsors.loadSponsor();
        setValute();
        selezionaSponsorChoiceBox.setItems(sponsors.getSponsors());
        setSponsorizzazioniTable();
    }

    private void loadAddEnti() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddEnti.fxml"));
            AddEntiController controller = new AddEntiController(subscene,conferenza,user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadVisualizzaSessione() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaSessioniConferenza.fxml"));
            VisualizzaSessioniConferenza controller = new VisualizzaSessioniConferenza(subscene,conferenza,user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSponsorizzazioniTable() {
        sponsorTable.setEditable(false);
        try {
            conferenza.loadSponsorizzazioni();
            sponsorColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("sponsor"));
            contributoColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, Float>("contributo"));
            valutaColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("valuta"));
            sponsorTable.setItems(conferenza.getSponsorizzazioni());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setValute() {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        ObservableList<String> valute = FXCollections.observableArrayList();
        try {
            valute.addAll(dao.retrieveSimboloValute());
            valutaChoiceBox.setItems(valute);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Optional<ButtonType> showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler eliminare la seguente sponsorizzazione?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadAddEnti();
    }

    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        try {
            Sponsor sponsorSelezionato = selezionaSponsorChoiceBox.getSelectionModel().getSelectedItem();
            if (sponsorSelezionato == null || importoTextField.getText().isEmpty()) {
                throw new NullPointerException();
            }
            double contributo = Double.parseDouble(importoTextField.getText());
            String valuta = valutaChoiceBox.getValue();
            Sponsorizzazione sponsorizzazione = new Sponsorizzazione(sponsorSelezionato, conferenza, contributo, valuta);
            conferenza.addSponsorizzazione(sponsorizzazione);
        } catch (PSQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Questo sponsor è già presente!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Seleziona tutti i campi prima di procedere");
            alert.showAndWait();
        }
    }

    @FXML
    void nextOnAction(ActionEvent event) {
        loadVisualizzaSessione();
    }

    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        try {
            Sponsorizzazione sp = sponsorTable.getSelectionModel().getSelectedItem();
            if (sp == null) {
                throw new NullPointerException();
            }
            Optional<ButtonType> result = showDeleteDialog();
            if (result.get() == ButtonType.OK) {
                try {
                    conferenza.removeSponsorizzazione(sp);
                    setSponsorizzazioniTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna sponsorizzazione selezionata");
            alert.showAndWait();
        }
    }
}