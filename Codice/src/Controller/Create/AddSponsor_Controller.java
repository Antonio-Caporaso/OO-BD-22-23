package Controller.Create;

import Controller.AlertWindowController;
import Exceptions.BlankFieldException;
import Exceptions.SponsorizzazionPresenteException;
import Model.DAO.SponsorizzazioneDAO;
import Model.Entities.Conferenza;
import Model.Entities.Sponsor;
import Model.Entities.Sponsorizzazione;
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

public class AddSponsor_Controller extends AlertWindowController implements Initializable {
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

    protected void setSponsorizzazioniTable() {
        sponsorTable.setEditable(false);
        //conferenza.loadSponsorizzazioni();
        sponsorColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("sponsor"));
        sponsorColumn.setReorderable(false);
        contributoColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, Float>("contributo"));
        contributoColumn.setReorderable(false);
        valutaColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("valuta"));
        valutaColumn.setReorderable(false);
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
            showAlertWindow(Alert.AlertType.ERROR,"Errore","Questo sponsor è già presente!");
        } catch (NumberFormatException e) {
            showAlertWindow(Alert.AlertType.ERROR,"Errore","Inserire un valore valido per il contributo della sponsorizzazione.");
        } catch (BlankFieldException | SQLException eb) {
                showAlertWindow(Alert.AlertType.ERROR,"Errore",eb.getMessage());
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
            if (sp == null)
                throw new NullPointerException();
            Optional<ButtonType> result = showConfirmationDialog("Sicuro di voler eliminare la sponsorizzazione seguente?");
            if (result.get() == ButtonType.OK) {
                conferenza.removeSponsorizzazione(sp);
                setSponsorizzazioniTable();
            }
        } catch (NullPointerException e) {
            showAlertWindow(Alert.AlertType.ERROR,"Errore","Nessuna sponsorizzazione selezionata");
        }
    }
}