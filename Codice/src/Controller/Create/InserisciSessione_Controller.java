package Controller.Create;

import Controller.AlertWindowController;
import Exceptions.BlankFieldException;
import Exceptions.DateMismatchException;
import Exceptions.SediNonDisponibiliException;
import Exceptions.SessionePresenteException;
import Interfaces.FormChecker;
import Model.Entities.Conferenza;
import Model.Entities.Sala;
import Model.Entities.Sessione;
import Model.Entities.Organizzatore;
import Model.Entities.Utente;
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

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class InserisciSessione_Controller extends AlertWindowController implements Initializable, FormChecker {
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
    private Sale sale;
    @FXML
    private ChoiceBox<Sala> saleChoiceBox;
    private SubScene subscene;
    @FXML
    private TextField titoloSessioneTextField;
    private Utente user;

    public InserisciSessione_Controller(SubScene subscene, Conferenza conferenza, Utente user){
        this.subscene = subscene;
        this.conferenza = conferenza;
        this.user = user;
    }
    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (titoloSessioneTextField.getText().isBlank() || inizioDateTimePicker.getValue() == null ||
                fineDateTimePicker.getValue() == null || saleChoiceBox.getValue() == null || coordinatoreChoiceBox.getValue()==null)
            throw new BlankFieldException();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadSale();
            loadCoordinatoreChoiceBox();
            Tooltip.install(inizioSessioneAboutImage, new Tooltip("L'inizio della conferenza è: " + conferenza.getInizio()));
            Tooltip.install(fineSessioneAboutImage, new Tooltip("La fine della conferenza è: " + conferenza.getFine()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCoordinatoreChoiceBox() throws SQLException {
        conferenza.getComitato_s().loadMembri();
        coordinatoreChoiceBox.setItems(conferenza.getComitato_s().getMembri());
    }

    private void loadViewSessioni(Conferenza c) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/VisualizzaSessioniConferenza.fxml"));
            VisualizzaSessioniConferenza_Controller controller = new VisualizzaSessioniConferenza_Controller(subscene, c, user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Sessione setSessione() {
        Sessione s = new Sessione();
        s.setConferenza(conferenza);
        s.setTitolo(titoloSessioneTextField.getText());
        s.setLocazione(saleChoiceBox.getValue());
        s.setCoordinatore(coordinatoreChoiceBox.getValue());
        s.setInizio(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()));
        s.setFine(Timestamp.valueOf(fineDateTimePicker.getDateTimeValue()));
        return s;
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadViewSessioni(conferenza);
    }

    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        try {
            checkFieldsAreBlank();
            Sessione s = setSessione();
            conferenza.addSessione(s);
            showAlertWindow(Alert.AlertType.INFORMATION,"Successo","Sessione inserita correttamente");
            loadViewSessioni(conferenza);
        } catch (DateMismatchException | SQLException | SessionePresenteException | BlankFieldException e) {
            showAlertWindow(Alert.AlertType.ERROR,"Errore",e.getMessage());
        }
    }
    private void loadSale() {
        try {
            sale=new Sale(conferenza.getSede());
            sale.loadSale();
            saleChoiceBox.setItems(sale.getSale());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
