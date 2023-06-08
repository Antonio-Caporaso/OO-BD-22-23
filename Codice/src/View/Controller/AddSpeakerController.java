package View.Controller;

import Exceptions.BlankFieldException;
import Persistence.DAO.SpeakerDao;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.partecipanti.Speaker;
import Services.Enti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSpeakerController implements Initializable,FormChecker {
    private AddInterventoController addInterventoController;

    @FXML
    private Button addButton;

    @FXML
    private Button annullaButton;

    @FXML
    private TextField cognomeTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private ChoiceBox<Ente> istituzioneChoice;

    @FXML
    private TextField nomeTextField;

    @FXML
    private ChoiceBox<String> titoloChoice;
    private EditInterventoController editInterventoController;

    public void setEditInterventoController(EditInterventoController editInterventoController) {
        this.editInterventoController =editInterventoController;
    }

    public AddInterventoController getAddInterventoController() {
        return addInterventoController;
    }

    public void setAddInterventoController(AddInterventoController addInterventoController) {
        this.addInterventoController = addInterventoController;
    }

    @FXML
    void addOnAction(ActionEvent event) {
        Speaker s = retrieveDettagliSpeaker();
        SpeakerDao dao = new SpeakerDao();
        try{
            int id = dao.createSpeaker(s);
            s.setIdSpeaker(id);
            addInterventoController.setSpeaker(s);
            Stage stage = (Stage) annullaButton.getScene().getWindow();
            stage.close();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private Speaker retrieveDettagliSpeaker() {
        Speaker s = new Speaker();
        s.setCognome(cognomeTextField.getText());
        s.setNome(nomeTextField.getText());
        s.setEmail(emailTextField.getText());
        s.setIstituzione(istituzioneChoice.getValue().getNome());
        s.setTitolo(titoloChoice.getValue());
        return s;
    }

    @FXML
    void annullaOnAction(ActionEvent event) {
        Stage stage = (Stage) annullaButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTitoloChoiceBox();
        setIstituzioniChoiceBox();
    }

    private void setIstituzioniChoiceBox() {
        Enti enti = new Enti();
        enti.loadEnti();
        istituzioneChoice.setItems(enti.getEnti());
    }

    private void setTitoloChoiceBox() {
        String[] titoli ={"Dr.","Dott.","Dott.ssa","Prof.","Prof.ssa","Ing.","Sig","Sig.ra","Altro"};
        titoloChoice.getItems().addAll(titoli);
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if(
                titoloChoice.getValue().equals(null) ||
                nomeTextField.getText().isBlank() ||
                cognomeTextField.getText().isBlank() ||
                istituzioneChoice.getValue().equals(null) ||
                emailTextField.getText().isBlank()
        )
            throw new BlankFieldException();
    }
}

