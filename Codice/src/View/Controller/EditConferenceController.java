package View.Controller;

import Exceptions.SessioneNotSelectedException;
import Persistence.DAO.ConferenzaDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import Services.EntiOrganizzatori;
import Services.Sessioni;
import Services.SponsorizzazioniConferenza;
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
public class EditConferenceController implements Initializable {
    private Conferenza conferenza;
    private Sessioni sessioni ;
    private EntiOrganizzatori organizzatori;
    private SponsorizzazioniConferenza sponsorizzazioniConferenza;
    private SubScene subscene;
    private Utente user;
    @FXML
    private Button annullaButton;
    @FXML
    private Label budgetLabel;
    @FXML
    private Button confermaButton;
    @FXML
    private Label dataFineLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private Label descrizioneLabel;
    @FXML
    private Button editDetailsButton;
    @FXML
    private Button editEntiButton;
    @FXML
    private Button rimuoviSessioneButton;
    @FXML
    private TextArea entiView;
    @FXML
    private Button addSessioneButton;
    @FXML
    private Button editSessionsButton;
    @FXML
    private Button editSponsorshipsButton;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label sedeLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private ListView<Sessione> sessioniView;
    @FXML
    private TextArea sponsorizzazioniView;
    @FXML
    public void addSessioneOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddSessione.fxml"));
        AddSessioneController controller = new AddSessioneController();
        loader.setController(controller);
        controller.setConferenza(conferenza);
        controller.setEditConferenceController(this);
        controller.setSubscene(subscene);
        controller.setSessioni(sessioni);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    public void confermaButtonOnAction(ActionEvent event) throws IOException, SQLException {
        ConferenzaDao dao = new ConferenzaDao();
        dao.updateDettagliConferenza(conferenza);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/GestioneConferenze.fxml"));
        ManageConferenceController controller = new ManageConferenceController(user);
        loader.setController(controller);
        controller.setSubscene(subscene);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    public void editDetailsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConferenceDetails.fxml"));
        EditConferenceDetailsController controller = new EditConferenceDetailsController();
        loader.setController(controller);
        controller.setEditConferenceController(this);
        controller.setConferenza(conferenza);
        controller.setSubScene(subscene);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    public void editEntiOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditEnti.fxml"));
        EditEntiController controller = new EditEntiController();
        loader.setController(controller);
        controller.setConferenza(conferenza);
        controller.setSubScene(subscene);
        controller.setEditController(this);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    public void editSessionsOnAction(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditSessione.fxml"));
            EditSessioneController controller = new EditSessioneController();
            loader.setController(controller);
            Sessione s = sessioniView.getSelectionModel().getSelectedItem();
            if(s == null)
                throw new SessioneNotSelectedException();
            controller.setSessione(s);
            controller.setConferenza(conferenza);
            controller.setEditController(this);
            controller.setSubScene(subscene);
            Parent root = loader.load();
            subscene.setRoot(root);
        }catch(SessioneNotSelectedException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void rimuoviSessioneOnAction(ActionEvent event) {
       try {
           Sessione s = sessioniView.getSelectionModel().getSelectedItem();
           if (s == null) {
               throw new SessioneNotSelectedException();
           }
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setContentText("Sicuro di voler rimuovere la sessione '" + s.getTitolo() + "'?");
           Optional<ButtonType> result = alert.showAndWait();
           if (result.get() == ButtonType.OK) {
               try {
                   sessioni.removeSessione(s);
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }catch (SessioneNotSelectedException e){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setContentText(e.getMessage());
           alert.showAndWait();
       }
    }
    @FXML
    public void editSponsorshipOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditSponsor.fxml"));
        EditSponsorController controller = new EditSponsorController();
        loader.setController(controller);
        controller.setConferenza(conferenza);
        controller.setsubscene(subscene);
        controller.setEditConferenceController(this);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    public SubScene getsubscene() {
        return subscene;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDetails();
        setOrganizzatori();
        setSponsorizzazioni();
        setSessioni();
    }
    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    public void setSessioni() {
        sessioni = new Sessioni(conferenza);
        try{
            sessioni.loadSessioni();
            sessioniView.setItems(sessioni.getSessioni());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void setDetails() {
        this.setTitleLabel();
        nomeLabel.setText(conferenza.getNome());
        descrizioneLabel.setText(conferenza.getDescrizione());
        budgetLabel.setText(Float.toString(conferenza.getBudget()));
        dataInizioLabel.setText(conferenza.getDataInizio().toString());
        dataFineLabel.setText(conferenza.getDataFine().toString());
        sedeLabel.setText(conferenza.getSede().toString());
    }
    public void setOrganizzatori() {
        organizzatori = new EntiOrganizzatori(conferenza);
        try{
            organizzatori.loadOrganizzatori();
            entiView.setText("");
            for(Ente e: organizzatori.getEnti()){
                entiView.appendText(e.toString()+"\n");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void setSponsorizzazioni(){
        sponsorizzazioniConferenza = new SponsorizzazioniConferenza(conferenza);
        try{
            sponsorizzazioniConferenza.loadSponsorizzazioni();
            sponsorizzazioniView.setText("");
            for(Sponsorizzazione s : sponsorizzazioniConferenza.getSponsorizzazioni()){
                sponsorizzazioniView.appendText(s.toString()+"\n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setTitleLabel(){
        if(conferenza!=null)
            titleLabel.setText("Modifica della conferenza: "+ conferenza.getNome());
    }
    public void setUser(Utente user) {
        this.user = user;
    }
}
