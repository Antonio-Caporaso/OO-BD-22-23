package View.Controller;

import Persistence.DAO.SessioneDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.organizzazione.Organizzatore;
import Utilities.MembriComitato;
import Utilities.Sale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificaDettagliSessioneController implements Initializable {
    @FXML
    private Button annullaButton;
    private Conferenza conferenza;
    @FXML
    private Button confermaButton;
    @FXML
    private ChoiceBox<Organizzatore> coordinatoreChoiceBox;
    @FXML
    private Label fineConferenzaLabel;
    @FXML
    private DateTimePicker fineDateTimePicker;
    @FXML
    private Label inizioConferenzaLabel;
    @FXML
    private DateTimePicker inizioDateTimePicker;
    private MembriComitato membriComitatoScientifico;
    private ModificaSessioneController modificaSessioneController;
    @FXML
    private TextField nomeTF;
    private Sale sale;
    @FXML
    private ChoiceBox<Sala> saleChoice;
    private Sessione sessione;
    private SubScene subscene;

    public ModificaSessioneController getEditSessioneController() {
        return modificaSessioneController;
    }

    public void setEditSessioneController(ModificaSessioneController modificaSessioneController) {
        this.modificaSessioneController = modificaSessioneController;
    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    public SubScene getSubscene() {
        return subscene;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inizioConferenzaLabel.setText(conferenza.getInizio().toString());
        fineConferenzaLabel.setText(conferenza.getFine().toString());
        saleChoice.setValue(sessione.getLocazione());
        try {
            setTitoloSessione();
            setDate();
            setMembriComitatoScientifico();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    private Optional<ButtonType> confirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler modificare i dettagli della sessione " + sessione.getId_sessione() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    private void goToEditWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessione.fxml"));
        loader.setController(modificaSessioneController);
        modificaSessioneController.setSessione(sessione);
        modificaSessioneController.setDettagliSessione();
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void setDate() throws SQLException {
        inizioDateTimePicker.setDateTimeValue(sessione.getInizio().toLocalDateTime());
        fineDateTimePicker.setDateTimeValue(sessione.getFine().toLocalDateTime());
    }

    private void setMembriComitatoScientifico() throws SQLException {
        membriComitatoScientifico = new MembriComitato(conferenza);
        membriComitatoScientifico.loadMembriComitatoScientifico();
        coordinatoreChoiceBox.setItems(membriComitatoScientifico.getMembriComitatoScientifico());
        coordinatoreChoiceBox.setValue(sessione.getCoordinatore());
    }

    private void setTitoloSessione() {
        nomeTF.setText(sessione.getTitolo());
    }

    private void updateSessione() {
        SessioneDao dao = new SessioneDao();
        Sessione s = new Sessione();
        s.setId_sessione(sessione.getId_sessione());
        s.setTitolo(nomeTF.getText());
        s.setLocazione(saleChoice.getValue());
        s.setInizio(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()));
        s.setFine(Timestamp.valueOf(fineDateTimePicker.getDateTimeValue()));
        s.setCoordinatore(coordinatoreChoiceBox.getValue());
        try {
            dao.updateSessione(s);
            sessione = s;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage() + "\n" + "Data inizio conferenza:" + conferenza.getInizio() + "\n" + "Data fine conferenza:" + conferenza.getFine());
            alert.showAndWait();
        }
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessione.fxml"));
        loader.setController(modificaSessioneController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void confermaOnAction(ActionEvent event) throws SQLException, IOException {
        Optional<ButtonType> result = confirmationAlert();
        if (result.get() == ButtonType.OK) {
            updateSessione();
        }
        goToEditWindow();
    }

    @FXML
    void showSaleDisponibili(MouseEvent event) {
        try {
            sale = new Sale(sessione.getConferenza().getSede());
            sale.loadSaleDisponibili(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()), Timestamp.valueOf(fineDateTimePicker.getDateTimeValue()));
            saleChoice.setItems(sale.getSale());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
