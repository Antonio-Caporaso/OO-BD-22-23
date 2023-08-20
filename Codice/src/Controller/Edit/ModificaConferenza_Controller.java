package Controller.Edit;

import Model.DAO.ConferenzaDao;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Sessione;
import Model.Entities.Utente;
import Model.Entities.organizzazione.Ente;
import Model.Entities.organizzazione.Sponsorizzazione;
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

public class ModificaConferenza_Controller implements Initializable {
    @FXML
    private Button annullaButton;
    @FXML
    private Label budgetLabel;
    private Conferenza conferenza;
    @FXML
    private Button confermaButton;
    @FXML
    private TableColumn<Sponsorizzazione, Float> contributoColumn;
    @FXML
    private Label dataFineLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private TextArea descrizioneArea;
    @FXML
    private Button editDetailsButton;
    @FXML
    private Button editEntiButton;
    @FXML
    private Button editSessioniButton;
    @FXML
    private Button editSponsorshipsButton;
    @FXML
    private TableView<Ente> entiTable;
    @FXML
    private TableColumn<Sessione, Date> fineSessioneColumn;

    private ModificaConferenze_Controller modificaConferenzeController;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private TableColumn<Sessione, Date> inizioSessioneColumn;
    @FXML
    private TableColumn<Ente, String> nomeEnte;
    @FXML
    private Label nomeLabel;
    @FXML
    private TableColumn<Sessione, String> nomeSessioneColumn;
    @FXML
    private TableColumn<Sessione, String> salaSessioneColumn;
    @FXML
    private Label sedeLabel;
    @FXML
    private TableColumn<Ente, String> siglaEnte;
    @FXML
    private Button slittaConferenzaButton;
    @FXML
    private TableColumn<Sponsorizzazione, String> sponsorColumn;
    @FXML
    private TableView<Sponsorizzazione> sponsorTable;
    private SubScene subscene;
    @FXML
    private TableView<Sessione> table;
    @FXML
    private Label titleLabel;
    private Utente user;
    @FXML
    private TableColumn<Sponsorizzazione, String> valutaColumn;

    public ModificaConferenza_Controller(Conferenza c, SubScene subscene, Utente user) {
        this.conferenza = c;
        this.subscene = subscene;
        this.user = user;
    }

    @FXML
    public void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenze.fxml"));
        ModificaConferenze_Controller controller = new ModificaConferenze_Controller(user,subscene);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    public void editDetailsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaDettagliConferenza.fxml"));
        ModificaDettagliConferenza_Controller controller = new ModificaDettagliConferenza_Controller(conferenza,subscene,this);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    public void editEntiOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaEntiOrganizzatori.fxml"));
        ModificaEntiOrganizzatori_Controller controller = new ModificaEntiOrganizzatori_Controller(conferenza,subscene,this);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    public void editSessioniButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaSessioni.fxml"));
        ModificaSessioni_Controller controller = new ModificaSessioni_Controller(conferenza,subscene,this);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    public void editSponsorshipOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaSponsorizzazioni.fxml"));
        ModificaSponsorizzazioni_Controller controller = new ModificaSponsorizzazioni_Controller(conferenza,subscene,this);
        loader.setController(controller);
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

    public void setConferenza(Conferenza c) {
        this.conferenza = c;
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

    public void setGestioneConferenzeController(ModificaConferenze_Controller modificaConferenzeController) {
        this.modificaConferenzeController = modificaConferenzeController;
    }

    public void setOrganizzatori() {
        entiTable.setEditable(false);
        nomeEnte.setCellValueFactory(new PropertyValueFactory<>("nome"));
        siglaEnte.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        entiTable.getItems().addAll(conferenza.getEnti());
    }

    public void setSponsorizzazioni() {
        sponsorColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("sponsor"));
        contributoColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, Float>("contributo"));
        valutaColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("valuta"));
        sponsorTable.getItems().addAll(conferenza.getSponsorizzazioni());
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setTitleLabel() {
        if (conferenza != null)
            titleLabel.setText("Modifica della conferenza: " + conferenza.getTitolo());
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    protected void setTable() {
        table.setEditable(false);
        try {
            conferenza.loadSessioni();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        nomeSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione, String>("titolo"));
        inizioSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione, Date>("inizio"));
        fineSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione, Date>("fine"));
        salaSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione, String>("locazione"));
        table.getItems().addAll(conferenza.getSessioni());
        nomeSessioneColumn.isSortable();
    }

    @FXML
    void SlittaConferenzaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/SlittaConferenza.fxml"));
        SlittaConferenza_Controller controller = new SlittaConferenza_Controller(conferenza,this);
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
