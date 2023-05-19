package View.Controller;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Sponsor;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import Services.SponsorizzazioniConferenza;
import Services.Sponsors;
public class AggiungiSponsorController implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button backButton;
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
    private ListView<Sponsorizzazione> sponsorListView;
    @FXML
    private ChoiceBox<String> valutaChoiceBox;
    @FXML
    private SubScene subscene;
    private Conferenza conferenza;
    private Utente user;
    private Sponsors sponsors= new Sponsors();
    //Public Setters
    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setUtente(Utente utente){
        this.user=utente;
    }
    //Button methods
    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadAddOrganizzatore();
    }
    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        try{
            Sponsor sponsorSelezionato = selezionaSponsorChoiceBox.getSelectionModel().getSelectedItem();
            if(sponsorSelezionato ==null||importoTextField.getText().isEmpty()){
                throw new NullPointerException();
            }
            double contributo = Double.parseDouble(importoTextField.getText());
            SponsorizzazioniConferenza sponsorizzazioniConferenza= new SponsorizzazioniConferenza(conferenza);
            Sponsorizzazione sponsorizzazione=new Sponsorizzazione(sponsorSelezionato,conferenza,contributo);
            sponsorizzazioniConferenza.addSponsorizzazione(sponsorizzazione);
            setSponsorizzazioniListView();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Seleziona tutti i campi prima di procedere");
            alert.showAndWait();
        }
    }
    @FXML
    void nextOnAction(ActionEvent event) {
        loadInserisciSessione();
    }
    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        SponsorizzazioniConferenza sponsorizzazioniConferenza= new SponsorizzazioniConferenza(conferenza);
        try{
            Sponsorizzazione sp = sponsorListView.getSelectionModel().getSelectedItem();
            if (sp == null){
                throw new NullPointerException();
            }
            Optional<ButtonType> result = showDeleteDialog();
            if(result.get() == ButtonType.OK) {
                try{
                    sponsorizzazioniConferenza.removeSponsorizzazione(sp);
                    setSponsorizzazioniListView();
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
    //Private Methods
    private void loadInserisciSessione(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/InserisciSessione.fxml"));
            InserisciSessioneController controller = new InserisciSessioneController();
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
    private void setSponsorizzazioniListView() {
        SponsorizzazioniConferenza sponsorizzazioniConferenza=new SponsorizzazioniConferenza(conferenza);
        try{
            sponsorizzazioniConferenza.loadSponsorizzazioni();
            sponsorListView.setItems(sponsorizzazioniConferenza.getSponsorizzazioni());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadAddOrganizzatore(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddOrganizzatori.fxml"));
            AddOrganizzatoriController controller = new AddOrganizzatoriController();
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
    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sponsors.loadSponsor();
        selezionaSponsorChoiceBox.setItems(sponsors.getSponsors());
        setSponsorizzazioniListView();
        ObservableList<String> valute = FXCollections.observableArrayList();
        valute.addAll("$","£","€");
        valutaChoiceBox.setItems(valute);
    }
}