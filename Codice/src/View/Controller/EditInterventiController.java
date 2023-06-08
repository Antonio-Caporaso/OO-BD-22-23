package View.Controller;

import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.partecipanti.Partecipante;
import Persistence.Entities.partecipanti.Speaker;
import Services.InterventiSessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class EditInterventiController implements Initializable {
    private Programma programma;
    private InterventiSessione interventiSessione;
    private SubScene subScene;
    private Sessione sessione;
    private ManageSessioniController manageSessioniController;
    private EditProgrammaController editProgrammaController;
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
    public EditProgrammaController getEditProgrammaController() {
        return editProgrammaController;
    }

    public void setEditProgrammaController(EditProgrammaController editProgrammaController) {
        this.editProgrammaController = editProgrammaController;
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

    public ManageSessioniController getManageSessioniController() {
        return manageSessioniController;
    }

    public void setManageSessioniController(ManageSessioniController manageSessioniController) {
        this.manageSessioniController = manageSessioniController;
    }

    @FXML
    void addInterventoButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervento.fxml"));
        AddInterventoController controller = new AddInterventoController();
        controller.setProgramma(programma);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    void deleteInterventoOnAction(ActionEvent event) {

    }

    @FXML
    void editInterventoOnAction(ActionEvent event) {

    }

    @FXML
    void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditProgramma.fxml"));
        loader.setController(editProgrammaController);
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

