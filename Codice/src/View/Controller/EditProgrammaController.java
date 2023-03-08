package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.*;
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
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditProgrammaController implements Initializable {
    private Programma programma;
    private SubScene subscene;
    private EditConferenceController editConferenceController;
    private InterventiSessione interventi;
    private IntervalliSessione intervalli;
    private Sessione sessione;
    private EventiSocialiSessione eventi;
    @FXML
    private Label coordinatoreLabel;
    @FXML
    private Button editEventiButton;
    @FXML
    private Button editIntervalliButton;
    @FXML
    private Button editInterventiButton;
    @FXML
    private TextArea eventiTextArea;
    @FXML
    private Button fineButton;
    @FXML
    private TextArea intervalliTextArea;
    @FXML
    private TextArea interventiTextArea;
    @FXML
    private Label keynote;
    @FXML
    private Label keynoteLabel;

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

    public void setEditConferenceController(EditConferenceController editConferenceController) {
        this.editConferenceController = editConferenceController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inizializzaDettagliProgramma();
    }
    private void inizializzaDettagliProgramma(){
        ProgrammaDao dao = new ProgrammaDao();
        try{
            programma = dao.retrieveProgrammaBySessione(sessione);
            intervalli = new IntervalliSessione(programma);
            interventi = new InterventiSessione(programma);
            eventi = new EventiSocialiSessione(programma);
            intervalli.loadIntervalli();
            for(Intervallo i: intervalli.getIntervalli()){
                intervalliTextArea.appendText(i.toString()+"\n");
            }
            interventi.loadInterventi();
            for(Intervento i: interventi.getInterventi()){
                interventiTextArea.appendText(i.toString()+"\n");
            }
            eventi.loadEventiSociali();
            for(EventoSociale e: eventi.getEventi()){
                eventiTextArea.appendText(e.toString()+"\n");
            }
            coordinatoreLabel.setText(programma.getChair().getNome()+" "+programma.getChair().getCognome());
            if(programma.getKeynote().getNome() == null){
                keynoteLabel.setVisible(false);
                keynote.setVisible(false);
            }else {
                keynote.setText(programma.getKeynote().getNome()+" "+programma.getKeynote().getCognome());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        loader.setController(editConferenceController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void editEventiOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditEventi.fxml"));
        EditEventiController controller = new EditEventiController();
        controller.setEditProgrammaController(this);
        controller.setSubScene(subscene);
        controller.setEventi(eventi);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void editIntervalliOnAction(ActionEvent event) {

    }

    @FXML
    void editInterventiOnAction(ActionEvent event) {

    }
}
