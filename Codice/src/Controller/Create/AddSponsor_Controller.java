package Controller.Create;

import Exceptions.BlankFieldException;
import Exceptions.SponsorizzazionPresenteException;
import Model.DAO.SponsorizzazioneDAO;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Organizzazione.Sponsor;
import Model.Entities.Organizzazione.Sponsorizzazione;
import Model.Entities.Utente;
import Model.Utilities.Sponsors;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddSponsor_Controller implements Initializable {
    private final Conferenza conferenza;
    @FXML
    private TableColumn<Sponsorizzazione, Float> contributoColumn;
    @FXML
    private TextField importoTextField;
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

    public AddSponsor_Controller(SubScene subscene, Conferenza conferenza, Utente user) {
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

    private Sponsorizzazione getSponsorshipDetails() throws BlankFieldException, NumberFormatException {
        Sponsor sponsorSelezionato = selezionaSponsorChoiceBox.getSelectionModel().getSelectedItem();

        if (sponsorSelezionato == null || importoTextField.getText().isEmpty() || valutaChoiceBox.getSelectionModel().isEmpty())
            throw new BlankFieldException();

        double contributo = Double.parseDouble(importoTextField.getText());
        String valuta = valutaChoiceBox.getValue();
        return new Sponsorizzazione(sponsorSelezionato, conferenza, contributo, valuta);
    }

    private void loadMembriComitati() {
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

    private void loadVisualizzaSessioni() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/VisualizzaSessioniConferenza.fxml"));
            VisualizzaSessioniConferenza_Controller controller = new VisualizzaSessioniConferenza_Controller(subscene, conferenza, user);
            controller.setAddSponsorController(this);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
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

    protected void setSponsorizzazioniTable() {
        sponsorTable.setEditable(false);
        //conferenza.loadSponsorizzazioni();
        sponsorColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("sponsor"));
        contributoColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, Float>("contributo"));
        valutaColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("valuta"));
        sponsorTable.setItems(conferenza.getSponsorizzazioni());
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadMembriComitati();
    }

    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        try {
            Sponsorizzazione sponsorizzazione = getSponsorshipDetails();
            conferenza.addSponsorizzazione(sponsorizzazione);
        } catch (SponsorizzazionPresenteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Questo sponsor è già presente!");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inserire un valore valido per il contributo della sponsorizzazione.");
            alert.showAndWait();
        } catch (BlankFieldException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Seleziona tutti i campi prima di procedere");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Errore in fase di salvataggio");
            alert.setContentText(e.getSQLState() + ": " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void nextOnAction(ActionEvent event) {
        loadVisualizzaSessioni();
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
                conferenza.removeSponsorizzazione(sp);
                setSponsorizzazioniTable();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna sponsorizzazione selezionata");
            alert.showAndWait();
        }
    }
}