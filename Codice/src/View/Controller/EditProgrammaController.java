package View.Controller;

import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.partecipanti.Speaker;
import Services.EventiSocialiSessione;
import Services.IntervalliSessione;
import Services.InterventiSessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProgrammaController implements Initializable {
    private Programma programma;
    private SubScene subscene;
    private ManageSessioniController manageSessioniController;
    private InterventiSessione interventi;
    private IntervalliSessione intervalli;
    private Sessione sessione;
    private EventiSocialiSessione eventi;
    @FXML
    private Button editChairButton;
    @FXML
    private Label coordinatoreLabel;
    @FXML
    private Button fineButton;
    @FXML
    private Label keynoteLabel;
    @FXML
    private Button addEventoButton;

    @FXML
    private Button addIntervalloButton;

    @FXML
    private Button addInterventoButton;

    @FXML
    private Button deleteEventoButton;

    @FXML
    private Button deleteIntervalloButton;

    @FXML
    private Button deleteInterventoButton;
    @FXML
    private Button editKeynoteButton;
    @FXML
    private TableColumn<Speaker, String> cognomeKeynote;
    @FXML
    private TableColumn<Speaker, String> emailKeynoteColumn;
    @FXML
    private TableColumn<Speaker, String> istituzioneKeynoteColumn;
    @FXML
    private TableColumn<Speaker, String> nomeKeynoteColumn;
    @FXML
    private TableView<Speaker> keynoteSpeakerTable;

    public InterventiSessione getInterventi() {
        return interventi;
    }

    public void setInterventi(InterventiSessione interventi) {
        this.interventi = interventi;
    }

    public IntervalliSessione getIntervalli() {
        return intervalli;
    }

    public void setIntervalli(IntervalliSessione intervalli) {
        this.intervalli = intervalli;
    }

    public EventiSocialiSessione getEventi() {
        return eventi;
    }

    public void setEventi(EventiSocialiSessione eventi) {
        this.eventi = eventi;
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

    public void setManageSessioniController(ManageSessioniController manageSessioniController) {
        this.manageSessioniController = manageSessioniController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }



    @FXML
    void editKeynoteButtonOnAction(ActionEvent event){

    }
    @FXML
    private void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessioni.fxml"));
        loader.setController(manageSessioniController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    void addEventoOnAction(ActionEvent event) {

    }

    @FXML
    void addIntervalloOnAction(ActionEvent event) {

    }

    @FXML
    void addInterventoOnAction(ActionEvent event) {

    }

    @FXML
    void deleteEventoOnAction(ActionEvent event) {

    }

    @FXML
    void deleteIntervalloOnAction(ActionEvent event) {

    }

    @FXML
    void deleteInterventoOnAction(ActionEvent event) {

    }

}
