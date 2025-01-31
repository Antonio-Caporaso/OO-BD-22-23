package Controller.Edit;

import Controller.AlertWindowController;
import Model.DAO.ConferenzaDao;
import Model.Entities.Conferenza;
import Model.Entities.Sede;
import Model.Entities.Sessione;
import Model.Utilities.Sedi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificaDettagliConferenza_Controller extends AlertWindowController implements Initializable {
    @FXML
    private ImageView alert_1;
    @FXML
    private ImageView alert_2;
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
    private ChoiceBox<Sede> sedeChoiceBox;
    private Sedi sedi = new Sedi();
    private SubScene subScene;

    public ModificaDettagliConferenza_Controller(Conferenza conferenza, SubScene subScene, ModificaConferenza_Controller modificaConferenzaController) {
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
        loadSedi();
        setDettagliConferenza();
        inizioDateTimePicker.valueProperty().addListener(observable -> {
            Timestamp data_inizio = Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue());
            conferenza.getSessioni().sort(Comparator.comparing(Sessione::getInizio));
            if (data_inizio.after(conferenza.getSessioni().get(0).getInizio())) {
                alert_1.setImage(new Image("/View/Resources/danger.png"));
                Tooltip.install(alert_1, new Tooltip("La data appena selezionata comporterà l'eliminazione di alcune sessioni"));
            } else {
                alert_1.setImage(null);
            }
        });
        fineDateTimePicker.valueProperty().addListener(observable -> {
            Timestamp data_fine = Timestamp.valueOf(fineDateTimePicker.getDateTimeValue());
            conferenza.getSessioni().sort(Comparator.comparing(Sessione::getInizio));
            if (data_fine.before(conferenza.getSessioni().get(conferenza.getSessioni().toArray().length - 1).getFine())) {
                alert_2.setImage(new Image("/View/Resources/danger.png"));
                Tooltip.install(alert_2, new Tooltip("La data appena selezionata comporterà l'eliminazione di alcune sessioni"));
            } else
                alert_2.setImage(null);
        });
    }

    public void setEditConferenceController(ModificaConferenza_Controller modificaConferenzaController) {
        this.modificaConferenzaController = modificaConferenzaController;
    }

    private void goBackToEditConferenceMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenza.fxml"));
        loader.setController(modificaConferenzaController);
        modificaConferenzaController.setConferenza(conferenza);
        modificaConferenzaController.setDetails();
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    private void goBackToEditConferenceWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenza.fxml"));
        loader.setController(modificaConferenzaController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    private void loadSedi() {
        try {
            sedi.loadSedi();
            sedeChoiceBox.setItems(sedi.getSedi());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDettagliConferenza() {
        nomeTF.setText(conferenza.getTitolo());
        descrizioneTextArea.setText(conferenza.getDescrizione());
        inizioDateTimePicker.setDateTimeValue(conferenza.getInizio().toLocalDateTime());
        fineDateTimePicker.setDateTimeValue(conferenza.getFine().toLocalDateTime());
        sedeChoiceBox.setValue(conferenza.getSede());
    }

    private void updateConferenza() throws SQLException {
        conferenza.setTitolo(nomeTF.getText());
        conferenza.setDescrizione(descrizioneTextArea.getText());
        conferenza.setInizio(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()));
        conferenza.setFine(Timestamp.valueOf(fineDateTimePicker.getDateTimeValue()));
        conferenza.setSede(sedeChoiceBox.getSelectionModel().getSelectedItem());
        ConferenzaDao dao = new ConferenzaDao();
        dao.updateDettagliConferenza(conferenza);
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        goBackToEditConferenceWindow();
    }

    @FXML
    void okOnAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler modificare i dettagli della conferenza?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                updateConferenza();
                goBackToEditConferenceMainWindow();
            } catch (SQLException exception) {
                showAlertWindow(Alert.AlertType.ERROR,"Errore", exception.getMessage());
            }
        }
    }
}
