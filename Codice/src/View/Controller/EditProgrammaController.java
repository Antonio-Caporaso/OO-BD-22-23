package View.Controller;

import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import Services.EventiSocialiSessione;
import Services.IntervalliSessione;
import Services.InterventiSessione;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;

import java.net.URL;
import java.util.ResourceBundle;

public class EditProgrammaController implements Initializable {
    private Programma programma;
    private SubScene subscene;
    private EditSessioneController editSessioneController;
    private InterventiSessione interventi;
    private IntervalliSessione intervalli;
    private Sessione sessione;
    private EventiSocialiSessione eventi;

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
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

    public void setEditSessioneController(EditSessioneController editSessioneController) {
        this.editSessioneController = editSessioneController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
