package View.Controller;

import Persistence.DAO.SessioneDao;
import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.Conferenze.Sessione;
import Services.Sale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import jfxtras.scene.control.CalendarTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditDettagliSessioneController implements Initializable {
    private Sessione sessione;
    private SubScene subscene;
    private Sale sale;
    private EditSessioneController controller;
    @FXML
    private Button annullaButton;

    @FXML
    private Button confermaButton;

    @FXML
    private DatePicker dataFineDP;

    @FXML
    private DatePicker dataInizioDP;
    @FXML
    private CalendarTimePicker oraFIne;

    @FXML
    private CalendarTimePicker oraInizio;

    @FXML
    private Spinner<String> minutiOraInizioSpinner;

    @FXML
    private Spinner<String> minutiOraSpinner;

    @FXML
    private TextField nomeTF;

    @FXML
    private Spinner<String> oraFineSpinner;

    @FXML
    private Spinner<String> oraInizioSpinner;

    @FXML
    private ChoiceBox<Sala> saleChoice;
//    @FXML
//    private JFXTimePicker oraInizioTP;
//    @FXML
//    private JFXTimePicker oraFineTP;

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditSessioneController.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void confermaOnAction(ActionEvent event) throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler modificare i dettagli della sessione "+sessione.getSessioneID());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            SessioneDao dao = new SessioneDao();
            sessione.setTitolo(nomeTF.getText());
            sessione.setLocazione(saleChoice.getValue());
            sessione.setDataInizio(Date.valueOf(dataInizioDP.getValue()));
            sessione.setDataFine(Date.valueOf(dataFineDP.getValue()));
            // Per orario di inizio
            // Per orario di fine
            dao.updateSessione(sessione);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditSessione.fxml"));
        loader.setController(controller);
        controller.setSessione(sessione);
        controller.setDetails();
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            sale = new Sale(sessione.getConferenza().getSede());
            nomeTF.setText(sessione.getTitolo());
            sale.loadSale();
            saleChoice.setItems(sale.getSale());
            saleChoice.setValue(sessione.getLocazione());
            dataInizioDP.setValue(sessione.getDataInizio().toLocalDate());
            dataFineDP.setValue(sessione.getDataFine().toLocalDate());
//        oraInizioTP.setValue(sessione.getOrarioInizio().toLocalTime());
//        oraFineTP.setValue(sessione.getOrarioFine().toLocalTime());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public EditSessioneController getController() {
        return controller;
    }

    public void setController(EditSessioneController controller) {
        this.controller = controller;
    }
}
