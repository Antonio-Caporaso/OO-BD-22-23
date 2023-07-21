package Controller;

import Model.DAO.ConferenzaDao;
import Model.Entities.Conferenze.Conferenza;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.postgresql.util.PGInterval;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SlittaConferenzaController implements Initializable {
    @FXML
    private Button annullaButton;
    private Conferenza conferenza;
    @FXML
    private TextField giorniTF;
    private ModificaConferenzaController modificaConferenzaController;
    @FXML
    private Button okButton;

    public SlittaConferenzaController(Conferenza conferenza, ModificaConferenzaController modificaConferenzaController) {
        this.conferenza = conferenza;
        this.modificaConferenzaController = modificaConferenzaController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public void setModificaConferenzaController(ModificaConferenzaController modificaConferenzaController) {
        this.modificaConferenzaController = modificaConferenzaController;
    }

    private void slittaConferenza(PGInterval durata) throws SQLException {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        conferenzaDao.slittaConferenza(conferenza, durata);
    }

    @FXML
    void annullaButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void okButtonOnAction(ActionEvent event) throws SQLException {
        try {
            int giorni = Integer.parseInt(giorniTF.getText());
            PGInterval durata = new PGInterval(0, 0, giorni, 0, 0, 0);
            slittaConferenza(durata);
            modificaConferenzaController.reloadConferenza();
            Stage stage = (Stage) annullaButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Input non valido");
            alert.setContentText("Inserire un numero!");
            alert.showAndWait();
        }
    }
}
