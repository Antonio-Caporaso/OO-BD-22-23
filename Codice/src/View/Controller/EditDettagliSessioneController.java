package View.Controller;

import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.Conferenze.Sessione;
import Services.Sale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
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

    @FXML
    void annullaOnAction(ActionEvent event) {

    }

    @FXML
    void confermaOnAction(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sale = new Sale(sessione.getConferenza().getSede());
        nomeTF.setText(sessione.getTitolo());
        try{
            sale.loadSale();
            saleChoice.setItems(sale.getSale());
            saleChoice.setValue(sessione.getLocazione());
        }catch (SQLException e){
            e.printStackTrace();
        }
        dataInizioDP.setValue(sessione.getDataInizio().toLocalDate());
        dataFineDP.setValue(sessione.getDataFine().toLocalDate());
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
