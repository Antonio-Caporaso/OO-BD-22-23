package Controller.Edit;

import Model.DAO.SessioneDao;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Sala;
import Model.Entities.Conferenze.Sessione;
import Model.Entities.organizzazione.Organizzatore;
import Model.Utilities.MembriComitato;
import Model.Utilities.Sale;
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

public class ModificaDettagliSessione_Controller implements Initializable {
    @FXML
    private Button annullaButton;
    private Conferenza conferenza;
    @FXML
    private Button confermaButton;
    @FXML
    private ChoiceBox<Organizzatore> coordinatoreChoiceBox;
    @FXML
    private DateTimePicker fineDateTimePicker;
    @FXML
    private Tooltip toolTipFineConferenza;

    @FXML
    private Tooltip toolTipInizioConferenza;

    @FXML
    private DateTimePicker inizioDateTimePicker;
    private MembriComitato membriComitatoScientifico;
    private ModificaSessione_Controller modificaSessioneController;
    @FXML
    private TextField nomeTF;
    private Sale sale;
    @FXML
    private ChoiceBox<Sala> saleChoice;
    private Sessione sessione;
    private SubScene subscene;

    public ModificaDettagliSessione_Controller(Sessione sessione, Conferenza conferenza, SubScene subScene, ModificaSessione_Controller modificaSessioneController) {
        this.sessione = sessione;
        this.conferenza = conferenza;
        this.subscene = subScene;
        this.modificaSessioneController = modificaSessioneController;
    }

    public ModificaSessione_Controller getEditSessioneController() {
        return modificaSessioneController;
    }

    public void setEditSessioneController(ModificaSessione_Controller modificaSessioneController) {
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
        toolTipInizioConferenza.setText("Inizio conferenza: " +conferenza.getInizio().toString());
        toolTipFineConferenza.setText("Fine conferenza: "+conferenza.getFine().toString());
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Edit/ModificaSessione.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Edit/ModificaSessione.fxml"));
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
