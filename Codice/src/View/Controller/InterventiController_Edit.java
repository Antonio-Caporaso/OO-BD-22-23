package View.Controller;

import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.partecipanti.Speaker;
import Services.InterventiSessione;
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
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class InterventiController_Edit implements Initializable {
    private Programma programma;
    private InterventiSessione interventiSessione;
    private SubScene subScene;
    private Sessione sessione;
    private SessioniController_Manage sessioniControllerManage;
    private ProgrammaController_Edit programmaControllerEdit;
    @FXML
    private TableColumn<Intervento, String> abstractColumn;
    @FXML
    private Button addInterventoButton;

    @FXML
    private Button deleteInterventoButton;

    @FXML
    private Button editInterventoOnAction;

    @FXML
    private Button fineButton;
    @FXML
    private TableColumn<Intervento,String> titleColumn;

    @FXML
    private TableView<Intervento> interventiTable;

    @FXML
    private TableColumn<Intervento, Timestamp> orarioInterventoColumn;

    @FXML
    private TableColumn<Intervento, Speaker> speakerColumn;

    public ProgrammaController_Edit getEditProgrammaController() {
        return programmaControllerEdit;
    }

    public void setEditProgrammaController(ProgrammaController_Edit programmaControllerEdit) {
        this.programmaControllerEdit = programmaControllerEdit;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    public InterventiSessione getInterventiSessione() {
        return interventiSessione;
    }

    public void setInterventiSessione(InterventiSessione interventiSessione) {
        this.interventiSessione = interventiSessione;
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

    public SessioniController_Manage getManageSessioniController() {
        return sessioniControllerManage;
    }

    public void setManageSessioniController(SessioniController_Manage sessioniControllerManage) {
        this.sessioniControllerManage = sessioniControllerManage;
    }

    @FXML
    void addInterventoButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervento.fxml"));
        AddInterventoController_Edit controller = new AddInterventoController_Edit();
        controller.setInterventiSessione(interventiSessione);
        controller.setProgramma(programma);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void deleteInterventoOnAction(ActionEvent event) {
        Intervento i = interventiTable.getSelectionModel().getSelectedItem();
        if(i==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nessun intervento selezionato");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Sicuro di voler rimuovere l'intervento?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    interventiSessione.removeIntervento(i);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void editInterventoOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditIntervento.fxml"));
        InterventoController_Edit controller = new InterventoController_Edit();
        controller.setInterventiSessione(interventiSessione);
        controller.setIntervento(interventiTable.getSelectionModel().getSelectedItem());
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditProgramma.fxml"));
        loader.setController(programmaControllerEdit);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInterventiTable();
    }

    private void setInterventiTable(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        abstractColumn.setCellValueFactory(new PropertyValueFactory<>("estratto"));
        speakerColumn.setCellValueFactory(new PropertyValueFactory<>("speaker"));
        orarioInterventoColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
        interventiTable.setItems(interventiSessione.getInterventi());
    }
}

