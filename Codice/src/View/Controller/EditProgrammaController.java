package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
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
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;

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
    private Button editChairButton;
    @FXML
    private Label coordinatoreLabel;
    @FXML
    private Button fineButton;
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
        setProgramma();
    }

    private void setProgramma() {
        ProgrammaDao dao = new ProgrammaDao();
        try {
            programma = dao.retrieveProgrammaBySessione(sessione);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        intervalli = new IntervalliSessione(programma);
        interventi = new InterventiSessione(programma);
        eventi = new EventiSocialiSessione(programma);
    }

    private void setKeynoteLabel() {
        String keynote =programma.getKeynote().getNome()+" "+programma.getKeynote().getCognome();
        if(keynote.isBlank()){
            keynoteLabel.setText("Keynote speaker non previsto");
        }else{
            keynoteLabel.setText(keynote);
        }
    }

    private void setChairLabel() {
        coordinatoreLabel.setText(programma.getChair().getNome()+" "+programma.getChair().getCognome());
    }

    @FXML
    private void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        loader.setController(editConferenceController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

}
