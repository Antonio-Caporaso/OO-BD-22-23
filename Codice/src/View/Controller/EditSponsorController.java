package View.Controller;

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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditSponsorController implements Initializable {
    private SponsorizzazioniConferenza sponsorizzazioni;
    private Sponsors sponsors = new Sponsors();
    private Conferenza conferenza;
    private EditConferenceController controller;
    private SubScene subscene;
    @FXML
    private ListView<Sponsorizzazione> SponsorView;
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
    @FXML
    void deleteOnAction(ActionEvent event) {
        try{
            Sponsorizzazione sp = SponsorView.getSelectionModel().getSelectedItem();
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
        Sponsor s = sponsorChoice.getSelectionModel().getSelectedItem();
        try{
            float contributo = Float.parseFloat(contributoTextField.getText());
            Sponsorizzazione sp = new Sponsorizzazione(s,conferenza,contributo);
            sponsorizzazioni.addSponsorizzazione(sp);
        }catch (NumberFormatException e){
            Alert alert = new Alert( Alert.AlertType.ERROR);
            alert.setContentText("Inserire un numero");
            alert.showAndWait();
        }catch (NullPointerException e2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        loader.setController(controller);
        controller.setConferenza(conferenza);
        controller.setSponsorizzazioni();
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }
    public void setEditConferenceController(EditConferenceController controller) {
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
        SponsorView.setItems(sponsorizzazioni.getSponsorizzazioni());
        ObservableList<String> valute = FXCollections.observableArrayList();
        valute.addAll("$","£","euro");
        valutaChoice.setItems(valute);
    }
}
