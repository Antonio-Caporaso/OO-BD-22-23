package Controller.Edit;

import Controller.AlertWindowController;
import Exceptions.BlankFieldException;
import Exceptions.DateMismatchException;
import Exceptions.SediNonDisponibiliException;
import Exceptions.SessionePresenteException;
import Interfaces.FormChecker;
import Model.DAO.ProgrammaDao;
import Model.Entities.*;
import Model.Utilities.Sale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddSessione_Controller extends AlertWindowController implements Initializable, FormChecker {
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
    private ModificaSessioni_Controller modificaSessioniController;
    @FXML
    private TextField nomeTF;
    private Sale sale;
    @FXML
    private ChoiceBox<Sala> saleChoice;
    private SubScene subscene;
    private Programma programma;

    public AddSessione_Controller(Conferenza conferenza, SubScene subscene, ModificaSessioni_Controller modificaSessioniController) {
        this.conferenza = conferenza;
        this.subscene = subscene;
        this.modificaSessioniController = modificaSessioniController;
        sale = new Sale(conferenza.getSede());
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (nomeTF.getText().isBlank() || saleChoice.getValue() == null
                || inizioDateTimePicker.getDateTimeValue() == null
                || fineDateTimePicker.getDateTimeValue() == null
                || coordinatoreChoiceBox.getValue()==null)
            throw new BlankFieldException();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCoordinatoreChoiceBox();
            Tooltip.install(inizioSessioneAboutImage, new Tooltip("L'inizio della conferenza è: " + conferenza.getInizio()));
            Tooltip.install(fineSessioneAboutImage, new Tooltip("La fine della conferenza è: " + conferenza.getFine()));
            loadSale();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadSale() {
        try {
            sale.loadSale();
            saleChoice.setItems(sale.getSale());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    private void goToAddProgrammaWindow(Sessione s) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaProgrammaSessione.fxml"));
        ModificaProgrammaSessione_Controller controller = new ModificaProgrammaSessione_Controller(s, subscene, modificaSessioniController);
        loader.setController(controller);
        controller.setProgramma(programma);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void setCoordinatoreChoiceBox() throws SQLException {
        conferenza.getComitato_s().loadMembri();
        coordinatoreChoiceBox.setItems(conferenza.getComitato_s().getMembri());
    }

    private Sessione setSessione() {
        Sessione s = new Sessione();
        s.setConferenza(conferenza);
        s.setTitolo(nomeTF.getText());
        s.setLocazione(saleChoice.getValue());
        s.setCoordinatore(coordinatoreChoiceBox.getValue());
        s.setInizio(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()));
        s.setFine(Timestamp.valueOf(fineDateTimePicker.getDateTimeValue()));
        return s;
    }
    private void salvaSessione(Sessione sessione){
        try {
            conferenza.addSessione(sessione);
        } catch (DateMismatchException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (SessionePresenteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Errore in fase di salvataggio");
            alert.setContentText(e.getSQLState() + ": " + e.getMessage());
            alert.showAndWait();
        }
    }
    private void retrieveProgrammaSessione(Sessione sessione) {
        ProgrammaDao programmaDao = new ProgrammaDao();
        try {
            programma = programmaDao.retrieveProgrammaBySessione(sessione);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void avantiButtonOnAction(ActionEvent event) throws IOException {
        try {
            checkFieldsAreBlank();
            Sessione s = setSessione();
            salvaSessione(s);
            retrieveProgrammaSessione(s);
            goToAddProgrammaWindow(s);
        } catch (BlankFieldException e) {
            showAlertWindow(Alert.AlertType.WARNING,"Attenzione","Compila tutti i campi");
        } catch (SQLException exception) {
            showAlertWindow(Alert.AlertType.ERROR,"Errore",exception.getMessage());
        }
    }


    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaSessioni.fxml"));
        loader.setController(modificaSessioniController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
}
