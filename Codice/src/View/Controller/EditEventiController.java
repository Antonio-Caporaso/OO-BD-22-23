package View.Controller;

import Persistence.Entities.Conferenze.EventoSociale;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditEventiController implements Initializable {
    private ModificaProgrammaSessioneController editProgrammaController;
    private Programma programma;
    private SubScene subScene;
    private Sessione sessione;
    private ManageSessioniController manageSessioniController;
    @FXML
    private Button addEventoButton;

    @FXML
    private Button deleteEventoButton;

    @FXML
    private Button editEventoOnAction;

    @FXML
    private TableView<EventoSociale> eventiTable;

    @FXML
    private Button fineButton;

    @FXML
    private TableColumn<EventoSociale, Time> orarioEventoColumn;

    @FXML
    private TableColumn<EventoSociale, String> tipologiaEventoColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEventiTable();
    }

    public void setEditProgrammaController(ModificaProgrammaSessioneController editProgrammaController) {
        this.editProgrammaController=editProgrammaController;
    }

    public ManageSessioniController getManageSessioniController() {
        return manageSessioniController;
    }

    public void setManageSessioniController(ManageSessioniController manageSessioniController) {
        this.manageSessioniController = manageSessioniController;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    @FXML
    void addEventoButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddEvento.fxml"));
        AddEventoController controller = new AddEventoController();
        controller.setProgramma(programma);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteEventoOnAction(ActionEvent event) {
        EventoSociale evento = eventiTable.getSelectionModel().getSelectedItem();
        if(evento==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nessun evento selezionato");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Sicuro di voler rimuovere l'evento?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    programma.removeEvento(evento);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void editEventoOnAction(ActionEvent event) throws IOException {

        EventoSociale e = eventiTable.getSelectionModel().getSelectedItem();
        if(e.equals(null)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selezionare un evento");
            alert.setContentText("Nessun evento selezionato");
            alert.showAndWait();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditEvento.fxml"));
            EditEventoController controller = new EditEventoController();
            controller.setEventoSociale(e);
            controller.setProgramma(programma);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    @FXML
    void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaProgrammaSessione.fxml"));
        loader.setController(editProgrammaController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }
    private void setEventiTable() {
        try {
            programma.loadEventiSociali();
            orarioEventoColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            tipologiaEventoColumn.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
            eventiTable.setItems(programma.getEventi());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

