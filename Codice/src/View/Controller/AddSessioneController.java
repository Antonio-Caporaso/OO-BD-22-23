package View.Controller;

import Exceptions.BlankFieldException;
import Exceptions.SediNonDisponibiliException;
import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Programma;
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
import javafx.scene.paint.Color;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddSessioneController implements Initializable, FormChecker {
    @FXML
    private Button annullaButton;
    @FXML
    private Button avantiButton;
    private Conferenza conferenza;
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
    private ModificaSessioniController modificaSessioniController;
    private ModificaConferenzaController modificaConferenzaController;
    @FXML
    private TextField nomeTF;
    private Sale sale;
    @FXML
    private ChoiceBox<Sala> saleChoice;
    private SubScene subscene;

    public AddSessioneController(Conferenza conferenza, SubScene subscene, ModificaSessioniController modificaSessioniController) {
        this.conferenza = conferenza;
        this.subscene = subscene;
        this.modificaSessioniController = modificaSessioniController;
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (nomeTF.getText().isBlank() || saleChoice.getValue().equals(null)
                || inizioDateTimePicker.getDateTimeValue().equals(null)
                || fineDateTimePicker.getDateTimeValue().equals(null))
            throw new BlankFieldException();
    }

    public ModificaSessioniController getManageSessioniController() {
        return modificaSessioniController;
    }

    public void setManageSessioniController(ModificaSessioniController modificaSessioniController) {
        this.modificaSessioniController = modificaSessioniController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inizioConferenzaLabel.setText(conferenza.getInizio().toString());
        inizioConferenzaLabel.setTextFill(Color.WHITE);
        fineConferenzaLabel.setTextFill(Color.WHITE);
        fineConferenzaLabel.setText(conferenza.getFine().toString());
        try {
            setCoordinatoreChoiceBox();
        } catch (SQLException e) {
        }
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    private void goToAddProgrammaWindow(Sessione s) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaProgrammaSessione.fxml"));
        ModificaProgrammaSessioneController controller = new ModificaProgrammaSessioneController(s,subscene,modificaSessioniController);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private Programma retrieveProgramma(Sessione s) throws SQLException {
        ProgrammaDao dao = new ProgrammaDao();
        return dao.retrieveProgrammaBySessione(s);
    }

    private void setCoordinatoreChoiceBox() throws SQLException {
        MembriComitato membriComitatoScientifico = new MembriComitato(conferenza);
        membriComitatoScientifico.loadMembriComitatoScientifico();
        coordinatoreChoiceBox.setItems(membriComitatoScientifico.getMembriComitatoScientifico());
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessioni.fxml"));
        loader.setController(modificaSessioniController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
}
