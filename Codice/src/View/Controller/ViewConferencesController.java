package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sede;
import Services.Conferenze;
import Services.Sedi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewConferencesController implements Initializable {
    private Conferenze conferenze = new Conferenze();
    private Sedi sedi = new Sedi();
    @FXML
    private Button cercaPerDataButton;

    @FXML
    private Button cercaPerSedeButton;

    @FXML
    private ListView<Conferenza> conferenceView;

    @FXML
    private DatePicker dataFineDP;

    @FXML
    private DatePicker dataInizioDP;

    @FXML
    private ChoiceBox<Sede> sedeChoice;

    @FXML
    private Button viewDetailsButton;

    @FXML
    private Button viewEntiButton;

    @FXML
    private Button viewSessionButton;

    @FXML
    private Button viewSponsorsButton;

    @FXML
    void cercaPerDataOnAction(ActionEvent event) throws SQLException {
        Date dataInizio = Date.valueOf(dataInizioDP.getValue());
        Date dataFine = Date.valueOf(dataFineDP.getValue());
        conferenze.loadByDateInterval(dataInizio,dataFine);
        conferenceView.setItems(conferenze.getConferenze());
    }

    @FXML
    void cercaPerSedeOnAction(ActionEvent event) throws SQLException {
        Sede sede = sedeChoice.getSelectionModel().getSelectedItem();
        conferenze.loadBySede(sede);
        conferenceView.setItems(conferenze.getConferenze());
    }

    @FXML
    void viewDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void viewEntiOnAction(ActionEvent event) {

    }

    @FXML
    void viewSessioniOnAction(ActionEvent event) {

    }

    @FXML
    void viewSponsorsOnAction(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sedi.loadSedi();
        sedeChoice.setItems(sedi.getSedi());
    }
}
