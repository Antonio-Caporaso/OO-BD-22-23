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
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificaDettagliSessioneController implements Initializable {
    private Conferenza conferenza;
    private Sessione sessione;
    private SubScene subscene;
    private Sale sale;
    private EditSessioneController editSessioneController;
    @FXML
    private Button annullaButton;
    @FXML
    private Button confermaButton;
    @FXML
    private TextField nomeTF;
    @FXML
    private ChoiceBox<Sala> saleChoice;
    @FXML
    private DateTimePicker fineDateTimePicker;
    @FXML
    private DateTimePicker inizioDateTimePicker;
    @FXML
    private ChoiceBox<Organizzatore> coordinatoreChoiceBox;
    private MembriComitato membriComitatoScientifico;

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditSessione.fxml"));
        loader.setController(editSessioneController);
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
        loader.setController(editSessioneController);
        editSessioneController.setSessione(sessione);
        editSessioneController.setDettagliSessione();
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void updateSessione() throws SQLException {
        SessioneDao dao = new SessioneDao();
        sessione.setTitolo(nomeTF.getText());
        sessione.setLocazione(saleChoice.getValue());
        sessione.setDataInizio(Date.valueOf(inizioDateTimePicker.getDateTimeValue().toLocalDate()));
        sessione.setDataFine(Date.valueOf(fineDateTimePicker.getDateTimeValue().toLocalDate()));
        sessione.setOrarioFine(Time.valueOf(fineDateTimePicker.getDateTimeValue().toLocalTime()));
        sessione.setOrarioInizio(Time.valueOf(inizioDateTimePicker.getDateTimeValue().toLocalTime()));
        dao.updateSessione(sessione);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        inizioDateTimePicker.setDateTimeValue(sessione.getDataInizio().toLocalDate().atTime(sessione.getOrarioInizio().toLocalTime()));
        fineDateTimePicker.setDateTimeValue(sessione.getDataFine().toLocalDate().atTime(sessione.getOrarioFine().toLocalTime()));
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

    public EditSessioneController getEditSessioneController() {
        return editSessioneController;
    }

    public void setEditSessioneController(EditSessioneController editSessioneController) {
        this.editSessioneController = editSessioneController;
    }
}
