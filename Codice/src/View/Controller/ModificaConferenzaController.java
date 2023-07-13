package View.Controller;

import Persistence.DAO.ConferenzaDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class ModificaConferenzaController implements Initializable {
    private Conferenza conferenza;
    private SubScene subscene;
    private Utente user;
    @FXML
    private Button slittaConferenzaButton;
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
    private TextArea descrizioneArea;
    private GestioneConferenzeController gestioneConferenzeController;
    @FXML
    private Button editDetailsButton;
    @FXML
    private Button editEntiButton;
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
    private TableView<Ente> entiTable;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private TableColumn<Ente, String> siglaEnte;
    @FXML
    private TableColumn<Ente, String> nomeEnte;
    @FXML
    private TableView<Sessione> table;
    @FXML
    private TableColumn<Sessione,String> nomeSessioneColumn;
    @FXML
    private TableColumn<Sessione, Date> inizioSessioneColumn;
    @FXML
    private TableColumn<Sessione, Date> fineSessioneColumn;
    @FXML
    private TableView<Sponsorizzazione> sponsorTable;
    @FXML
    private TableColumn<Sponsorizzazione, String> sponsorColumn;
    @FXML
    private TableColumn<Sponsorizzazione, Float> contributoColumn;
    @FXML
    private TableColumn<Sponsorizzazione,String> valutaColumn;
    @FXML
    private TableColumn<Sessione, String> salaSessioneColumn;
    public void setGestioneConferenzeController(GestioneConferenzeController gestioneConferenzeController) {
        this.gestioneConferenzeController = gestioneConferenzeController;
    }

    @FXML
    public void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/GestioneConferenze.fxml"));
        GestioneConferenzeController controller = new GestioneConferenzeController(user);
        loader.setController(controller);
        controller.setUser(user);
        controller.setSubscene(subscene);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    public void editDetailsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaDettagliConferenza.fxml"));
        EditDettagliConferenzaController controller = new EditDettagliConferenzaController();
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
        descrizioneArea.setText(conferenza.getDescrizione());
        dataInizioLabel.setText(conferenza.getInizio().toString());
        dataFineLabel.setText(conferenza.getFine().toString());
        sedeLabel.setText(conferenza.getSede().toString());
        indirizzoLabel.setText(conferenza.getSede().getIndirizzo().toString());
    }
    public void setOrganizzatori() {
        entiTable.setEditable(false);
        nomeEnte.setCellValueFactory(new PropertyValueFactory<>("nome"));
        siglaEnte.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        entiTable.getItems().addAll(conferenza.getEnti());
    }
    public void setSponsorizzazioni(){
        sponsorColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione,String>("sponsor"));
        contributoColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione,Float>("contributo"));
        valutaColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione,String>("valuta"));
        sponsorTable.getItems().addAll(conferenza.getSponsorizzazioni());
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
    protected void setTable(){
        table.setEditable(false);
        try{
            conferenza.loadSessioni();
        }catch (SQLException e){
            e.printStackTrace();
        }
        nomeSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,String>("titolo"));
        inizioSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Date>("inizio"));
        fineSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Date>("fine"));
        salaSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,String>("locazione"));
        table.getItems().addAll(conferenza.getSessioni());
        nomeSessioneColumn.isSortable();
    }
    @FXML
    void SlittaConferenzaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/SlittaConferenza.fxml"));
        SlittaConferenzaController controller = new SlittaConferenzaController();
        controller.setConferenza(conferenza);
        controller.setModificaConferenzaController(this);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    protected void reloadConferenza() throws SQLException {
        ConferenzaDao dao = new ConferenzaDao();
        conferenza = dao.retrieveConferenzaByID(conferenza.getId_conferenza());
        setDetails();
        setTable();
    }
}
