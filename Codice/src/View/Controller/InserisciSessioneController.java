package View.Controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

import Persistence.Entities.organizzazione.Organizzatore;
import Services.MembriComitato;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import tornadofx.control.DateTimePicker;

import Exceptions.BlankFieldException;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Services.Sale;
import Services.Sessioni;

public class InserisciSessioneController implements Initializable,FormChecker {
    @FXML
    private Button backButton;
    @FXML
    private Button inserisciButton;
    @FXML
    private ChoiceBox<Sala> saleChoiceBox;
    @FXML
    private TextField titoloSessioneTextField;
    @FXML
    private DateTimePicker inizioDateTimePicker;
    @FXML
    private ChoiceBox<Organizzatore> coordinatoreChoiceBox;
    @FXML
    private DateTimePicker fineDateTimePicker;
    private Conferenza conferenza;
    private SubScene subscene;
    private Utente user;
    private Sale sale;
    private Sessioni sessioni;

    //Public Setters
    public void setConferenza(Conferenza conferenza){
        this.conferenza=conferenza;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setUtente(Utente utente){
        this.user=utente;
    }
    //Button methods
    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadViewSessioni(conferenza);
    }
    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        try {
            checkFieldsAreBlank();
            sessioni = new Sessioni(conferenza);
            Sessione s = setSessione();
            sessioni.addSessione(s);
            openAddSessioneDialogWindow();
            loadViewSessioni(conferenza);
        }catch (BlankFieldException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //private Methods
    private Sessione setSessione() {
        Sessione s = new Sessione();
        s.setConferenza(conferenza);
        s.setTitolo(titoloSessioneTextField .getText());
        s.setLocazione(saleChoiceBox.getValue());
        s.setCoordinatore(coordinatoreChoiceBox.getValue());
        s.setDataInizio(Date.valueOf(inizioDateTimePicker.getDateTimeValue().toLocalDate()));
        s.setDataFine(Date.valueOf(fineDateTimePicker.getDateTimeValue().toLocalDate()));
        s.setOrarioInizio(Time.valueOf(inizioDateTimePicker.getDateTimeValue().toLocalTime()));
        s.setOrarioFine(Time.valueOf(fineDateTimePicker.getDateTimeValue().toLocalTime()));
        return  s;
    }
    private void loadViewSessioni(Conferenza c){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaSessione.fxml"));
            VisualizzaSessioneController controller = new VisualizzaSessioneController();
            controller.setSubscene(subscene);
            loader.setController(controller);
            controller.setConferenza(c);
            controller.setUser(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void openAddSessioneDialogWindow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Successo!");
        alert.setHeaderText("Sessione aggiunta correttamente");
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.showAndWait();
    }
    private void loadSaleChoiceBox(){
        try{
            sale = new Sale(conferenza.getSede());
            sale.loadSale();
            saleChoiceBox.setItems(sale.getSale());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Overrides
    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if(titoloSessioneTextField.getText().isBlank() || inizioDateTimePicker.getValue().equals(null) ||
                fineDateTimePicker.getValue().equals(null) || saleChoiceBox.getValue().equals(null))
            throw new BlankFieldException();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            loadSaleChoiceBox();
        try {
            loadCoordinatoreChoiceBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCoordinatoreChoiceBox() throws SQLException {
        MembriComitato membriComitatoScientifico = new MembriComitato(conferenza);
        membriComitatoScientifico.loadMembriComitatoScientifico();
        coordinatoreChoiceBox.setItems(membriComitatoScientifico.getMembriComitatoScientifico());
    }
}
