package View.Controller;

import Persistence.DAO.ConferenzaDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sede;
import Utilities.Sedi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditDettagliConferenzaController implements Initializable {
    private Conferenza conferenza;
    private ModificaConferenzaController modificaConferenzaController;
    private SubScene subScene;
    private Sedi sedi = new Sedi();
    @FXML
    private Button annullaButton;
    @FXML
    private TextField descrizioneTF;
    @FXML
    private TextField nomeTF;
    @FXML
    private Button okButton;

    public void setEditConferenceController(ModificaConferenzaController modificaConferenzaController) {
        this.modificaConferenzaController = modificaConferenzaController;
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
        loader.setController(modificaConferenzaController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @FXML
    void okOnAction(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler modificare i dettagli della conferenza?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
            loader.setController(modificaConferenzaController);
            conferenza.setTitolo(nomeTF.getText());
            conferenza.setDescrizione(descrizioneTF.getText());
            ConferenzaDao dao = new ConferenzaDao();
            dao.updateDettagliConferenza(conferenza);
            modificaConferenzaController.setConferenza(conferenza);
            modificaConferenzaController.setDetails();
            Parent root = loader.load();
            subScene.setRoot(root);
        }
    }
    public Conferenza getConferenza() {
        return conferenza;
    }
    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeTF.setText(conferenza.getTitolo());
        descrizioneTF.setText(conferenza.getDescrizione());
    }
    public SubScene getSubScene() {
        return subScene;
    }
    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

}
