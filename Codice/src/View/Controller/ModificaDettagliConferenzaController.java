package View.Controller;

import Exceptions.SediNonDisponibiliException;
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
import javafx.scene.input.MouseEvent;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificaDettagliConferenzaController implements Initializable {
    private Conferenza conferenza;
    private ModificaConferenzaController modificaConferenzaController;
    private SubScene subScene;
    @FXML
    private ChoiceBox<Sede> sedeChoiceBox;
    @FXML
    private TextArea descrizioneTextArea;
    @FXML
    private DateTimePicker fineDateTimePicker;
    @FXML
    private DateTimePicker inizioDateTimePicker;
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
            conferenza.setInizio(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()));
            conferenza.setFine(Timestamp.valueOf(fineDateTimePicker.getDateTimeValue()));
            conferenza.setSede(sedeChoiceBox.getSelectionModel().getSelectedItem());
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

    @FXML
    void showSedi(MouseEvent event)  {
        try {
            Timestamp inizio = Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue());
            Timestamp fine = Timestamp.valueOf(fineDateTimePicker.getDateTimeValue());
            sedi.loadSediLibere(inizio, fine);
            if (sedi.getSedi().isEmpty())
                throw new SediNonDisponibiliException();
            else
                sedeChoiceBox.setItems(sedi.getSedi());
        }catch (SediNonDisponibiliException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inserire delle date per visualizzare le sedi libere");
            alert.showAndWait();
        } catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
