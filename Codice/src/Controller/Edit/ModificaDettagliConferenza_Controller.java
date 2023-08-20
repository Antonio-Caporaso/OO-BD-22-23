package Controller.Edit;

import Exceptions.SediNonDisponibiliException;
import Model.DAO.ConferenzaDao;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Sede;
import Model.Utilities.Sedi;
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

public class ModificaDettagliConferenza_Controller implements Initializable {
    @FXML
    private Button annullaButton;
    private Conferenza conferenza;
    @FXML
    private TextArea descrizioneTextArea;
    @FXML
    private DateTimePicker fineDateTimePicker;
    @FXML
    private DateTimePicker inizioDateTimePicker;
    private ModificaConferenza_Controller modificaConferenzaController;
    @FXML
    private TextField nomeTF;
    @FXML
    private Button okButton;
    @FXML
    private ChoiceBox<Sede> sedeChoiceBox;
    private Sedi sedi = new Sedi();
    private SubScene subScene;
    public ModificaDettagliConferenza_Controller(Conferenza conferenza, SubScene subScene,ModificaConferenza_Controller modificaConferenzaController){
        this.conferenza = conferenza;
        this.subScene = subScene;
        this.modificaConferenzaController = modificaConferenzaController;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeTF.setText(conferenza.getTitolo());
        descrizioneTextArea.setText(conferenza.getDescrizione());
        inizioDateTimePicker.setDateTimeValue(conferenza.getInizio().toLocalDateTime());
        fineDateTimePicker.setDateTimeValue(conferenza.getFine().toLocalDateTime());
        sedeChoiceBox.setValue(conferenza.getSede());
    }

    public void setEditConferenceController(ModificaConferenza_Controller modificaConferenzaController) {
        this.modificaConferenzaController = modificaConferenzaController;
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenza.fxml"));
        loader.setController(modificaConferenzaController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @FXML
    void okOnAction(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler modificare i dettagli della conferenza?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenza.fxml"));
            loader.setController(modificaConferenzaController);
            conferenza.setTitolo(nomeTF.getText());
            conferenza.setDescrizione(descrizioneTextArea.getText());
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

    @FXML
    void showSediDisponibili(MouseEvent event) {
        try {
            Timestamp inizio = Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue());
            Timestamp fine = Timestamp.valueOf(fineDateTimePicker.getDateTimeValue());
            sedi.loadSediLibere(inizio, fine);
            if (sedi.getSedi().isEmpty())
                throw new SediNonDisponibiliException();
            else
                sedeChoiceBox.setItems(sedi.getSedi());
        } catch (SediNonDisponibiliException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inserire delle date per visualizzare le sedi libere");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
