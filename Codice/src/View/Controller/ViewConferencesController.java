package View.Controller;
import Persistence.DTO.Conferenze.Conferenza;
import Persistence.DTO.Conferenze.Sede;
import Services.Conferenze;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewConferencesController implements Initializable {
    private Conferenze conferenze = new Conferenze();
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
    void cercaPerDataOnAction(ActionEvent event) {

    }

    @FXML
    void cercaPerSedeOnAction(ActionEvent event) {

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

    }
}
