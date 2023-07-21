package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sede;
import Utilities.Conferenze;
import Utilities.Sedi;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class VisualizzaConferenzeController implements Initializable {
    @FXML
    private Button cercaPerDataButton;
    @FXML
    private Button cercaPerSedeButton;
    private Conferenze conferenze = new Conferenze();
    @FXML
    private DatePicker dataFineDP;
    @FXML
    private DatePicker dataInizioDP;
    @FXML
    private TableColumn<Conferenza, String> descrizioneColumn;
    @FXML
    private RadioButton filterBySedeRadioButton;
    @FXML
    private TableColumn<Conferenza, Timestamp> fineConferenzaColumn;
    @FXML
    private TableColumn<Conferenza, Timestamp> inizioConferenzaColumn;
    @FXML
    private TableColumn<Conferenza, String> nomeConferenzaColumn;
    @FXML
    private ChoiceBox<Sede> sedeChoice;
    @FXML
    private TableColumn<Conferenza, String> sedeColumn;
    private Sedi sedi = new Sedi();
    private SubScene subScene;
    @FXML
    private TableView<Conferenza> tableConferenza;
    @FXML
    private Button visualizzaButton;

    public VisualizzaConferenzeController(SubScene subscene) {
        this.subScene = subscene;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sedi.loadSedi();
        sedeChoice.setItems(sedi.getSedi());
    }

    private void setTable(ObservableList<Conferenza> c) {
        tableConferenza.setEditable(false);
        nomeConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        inizioConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
        fineConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        sedeColumn.setCellValueFactory(new PropertyValueFactory<>("sede"));
        tableConferenza.setItems(c);
    }

    @FXML
    void findButtonOnAction(ActionEvent event) {
        tableConferenza.getItems().clear();
        try {
            if (sedeChoice.isDisabled()) {
                try {
                    Date dataInizio = Date.valueOf(dataInizioDP.getValue());
                    Date dataFine = Date.valueOf(dataFineDP.getValue());
                    conferenze.loadByDateInterval(dataInizio, dataFine);
                    setTable(conferenze.getConferenze());
                } catch (NullPointerException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Assicurati di aver inserito tutti i campi");
                    alert.showAndWait();
                }
            } else {
                try {
                    Date dataInizio = Date.valueOf(dataInizioDP.getValue());
                    Date dataFine = Date.valueOf(dataFineDP.getValue());
                    Sede sede = sedeChoice.getSelectionModel().getSelectedItem();
                    conferenze.loadByDateAndSede(dataInizio, dataFine, sede);
                    setTable(conferenze.getConferenze());
                } catch (NullPointerException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Assicurati di aver inserito tutti i campi");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void visualizzaConferenzaOnAction(MouseEvent event) throws IOException {
        Conferenza c = tableConferenza.getSelectionModel().getSelectedItem();
        if (c.equals(null)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selezionare una conferenza");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaConferenza.fxml"));
            VisualizzaConferenzaController controller = new VisualizzaConferenzaController(c, subScene, this);
            loader.setController(controller);
            subScene.setRoot(loader.load());
        }
    }

    @FXML
    void activateSediChoiceBox(ActionEvent event) {
        sedeChoice.setDisable(!sedeChoice.isDisabled());
    }
}
