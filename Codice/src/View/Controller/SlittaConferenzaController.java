package View.Controller;

import Persistence.DAO.ConferenzaDao;
import Persistence.Entities.Conferenze.Conferenza;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import org.postgresql.util.PGInterval;

import java.sql.SQLException;

public class SlittaConferenzaController {
    private Conferenza conferenza;
    private ModificaConferenzaController modificaConferenzaController;

    @FXML
    private Button annullaButton;

    @FXML
    private Spinner<Integer> giorniSpinner;

    @FXML
    private Button okButton;

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public void setModificaConferenzaController(ModificaConferenzaController modificaConferenzaController) {
        this.modificaConferenzaController = modificaConferenzaController;
    }

    @FXML
    void annullaButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void okButtonOnAction(ActionEvent event) throws SQLException {
        PGInterval durata = new PGInterval(0,0, giorniSpinner.getValue(), 0,0,0);
        slittaConferenza(durata);
        modificaConferenzaController.reloadConferenza();
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    private void slittaConferenza(PGInterval durata) throws SQLException {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenzaDao.slittaConferenza(conferenza,durata);
    }
}
