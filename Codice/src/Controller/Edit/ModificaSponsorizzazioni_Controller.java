package Controller.Edit;

import Exceptions.BlankFieldException;
import Exceptions.SponsorizzazionPresenteException;
import Interfaces.FormChecker;
import Model.DAO.SponsorizzazioneDAO;
import Model.Entities.Conferenza;
import Model.Entities.Sponsor;
import Model.Entities.Sponsorizzazione;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificaSponsorizzazioni_Controller implements Initializable, FormChecker {
    @FXML
    private Button annullaButton;
    private Conferenza conferenza;
    @FXML
    private Button confermaButton;
    @FXML
    private TableColumn<Sponsorizzazione, Float> contributoColumn;
    @FXML
    private TextField contributoTextField;
    private ModificaConferenza_Controller controller;
    @FXML
    private ChoiceBox<Sponsor> sponsorChoice;
    @FXML
    private TableColumn<Sponsorizzazione, String> sponsorColumn;
    @FXML
    private TableView<Sponsorizzazione> sponsorTable;
    private Sponsors sponsors = new Sponsors();
    private SubScene subscene;
    @FXML
    private ChoiceBox<String> valutaChoice;
    @FXML
    private TableColumn<Sponsorizzazione, String> valutaColumn;

    public ModificaSponsorizzazioni_Controller(Conferenza conferenza, SubScene subscene, ModificaConferenza_Controller modificaConferenzaController) {
        this.conferenza = conferenza;
        this.subscene = subscene;
        this.controller = modificaConferenzaController;
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (sponsorChoice.equals(null) ||
                contributoTextField.getText().isEmpty() ||
                valutaChoice.getValue().isEmpty())
            throw new BlankFieldException();
    }

    public SubScene getsubscene() {
        return subscene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sponsors.loadSponsor();
        sponsorChoice.setItems(sponsors.getSponsors());
        setTable();
        setValute();
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public void setEditConferenceController(ModificaConferenza_Controller controller) {
        this.controller = controller;
    }

    public void setsubscene(SubScene subScene) {
        this.subscene = subScene;
    }

    private void goToEditConferenceWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenza.fxml"));
        loader.setController(controller);
        controller.setConferenza(conferenza);
        controller.setSponsorizzazioni();
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void setTable() {
        sponsorTable.setEditable(true);
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
            valutaChoice.setItems(valute);
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
    void deleteOnAction(ActionEvent event) {
        try {
            Sponsorizzazione sp = sponsorTable.getSelectionModel().getSelectedItem();
            if (sp == null)
                throw new NullPointerException();
            Optional<ButtonType> result = showDeleteDialog();
            if (result.get() == ButtonType.OK)
                conferenza.removeSponsorizzazione(sp);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna sponsorizzazione selezionata");
            alert.showAndWait();
        }
    }

    @FXML
    void inserisciSponsorOnAction(ActionEvent event) {
        try {
            Sponsor sponsorSelezionato = sponsorChoice.getSelectionModel().getSelectedItem();
            if (sponsorSelezionato == null || contributoTextField.getText().isEmpty()) {
                throw new NullPointerException();
            }
            double contributo = Double.parseDouble(contributoTextField.getText());
            String valuta = valutaChoice.getValue();
            Sponsorizzazione sponsorizzazione = new Sponsorizzazione(sponsorSelezionato, conferenza, contributo, valuta);
            conferenza.addSponsorizzazione(sponsorizzazione);
        } catch (SponsorizzazionPresenteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Questo sponsor è già presente!");
            alert.showAndWait();
        } catch (NullPointerException e) {
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
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        goToEditConferenceWindow();
    }
}
