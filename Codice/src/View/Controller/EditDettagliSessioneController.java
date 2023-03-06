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
import tornadofx.control.DateTimePicker;

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
    private TextField nomeTF;
    @FXML
    private ChoiceBox<Sala> saleChoice;
    @FXML
    private DateTimePicker fineDateTimePicker;
    @FXML
    private DateTimePicker inizioDateTimePicker;

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
            inizioDateTimePicker.setDateTimeValue(sessione.getDataInizio().toLocalDate().atTime(sessione.getOrarioInizio().toLocalTime()));
            fineDateTimePicker.setDateTimeValue(sessione.getDataFine().toLocalDate().atTime(sessione.getOrarioFine().toLocalTime()));
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
