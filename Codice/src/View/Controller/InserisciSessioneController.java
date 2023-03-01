package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sala;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class InserisciSessioneController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private DatePicker dataFineDatePicker;

    @FXML
    private DatePicker dataInizioDatePicker;

    @FXML
    private TextArea descrizioneTextArea;

    @FXML
    private Button creaButton;

    @FXML
    private ChoiceBox<String> orarioFineChoiceBox;

    @FXML
    private ChoiceBox<Date> orarioInizioChoiceBox;

    @FXML
    private ChoiceBox<Sala> salaChoiceBox;

    @FXML
    private TextField titoloSessioneTextField;

    @FXML
    void backButtonOnAction(ActionEvent event) {

    }

    @FXML
    void creaButtonOnAction(ActionEvent event) {

    }

    private Conferenza conferenza;
    private SubScene subscene;


    public void setConferenza(Conferenza conferenza){
        this.conferenza=conferenza;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
