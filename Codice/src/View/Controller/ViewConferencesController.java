package View.Controller;
import Persistence.Entities.Conferenze.Conferenza;
import Services.Conferenze;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewConferencesController implements Initializable {
    @FXML
    private Button viewConferenceButton;
    @FXML
    private ListView<Conferenza> conferenceVIew;
    private Conferenze conferenze = new Conferenze();

    @FXML
    void viewOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conferenze.loadConferenze();
            conferenceVIew.setItems(conferenze.getConferenze());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
