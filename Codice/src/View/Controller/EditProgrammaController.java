package View.Controller;

import Persistence.Entities.Conferenze.*;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
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
    private TableColumn<Intervento, String> abstractColumn;

    @FXML
    private TableColumn<Speaker, String> cognomeKeynoteColumn;

    @FXML
    private Button editEventiButton;

    @FXML
    private Button editIntervalliButton;

    @FXML
    private Button editInterventiButton;

    @FXML
    private Button editKeynoteButton;

    @FXML
    private TableColumn<Speaker, String> emailKeynoteColumn;

    @FXML
    private TableView<EventoSociale> eventiTable;

    @FXML
    private Button fineButton;

    @FXML
    private TableView<Intervento> interventiTable;

    @FXML
    private TableColumn<Speaker, String> istituzioneKeynoteColumn;

    @FXML
    private TableView<Intervallo> intervalliTable;

    @FXML
    private TableView<Speaker> keynoteSpeakerTable;

    @FXML
    private TableColumn<Speaker, String> nomeKeynoteColumn;

    @FXML
    private TableColumn<EventoSociale, Time> orarioEventoColumn;

    @FXML
    private TableColumn<Intervallo, Time> orarioIntervalloColumn;

    @FXML
    private TableColumn<Intervento, Time> orarioInterventoColumn;

    @FXML
    private TableColumn<Intervento,String> speakerColumn;

    @FXML
    private TableColumn<EventoSociale, String> tipologiaEventoColumn;

    @FXML
    private TableColumn<Intervallo, String> tipologiaIntervalloColumn;

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

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
        setIntervalliTable();
        setEventiTable();
        setInterventiTable();
        setKeynoteTable();
    }

    private void setKeynoteTable() {
        nomeKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        istituzioneKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("istituzione"));
        emailKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void setInterventiTable() {
    }

    private void setEventiTable() {
    }

    private void setIntervalliTable() {
    }

    @FXML
    private void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessioni.fxml"));
        loader.setController(manageSessioniController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    void editEventiButtonOnAction(ActionEvent event) {

    }

    @FXML
    void editIntervalliButtonOnAction(ActionEvent event) {

    }

    @FXML
    void editInterventiButtonOnAction(ActionEvent event) {

    }
    @FXML
    void editKeynoteButtonOnAction(ActionEvent event){

    }


}
