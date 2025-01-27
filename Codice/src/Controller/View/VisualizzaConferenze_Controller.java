package Controller.View;

import Exceptions.DateMismatchException;
import Exceptions.NoConferencesException;
import Model.Entities.Conferenza;
import Model.Entities.Sede;
import Model.Utilities.Conferenze;
import Model.Utilities.Sedi;
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

public class VisualizzaConferenze_Controller implements Initializable {
    private final Conferenze conferenze = new Conferenze();
    private final Sedi sedi = new Sedi();
    @FXML
    private DatePicker dataFineDP;
    @FXML
    private DatePicker dataInizioDP;
    @FXML
    private TableColumn<Conferenza, String> descrizioneColumn;
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
    private final SubScene subScene;
    @FXML
    private TableView<Conferenza> tableConferenza;

    public VisualizzaConferenze_Controller(SubScene subscene) {
        this.subScene = subscene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            sedi.loadSedi();
            sedeChoice.setItems(sedi.getSedi());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTable(ObservableList<Conferenza> c) {
        tableConferenza.setEditable(false);
        nomeConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        nomeConferenzaColumn.setReorderable(false);
        inizioConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
        inizioConferenzaColumn.setReorderable(false);
        fineConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
        fineConferenzaColumn.setReorderable(false);
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        descrizioneColumn.setReorderable(false);
        sedeColumn.setCellValueFactory(new PropertyValueFactory<>("sede"));
        sedeColumn.setReorderable(false);
        tableConferenza.setItems(c);
    }

    private void findByDate() {
        try {
            Date dataInizio = Date.valueOf(dataInizioDP.getValue());
            Date dataFine = Date.valueOf(dataFineDP.getValue());
            conferenze.loadByDateInterval(dataInizio, dataFine);
            setTable(conferenze.getConferenze());
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Assicurati di aver inserito tutti i campi");
            alert.showAndWait();
        } catch (NoConferencesException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(e.toString());
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void findByDateAndSede() {
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
        } catch (NoConferencesException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(e.toString());
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private boolean checkDate()throws DateMismatchException{
        if(!dataFineDP.getValue().isBefore(dataInizioDP.getValue())){
            return true;
        }else{
            throw new DateMismatchException();
        }
    }

    @FXML
    void findButtonOnAction(ActionEvent event) {
        tableConferenza.getItems().clear();
        try{
            checkDate();
            if (sedeChoice.isDisabled())
                findByDate();
            else
                findByDateAndSede();
        }catch (DateMismatchException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    void visualizzaConferenzaOnAction(MouseEvent event) throws IOException {
        Conferenza c = tableConferenza.getSelectionModel().getSelectedItem();
        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selezionare una conferenza");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/View/VisualizzaConferenza.fxml"));
            VisualizzaConferenza_Controller controller = new VisualizzaConferenza_Controller(c, subScene, this);
            loader.setController(controller);
            subScene.setRoot(loader.load());
        }
    }

    @FXML
    void activateSediChoiceBox(ActionEvent event) {
        sedeChoice.setDisable(!sedeChoice.isDisabled());
    }
}
