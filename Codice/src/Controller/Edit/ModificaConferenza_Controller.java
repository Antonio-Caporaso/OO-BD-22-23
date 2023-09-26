package Controller.Edit;

import Model.DAO.ConferenzaDao;
import Model.Entities.*;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificaConferenza_Controller implements Initializable {
    private Conferenza conferenza;
    @FXML
    private TableColumn<Sponsorizzazione, Float> contributoColumn;
    @FXML
    private Label dataFineLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private TextArea descrizioneArea;
    @FXML
    private TableView<Ente> entiTable;
    @FXML
    private TableColumn<Sessione, Date> fineSessioneColumn;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private TableColumn<Sessione, Date> inizioSessioneColumn;
    private ModificaConferenze_Controller modificaConferenzeController;
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
        goBackToEditConferencesWindow();
    }

    @FXML
    public void editDetailsOnAction(ActionEvent event) throws IOException {
        goToEditDetailsWindow();
    }

    @FXML
    public void editEntiOnAction(ActionEvent event) throws IOException {
        goToEditEntiWindow();
    }

    @FXML
    public void editSessioniButtonOnAction(ActionEvent event) throws IOException {
        goToEditSessionsWindow();
    }

    @FXML
    public void editSponsorshipOnAction(ActionEvent event) throws IOException {
        goToEditSponsorshipsWindow();
    }

    public SubScene getsubscene() {
        return subscene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDetails();
        setOrganizzatori();
        setSponsorizzazioni();
        setSessionsTable();
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

    private void goBackToEditConferencesWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenze.fxml"));
        ModificaConferenze_Controller controller = new ModificaConferenze_Controller(user, subscene);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void goToEditDetailsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaDettagliConferenza.fxml"));
        ModificaDettagliConferenza_Controller controller = new ModificaDettagliConferenza_Controller(conferenza, subscene, this);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void goToEditEntiWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaEntiOrganizzatori.fxml"));
        ModificaEntiOrganizzatori_Controller controller = new ModificaEntiOrganizzatori_Controller(conferenza, subscene, this);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void goToEditSessionsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaSessioni.fxml"));
        ModificaSessioni_Controller controller = new ModificaSessioni_Controller(conferenza, subscene, this);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void goToEditSponsorshipsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaSponsorizzazioni.fxml"));
        ModificaSponsorizzazioni_Controller controller = new ModificaSponsorizzazioni_Controller(conferenza, subscene, this);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private Optional<ButtonType> showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Eliminazione della conferenza");
        alert.setContentText("Sicuro di voler eliminare la conferenza?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    protected void setSessionsTable() {
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

    protected void reloadConferenza() throws SQLException {
        ConferenzaDao dao = new ConferenzaDao();
        conferenza = dao.retrieveConferenzaByID(conferenza.getId_conferenza());
        setDetails();
        setSessionsTable();
    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) {
        Optional<ButtonType> result = showConfirmationDialog();
        if (result.get() == ButtonType.OK) {
            ConferenzaDao conferenzaDao = new ConferenzaDao();
            try {
                conferenzaDao.deleteConferenza(conferenza);
                goBackToEditConferencesWindow();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
