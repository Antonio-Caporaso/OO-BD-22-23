package View.Controller;

import Persistence.DAO.SessioneDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.organizzazione.Organizzatore;
import Services.MembriComitato;
import Services.Sale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import jfxtras.scene.control.LocalTimeTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;

public class DettagliSessioneController_Edit implements Initializable {
    private Conferenza conferenza;
    private Sessione sessione;
    private SubScene subscene;
    private Sale sale;
    private SessioneController_Edit sessioneControllerEdit;
    @FXML
    private Label inizioConferenzaLabel;
    @FXML
    private Label fineConferenzaLabel;
    @FXML
    private Button annullaButton;
    @FXML
    private Button confermaButton;
    @FXML
    private TextField nomeTF;
    @FXML
    private ChoiceBox<Sala> saleChoice;
    @FXML
    private DatePicker fineDatePicker;
    @FXML
    private DatePicker inizioDatePicker;
    @FXML
    private LocalTimeTextField oraFineTimePicker;
    @FXML
    private LocalTimeTextField oraInizioTimePicker;
    @FXML
    private ChoiceBox<Organizzatore> coordinatoreChoiceBox;
    private MembriComitato membriComitatoScientifico;

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditSessione.fxml"));
        loader.setController(sessioneControllerEdit);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void confermaOnAction(ActionEvent event) throws SQLException, IOException {
        Optional<ButtonType> result = confirmationAlert();
        if(result.get() == ButtonType.OK) {
            updateSessione();
        }
        goToEditWindow();
    }

    private Optional<ButtonType> confirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler modificare i dettagli della sessione "+sessione.getSessioneID()+"?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    private void goToEditWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditSessione.fxml"));
        loader.setController(sessioneControllerEdit);
        sessioneControllerEdit.setSessione(sessione);
        sessioneControllerEdit.setDettagliSessione();
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void updateSessione() {
        SessioneDao dao = new SessioneDao();
        Sessione s = new Sessione();
        s.setSessioneID(sessione.getSessioneID());
        s.setTitolo(nomeTF.getText());
        s.setLocazione(saleChoice.getValue());
        s.setDataInizio(Date.valueOf(inizioDatePicker.getValue()));
        s.setDataFine(Date.valueOf(fineDatePicker.getValue()));
        s.setOrarioInizio(Time.valueOf(oraInizioTimePicker.localTimeProperty().getValue()));
        s.setOrarioFine(Time.valueOf(oraFineTimePicker.localTimeProperty().getValue()));
        s.setCoordinatore(coordinatoreChoiceBox.getValue());
        try {
            dao.updateSessione(s);
            sessione = s;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage()+"\n"+"Data inizio conferenza:"+conferenza.getDataInizio()+"\n"+"Data fine conferenza:"+conferenza.getDataFine());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inizioConferenzaLabel.setText(conferenza.getDataInizio().toString());
        fineConferenzaLabel.setText(conferenza.getDataFine().toString());
        try {
            setTitoloSessione();
            setSale();
            setDate();
            setMembriComitatoScientifico();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTitoloSessione() {
        nomeTF.setText(sessione.getTitolo());
    }

    private void setMembriComitatoScientifico() throws SQLException {
        membriComitatoScientifico = new MembriComitato(conferenza);
        membriComitatoScientifico.loadMembriComitatoScientifico();
        coordinatoreChoiceBox.setItems(membriComitatoScientifico.getMembriComitatoScientifico());
        coordinatoreChoiceBox.setValue(sessione.getCoordinatore());
    }

    private void setDate() throws SQLException {
        inizioDatePicker.setValue(sessione.getDataInizio().toLocalDate());
        fineDatePicker.setValue(sessione.getDataFine().toLocalDate());
        oraInizioTimePicker.setLocalTime(sessione.getOrarioInizio().toLocalTime());
        oraFineTimePicker.setLocalTime(sessione.getOrarioFine().toLocalTime());
    }

    private void setSale() throws SQLException {
        sale = new Sale(sessione.getConferenza().getSede());
        sale.loadSale();
        saleChoice.setItems(sale.getSale());
        saleChoice.setValue(sessione.getLocazione());
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

    public SessioneController_Edit getEditSessioneController() {
        return sessioneControllerEdit;
    }

    public void setEditSessioneController(SessioneController_Edit sessioneControllerEdit) {
        this.sessioneControllerEdit = sessioneControllerEdit;
    }
}
