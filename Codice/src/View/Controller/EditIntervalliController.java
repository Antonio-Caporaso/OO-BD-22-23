package View.Controller;

import Services.IntervalliSessione;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;

import java.net.URL;
import java.util.ResourceBundle;

public class EditIntervalliController implements Initializable {
    private EditProgrammaController editProgrammaController;
    private SubScene subScene;
    private IntervalliSessione intervalliSessione;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setIntervalli(IntervalliSessione intervalli) {
        intervalliSessione = intervalli;
    }

    public void setSubscene(SubScene subscene) {
        this.subScene = subscene;
    }

    public void setEditProgrammaController(EditProgrammaController editProgrammaController) {
        this.editProgrammaController = editProgrammaController;
    }
}
