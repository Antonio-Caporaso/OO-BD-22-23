package View.Controller;

import Exceptions.BlankFieldException;
import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Sponsor;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import Services.SponsorizzazioniConferenza;
import Services.Sponsors;
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

public class EditSponsorController implements Initializable, FormChecker {
    @FXML
    private TableColumn<Sponsorizzazione, Float> contributoColumn;
    @FXML
    private TableColumn<Sponsorizzazione, String> sponsorNameColumn;
    @FXML
    private TableView<Sponsorizzazione> sponsorTable;
    @FXML
    private TableColumn<Sponsorizzazione, String> valutaColumn;

    private SponsorizzazioniConferenza sponsorizzazioni;
    private Sponsors sponsors = new Sponsors();
    private Conferenza conferenza;
    private ModificaConferenzaController controller;
    private SubScene subscene;
    @FXML
    private Button annullaButton;
    @FXML
    private Button confermaButton;
    @FXML
    private TextField contributoTextField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editContributoButton;
    @FXML
    private Button inserisciButton;
    @FXML
    private ChoiceBox<Sponsor> sponsorChoice;
    @FXML
    private Label titleLabel;
    @FXML
    private ChoiceBox<String> valutaChoice;

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (sponsorChoice.equals(null) ||
                contributoTextField.getText().isEmpty() ||
                valutaChoice.getValue().isEmpty())
            throw new BlankFieldException();
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        try{
            Sponsorizzazione sp = sponsorTable.getSelectionModel().getSelectedItem();
            if (sp == null)
                throw new NullPointerException();
            Optional<ButtonType> result = showDeleteDialog();
            if(result.get() == ButtonType.OK) {
                try{
                    sponsorizzazioni.removeSponsorizzazione(sp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna sponsorizzazione selezionata");
            alert.showAndWait();
        }
    }

    private Optional<ButtonType> showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler eliminare la seguente sponsorizzazione?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    @FXML
    void inserisciSponsorOnAction(ActionEvent event) {
        try{
            checkFieldsAreBlank();
            Sponsor s = sponsorChoice.getSelectionModel().getSelectedItem();
            float contributo = Float.parseFloat(contributoTextField.getText());
            String valuta = valutaChoice.getValue();
            Sponsorizzazione sp = new Sponsorizzazione(s,conferenza,contributo,valuta);
            sponsorizzazioni.addSponsorizzazione(sp);
        }catch (BlankFieldException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Riempire tutti i campi");
            alert.showAndWait();
        }catch (NumberFormatException e){
            Alert alert = new Alert( Alert.AlertType.ERROR);
            alert.setContentText("Inserire un numero");
            alert.showAndWait();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
        loader.setController(controller);
        controller.setConferenza(conferenza);
        controller.setSponsorizzazioni();
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }
    public void setEditConferenceController(ModificaConferenzaController controller) {
        this.controller = controller;
    }

    public SubScene getsubscene() {
        return subscene;
    }

    public void setsubscene(SubScene subScene) {
        this.subscene = subScene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sponsors.loadSponsor();
        sponsorChoice.setItems(sponsors.getSponsors());
        sponsorizzazioni = new SponsorizzazioniConferenza(conferenza);
        setTable();
        setValute();
    }

    private void setTable() {
        sponsorTable.setEditable(true);
        try {
            sponsorizzazioni.loadSponsorizzazioni();
            sponsorNameColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione,String>("sponsor"));
            contributoColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione,Float>("contributo"));
            valutaColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione,String>("valuta"));
            sponsorTable.setItems(sponsorizzazioni.getSponsorizzazioni());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setValute() {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        ObservableList<String> valute = FXCollections.observableArrayList();
        try{
            valute.addAll(dao.retrieveSimboloValute());
            valutaChoice.setItems(valute);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
