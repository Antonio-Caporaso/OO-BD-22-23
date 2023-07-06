package View.Controller;

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
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;
public class ModificaConferenzaController implements Initializable {
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
    private GestioneConferenzeController gestioneConferenzeController;
    @FXML
    private Button editDetailsButton;
    @FXML
    private Button editEntiButton;
    @FXML
    private TextArea entiView;
    @FXML
    private Button editSessioniButton;
    @FXML
    private Button editSponsorshipsButton;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label sedeLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private TextArea sponsorizzazioniView;
    @FXML
    private TableView<Sessione> table;
    @FXML
    private TableColumn<Sessione,String> nomeSessioneColumn;
    @FXML
    private TableColumn<Sessione, Date> inizioSessioneColumn;
    @FXML
    private TableColumn<Sessione, Date> fineSessioneColumn;
    @FXML
    private TableColumn<Sessione, String> salaSessioneColumn;
    @FXML
    private TableColumn<Sessione, Time> oraInizioColumn;
    @FXML
    private TableColumn<Sessione, Time> orarioFineSessioneColumn;

    public void setGestioneConferenzeController(GestioneConferenzeController gestioneConferenzeController) {
        this.gestioneConferenzeController = gestioneConferenzeController;
    }

    @FXML
    public void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/GestioneConferenze.fxml"));
        loader.setController(gestioneConferenzeController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    public void editDetailsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConferenceDetails.fxml"));
        ModificaDettagliConferenzaController controller = new ModificaDettagliConferenzaController();
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
    @FXML
    public void editSessioniButtonOnAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessioni.fxml"));
        ManageSessioniController controller = new ManageSessioniController();
        loader.setController(controller);
        controller.setConferenza(conferenza);
        controller.setSubscene(subscene);
        controller.setSessioni(sessioni);
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
        setTable();
    }
    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    public void setDetails() {
        this.setTitleLabel();
        nomeLabel.setText(conferenza.getTitolo());
        descrizioneLabel.setText(conferenza.getDescrizione());
        budgetLabel.setText(Float.toString(conferenza.getBudget())+""+conferenza.getValuta());
        dataInizioLabel.setText(conferenza.getInizio().toString());
        dataFineLabel.setText(conferenza.getFine().toString());
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
            titleLabel.setText("Modifica della conferenza: "+ conferenza.getTitolo());
    }
    public void setUser(Utente user) {
        this.user = user;
    }
    private void setTable(){
        table.setEditable(false);
        sessioni = new Sessioni(conferenza);
        try{
            sessioni.loadSessioni();
        }catch (SQLException e){
            e.printStackTrace();
        }
        nomeSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,String>("titolo"));
        inizioSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Date>("dataInizio"));
        fineSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Date>("dataFine"));
        oraInizioColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Time>("orarioInizio"));
        orarioFineSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Time>("orarioFine"));
        salaSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,String>("locazione"));
        table.getItems().addAll(sessioni.getSessioni());
        nomeSessioneColumn.isSortable();
        oraInizioColumn.isSortable();
    }
}
