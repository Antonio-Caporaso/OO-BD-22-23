package View.Controller;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

import Persistence.DAO.SessioneDao;
import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Sponsor;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import Services.Sessioni;
import Persistence.DAO.SponsorDao;
import Services.SponsorizzazioniConferenza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
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
    private SponsorizzazioniConferenza sponsorizzazioniConferenza;
    private ObservableList<Sponsorizzazione> sponsorizzazioni;
    @FXML
    void backOnAction(ActionEvent event) {

    }

    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        Sponsor s = selezionaSponsorChoiceBox.getSelectionModel().getSelectedItem();
        SponsorizzazioniConferenza sponsorizzazioniConferenza= new SponsorizzazioniConferenza(conferenza);
        try{
            float contributo = Float.parseFloat(importoTextField.getText());
            Sponsorizzazione sp = new Sponsorizzazione(s,conferenza,contributo);
//            sponsorizzazioniConferenza.addSponsorizzazione(sp);
            SponsorizzazioneDAO SponsorizzazioneDao=new SponsorizzazioneDAO();
            try{
                SponsorizzazioneDao.saveSponsorizzazione(sp);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert( Alert.AlertType.ERROR);
            alert.setContentText("Inserire un numero");
            alert.showAndWait();
        }catch (NullPointerException e2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inserire un numero");
            alert.showAndWait();
        }
//        catch (SQLException e){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText(e.getMessage());
//            alert.showAndWait();
//        }
    }

    @FXML
    void nextOnAction(ActionEvent event) {
//        loadInserisciSessione(conferenza);
    }

    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        try{
            Sponsorizzazione sp = sponsorListView.getSelectionModel().getSelectedItem();
            if (sp == null)
                throw new NullPointerException();
            Optional<ButtonType> result = showDeleteDialog();
            if(result.get() == ButtonType.OK) {
                try{
                    sponsorizzazioniConferenza.removeSponsorizzazione(sp);
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
    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setUtente(Utente utente){
        this.user=utente;
    }

//    void loadInserisciSessione(Conferenza c){
//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/InserisciSessione.fxml"));
//            InserisciSessioneController controller = new InserisciSessioneController();
//            loader.setController(controller);
//            controller.setSubscene(subscene);
//            controller.setConferenza(c);
//            controller.setUtente(user);
//            Parent root = loader.load();
//            subscene.setRoot(root);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void setSponsorListView() {
        SponsorDao sponsorDao=new SponsorDao();
        try{
            LinkedList<Sponsor>sponsors  = sponsorDao.retrieveSponsors();
            sponsorListView.setItems(FXCollections.observableArrayList());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'AddSponsor.fxml'.";
        assert importoTextField != null : "fx:id=\"importoTextField\" was not injected: check your FXML file 'AddSponsor.fxml'.";
        assert inserisciButton != null : "fx:id=\"inserisciButton\" was not injected: check your FXML file 'AddSponsor.fxml'.";
        assert nextButton != null : "fx:id=\"nextButton\" was not injected: check your FXML file 'AddSponsor.fxml'.";
        assert rimuoviButton != null : "fx:id=\"rimuoviButton\" was not injected: check your FXML file 'AddSponsor.fxml'.";
        assert selezionaSponsorChoiceBox != null : "fx:id=\"selezionaSponsorChoiceBox\" was not injected: check your FXML file 'AddSponsor.fxml'.";
        assert sponsorListView != null : "fx:id=\"sponsorListView\" was not injected: check your FXML file 'AddSponsor.fxml'.";
        assert valutaChoiceBox != null : "fx:id=\"valutaChoiceBox\" was not injected: check your FXML file 'AddSponsor.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sponsors.loadSponsor();
        selezionaSponsorChoiceBox.setItems(sponsors.getSponsors());
//        sponsorizzazioniConferenza = new SponsorizzazioniConferenza(conferenza);
//        try{
//            sponsorizzazioniConferenza.loadSponsorizzazioni();
//        }catch (SQLException e){
//        }
//        e.printStackTrace();
//        sponsorListView.setItems(sponsorizzazioniConferenza.getSponsorizzazioni());

        ObservableList<String> valute = FXCollections.observableArrayList();
        valute.addAll("$","£","euro");
        valutaChoiceBox.setItems(valute);
    }
}

