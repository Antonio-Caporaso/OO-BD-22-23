package View.Controller;

import Exceptions.BlankFieldException;
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
import javafx.scene.paint.Color;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddSessioneController implements Initializable,FormChecker {
    private Conferenza conferenza;
    private Sale sale;
    private ManageSessioniController manageSessioniController;
    private ModificaConferenzaController modificaConferenzaController;
    private SubScene subscene;
    @FXML
    private Label inizioConferenzaLabel;
    @FXML
    private Label fineConferenzaLabel;
    @FXML
    private Button annullaButton;
    @FXML
    private DateTimePicker fineDateTimePicker;
    @FXML
    private DateTimePicker inizioDateTimePicker;
    @FXML
    private Button avantiButton;
    @FXML
    private TextField nomeTF;
    @FXML
    private ChoiceBox<Sala> saleChoice;
    @FXML
    private ChoiceBox<Organizzatore> coordinatoreChoiceBox;

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if(nomeTF.getText().isBlank() || saleChoice.getValue().equals(null)
                || inizioDateTimePicker.getDateTimeValue().equals(null)
                || fineDateTimePicker.getDateTimeValue().equals(null))
            throw new BlankFieldException();
    }

    public ManageSessioniController getManageSessioniController() {
        return manageSessioniController;
    }

    public void setManageSessioniController(ManageSessioniController manageSessioniController) {
        this.manageSessioniController = manageSessioniController;
    }

    @FXML
    void avantiButtonOnAction(ActionEvent event) throws IOException {
        try{
            checkFieldsAreBlank();
            Sessione s = setSessione();
            goToAddProgrammaWindow(s);
        }catch (BlankFieldException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Compilare prima tutti i campi");
            alert.showAndWait();
        }catch (SQLException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    private void goToAddProgrammaWindow(Sessione s) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditProgramma.fxml"));
        EditProgrammaController controller = new EditProgrammaController();
        controller.setManageSessioniController(manageSessioniController);
        controller.setSessione(s);
        controller.setSubscene(subscene);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private Programma retrieveProgramma(Sessione s) throws SQLException {
        ProgrammaDao dao = new ProgrammaDao();
        return dao.retrieveProgrammaBySessione(s);
    }


    private Sessione setSessione() {
        Sessione s = new Sessione();
        s.setConferenza(conferenza);
        s.setTitolo(nomeTF.getText());
        s.setLocazione(saleChoice.getValue());
        s.setCoordinatore(coordinatoreChoiceBox.getValue());
        s.setInizio(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()));
        s.setFine(Timestamp.valueOf(fineDateTimePicker.getDateTimeValue()));

        return  s;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inizioConferenzaLabel.setText(conferenza.getInizio().toString());
        inizioConferenzaLabel.setTextFill(Color.WHITE);
        fineConferenzaLabel.setTextFill(Color.WHITE);
        fineConferenzaLabel.setText(conferenza.getFine().toString());
        setSale();
        try {
            setCoordinatoreChoiceBox();
        } catch (SQLException e) {
        }
    }

    private void setCoordinatoreChoiceBox() throws SQLException {
        MembriComitato membriComitatoScientifico = new MembriComitato(conferenza);
        membriComitatoScientifico.loadMembriComitatoScientifico();
        coordinatoreChoiceBox.setItems(membriComitatoScientifico.getMembriComitatoScientifico());
    }

    private void setSale() {
        try{
            sale = new Sale(conferenza.getSede());
            sale.loadSale();
            saleChoice.setItems(sale.getSale());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessioni.fxml"));
        loader.setController(manageSessioniController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
}
