package View.Controller;
import Exceptions.BlankFieldException;
import Persistence.Entities.Conferenze.*;
import Persistence.Entities.Utente;
import Persistence.Entities.partecipanti.Speaker;
import Services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddActivityController implements Initializable {
    @FXML
    private TextArea abstractTextArea;
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<String> eventiChoiceBox;
    @FXML
    private DateTimePicker inizioDateTimePicker;
    @FXML
    private Button inserisciButton;
    @FXML
    private ChoiceBox<Speaker> speakerChoiceBox;
    @FXML
    private ChoiceBox<String> tipologiaChoiceBox;
    @FXML
    private ChoiceBox<String> intervalloChoiceBox;
    @FXML
    private TextField titoloTextField;
    private Conferenza conferenza;
    private SubScene subscene;
    private Utente user;
    private Sessione sessione;
    private Programma programma;
    private Speakers speakers =new Speakers();
    //Public Setters
    public void setConferenza(Conferenza conferenza){
        this.conferenza=conferenza;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setUser(Utente utente){
        this.user=utente;
    }
    public void setSessione(Sessione sessione){this.sessione=sessione;}
    public void setProgramma(Programma programma){this.programma=programma;}
    //Button Methods
    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadViewProgramma();
    }
    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        try{
//            checkFieldsAreBlank();
            saveActivity();
            loadViewProgramma();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
//    @FXML
//    void tipologiaDragDetected(MouseEvent event) {
//        if(tipologiaChoiceBox.getValue()=="Intervento"){
//            eventiChoiceBox.visibleProperty();
//        }
//    }
//    @FXML
//    void tipologiaOnContextMenuRequested(ContextMenuEvent event) {
//        if(tipologiaChoiceBox.getValue()=="Intervento"){
//            eventiChoiceBox.visibleProperty();
//        }
//    }
//    @FXML
//    void tipologiaOnMouseDragExited(MouseDragEvent event) {
//        System.out.println("OnMouseDragExited");
//    }
//
//    @FXML
//    void tipologiaOnMouseDragOver(MouseDragEvent event) {
//        System.out.println("OnMouseDragOver");
//    }

    @FXML
    void tipologiaOnMouseClicked(MouseEvent event) {
        if(tipologiaChoiceBox.getValue()=="Intervento"){
            showIntervento();
            System.out.println("Intervento");
        }else if (tipologiaChoiceBox.getValue()=="Intervallo"){
            showIntervallo();
            System.out.println("Intervallo");
        }else if (tipologiaChoiceBox.getValue()=="Evento Sociale"){
            showEvento();
            System.out.println("EventoSociale");
        }
    }

//    @FXML
//    void tipologiaOnMousePressed(MouseEvent event) {
//        System.out.println("OnMousePressed");
//    }
//
//    @FXML
//    void tipologiaOnMouseClicked(MouseEvent event) {
//        System.out.println("OnMouseDragExited");
//        if(tipologiaChoiceBox.getValue()=="Intervento"){
//            eventiChoiceBox.visibleProperty();
//        }
//    }

    //Private Methods
    private void loadViewProgramma(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ViewProgrammaSessione.fxml"));
            ViewProgrammaController controller = new ViewProgrammaController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUser(user);
            controller.setSessione(sessione);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void saveActivity(){
        String selectedType = tipologiaChoiceBox.getValue();
        if (selectedType == null) {
            return;
        }
        switch (selectedType) {
            case "Intervento" -> saveIntervento();
            case "Intervallo" -> saveIntervallo();
            case "Evento Sociale" -> saveEventoSociale();
        }
    }
    private void saveIntervento(){
        try {
            InterventiSessione interventiSessione=new InterventiSessione(programma);
            Intervento intervento = new Intervento();
            Timestamp orario=Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue());
            intervento.setOrario(orario);
            intervento.setTitolo(titoloTextField.getText());
            intervento.setSpeaker(speakerChoiceBox.getValue());
            intervento.setEstratto(abstractTextArea.getText());
            intervento.setProgramma(programma);
            interventiSessione.saveIntervento(intervento);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void saveIntervallo(){
        try {
            IntervalliSessione intervalliSessione = new IntervalliSessione(programma);
            Intervallo intervallo = new Intervallo();
            intervallo.setOrario(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()));
            intervallo.setTipologia(intervalloChoiceBox.getValue());
            intervallo.setProgramma(programma);
            intervalliSessione.addIntervallo2(intervallo);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void saveEventoSociale(){
        try {
            EventiSocialiSessione eventiSocialiSessione = new EventiSocialiSessione(programma);
            EventoSociale eventoSociale = new EventoSociale();
            eventoSociale.setOrario(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()));
            eventoSociale.setTipologia(eventiChoiceBox.getValue());
            eventoSociale.setProgramma(programma);
            eventiSocialiSessione.addEvento(eventoSociale);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void populateChoiceBoxes(){
        ObservableList<String> items = FXCollections.observableArrayList("Intervallo", "Intervento", "Evento Sociale");
        tipologiaChoiceBox.setItems(items);
        items=FXCollections.observableArrayList("Coffee Break", "Pranzo");
        intervalloChoiceBox.setItems(items);
        items=FXCollections.observableArrayList("Gita", "Cena","Proiezione");
        eventiChoiceBox.setItems(items);
        speakers.loadSpeakers();
        speakerChoiceBox.setItems(speakers.getSpeakers());
    }
    private void showIntervallo(){
        abstractTextArea.setDisable(false);
        titoloTextField.setDisable(false);
        speakerChoiceBox.setDisable(false);
        intervalloChoiceBox.setDisable(true);
        eventiChoiceBox.setDisable(true);
    }
    private void showIntervento(){
        abstractTextArea.setDisable(true);
        titoloTextField.setDisable(true);
        speakerChoiceBox.setDisable(true);
        eventiChoiceBox.setDisable(true);
        intervalloChoiceBox.setDisable(false);
    }
    private void showEvento(){
        abstractTextArea.setDisable(true);
        titoloTextField.setDisable(true);
        speakerChoiceBox.setDisable(true);
        intervalloChoiceBox.setDisable(true);
        eventiChoiceBox.setDisable(false);
    }
    private void hideAll(){
        abstractTextArea.setDisable(true);
        titoloTextField.setDisable(true);
        speakerChoiceBox.setDisable(true);
        intervalloChoiceBox.setDisable(true);
        eventiChoiceBox.setDisable(true);
    }
//    Overrides
//    @Override
//    public void checkFieldsAreBlank() throws BlankFieldException {
//        if(inizioDateTimePicker.getValue().equals(null) || eventiChoiceBox.getValue().equals(null)
//                ||tipologiaChoiceBox.getValue().equals(null) || speakerChoiceBox.getValue().equals(null)||
//                titoloTextField.getText().isBlank()|| abstractTextArea.getText().isBlank())
//            throw new BlankFieldException();
//    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateChoiceBoxes();
        hideAll();
    }
}

