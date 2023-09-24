package Controller.Edit;

import Interfaces.FormChecker;
import Exceptions.BlankFieldException;
import Exceptions.SediNonDisponibiliException;
import Model.DAO.ProgrammaDao;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Programma;
import Model.Entities.Conferenze.Sala;
import Model.Entities.Conferenze.Sessione;
import Model.Entities.Organizzazione.Organizzatore;
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
import java.util.ResourceBundle;

public class AddSessione_Controller implements Initializable, FormChecker {
    private Conferenza conferenza;
    @FXML
    private ImageView inizioSessioneAboutImage;
    @FXML
    private ImageView fineSessioneAboutImage;
    @FXML
    private ChoiceBox<Organizzatore> coordinatoreChoiceBox;
    @FXML
    private DateTimePicker fineDateTimePicker;
    @FXML
    private DateTimePicker inizioDateTimePicker;
    private ModificaSessioni_Controller modificaSessioniController;
    private ModificaConferenza_Controller modificaConferenzaController;
    @FXML
    private TextField nomeTF;
    private Sale sale;
    @FXML
    private ChoiceBox<Sala> saleChoice;
    private SubScene subscene;

    public AddSessione_Controller(Conferenza conferenza, SubScene subscene, ModificaSessioni_Controller modificaSessioniController) {
        this.conferenza = conferenza;
        this.subscene = subscene;
        this.modificaSessioniController = modificaSessioniController;
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (nomeTF.getText().isBlank() || saleChoice.getValue() == null
                || inizioDateTimePicker.getDateTimeValue() == null
                || fineDateTimePicker.getDateTimeValue() == null)
            throw new BlankFieldException();
    }

    public ModificaSessioni_Controller getManageSessioniController() {
        return modificaSessioniController;
    }

    public void setManageSessioniController(ModificaSessioni_Controller modificaSessioniController) {
        this.modificaSessioniController = modificaSessioniController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCoordinatoreChoiceBox();
            Tooltip.install(inizioSessioneAboutImage, new Tooltip("L'inizio della conferenza è: " + conferenza.getInizio()));
            Tooltip.install(fineSessioneAboutImage, new Tooltip("La fine della conferenza è: " + conferenza.getFine()));
        } catch (SQLException e) {
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
        ModificaProgrammaSessione_Controller controller = new ModificaProgrammaSessione_Controller(s,subscene,modificaSessioniController);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private Programma retrieveProgramma(Sessione s) throws SQLException {
        ProgrammaDao dao = new ProgrammaDao();
        return dao.retrieveProgrammaBySessione(s);
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

    @FXML
    void avantiButtonOnAction(ActionEvent event) throws IOException {
        try {
            checkFieldsAreBlank();
            Sessione s = setSessione();
            goToAddProgrammaWindow(s);
        } catch (BlankFieldException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Compilare prima tutti i campi");
            alert.showAndWait();
        } catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void showSale(MouseEvent event) {
        try {
            Timestamp inizio = Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue());
            Timestamp fine = Timestamp.valueOf(fineDateTimePicker.getDateTimeValue());
            sale.loadSaleDisponibili(inizio, fine);
            if (sale.getSale().isEmpty())
                throw new SediNonDisponibiliException();
            else
                saleChoice.setItems(sale.getSale());
        } catch (SediNonDisponibiliException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inserire delle date per visualizzare le sale libere");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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
