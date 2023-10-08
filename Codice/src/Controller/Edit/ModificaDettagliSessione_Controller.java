package Controller.Edit;

import Controller.AlertWindowController;
import Exceptions.BlankFieldException;
import Interfaces.FormChecker;
import Model.DAO.SessioneDao;
import Model.Entities.Conferenza;
import Model.Entities.Organizzatore;
import Model.Entities.Sala;
import Model.Entities.Sessione;
import Model.Utilities.Sale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificaDettagliSessione_Controller extends AlertWindowController implements Initializable, FormChecker {
    private Conferenza conferenza;
    @FXML
    private ChoiceBox<Organizzatore> coordinatoreChoiceBox;
    @FXML
    private DateTimePicker fineDateTimePicker;
    @FXML
    private ImageView fineSessioneAboutImage;
    @FXML
    private DateTimePicker inizioDateTimePicker;
    @FXML
    private ImageView inizioSessioneAboutImage;
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

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (nomeTF.getText().isBlank()
                || inizioDateTimePicker.getDateTimeValue() == null
                || fineDateTimePicker.getDateTimeValue() == null
                || saleChoice.getValue() == null
                || coordinatoreChoiceBox.getValue() == null
        ) throw new BlankFieldException();
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
        Tooltip.install(inizioSessioneAboutImage, new Tooltip("L'inizio della conferenza è: " + conferenza.getInizio()));
        Tooltip.install(fineSessioneAboutImage, new Tooltip("La fine della conferenza è: " + conferenza.getFine()));
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

    private Sessione getDettagliSessione() {
        Sessione s = new Sessione();
        s.setId_sessione(sessione.getId_sessione());
        s.setTitolo(nomeTF.getText());
        s.setLocazione(saleChoice.getValue());
        s.setInizio(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()));
        s.setFine(Timestamp.valueOf(fineDateTimePicker.getDateTimeValue()));
        s.setCoordinatore(coordinatoreChoiceBox.getValue());
        return s;
    }

    private void goToEditWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaSessione.fxml"));
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
        conferenza.getComitato_s().loadMembri();
        coordinatoreChoiceBox.setItems(conferenza.getComitato_s().getMembri());
        coordinatoreChoiceBox.setValue(sessione.getCoordinatore());
    }

    private void setTitoloSessione() {
        nomeTF.setText(sessione.getTitolo());
    }

    private void updateSessione() {
        try {
            checkFieldsAreBlank();
            Sessione s = getDettagliSessione();
            SessioneDao dao = new SessioneDao();
            dao.updateSessione(s);
            sessione = s;
        } catch (BlankFieldException exception) {
           showAlertWindow(Alert.AlertType.WARNING,"Attenzione",exception.getMessage());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Errore in fase di aggiornamento");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaSessione.fxml"));
        loader.setController(modificaSessioneController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void confermaOnAction(ActionEvent event) throws SQLException, IOException {
        Optional<ButtonType> result = showConfirmationDialog("Sicuro di voler aggiornare la seguente sessione?");
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
            showAlertWindow(Alert.AlertType.ERROR,"Errore",e.getMessage());
        }
    }
}
