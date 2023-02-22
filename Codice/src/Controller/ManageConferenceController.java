package Controller;

import Model.Conferenze.Conferenza;
import Model.Utente;
import Presenter.ConferenceModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageConferenceController implements Initializable {
    @FXML
    private ListView<Conferenza> conferenzeView;
    @FXML
    private Button addSessioneButton;
    @FXML
    private Button deleteConferenzaButton;
    @FXML
    private Button editSessioneButton;
    @FXML
    private Button modificaButton;
    private ConferenceModel conferenze = new ConferenceModel();
    private Utente user;

    public ManageConferenceController(Utente user) {
        this.user = user;
    }

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conferenze.loadConferenzeUtente(user);
            conferenzeView.getItems().addAll(conferenze.getConferenzeUtente());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
