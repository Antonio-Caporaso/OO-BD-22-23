package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.*;
import Persistence.Entities.partecipanti.Speaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ModificaProgrammaSessioneController implements Initializable {
    private Programma programma;
    private SubScene subscene;
    private ManageSessioniController manageSessioniController;
    private Sessione sessione;
    @FXML
    private Button editEventiButton;
    @FXML
    private Button editIntervalliButton;
    @FXML
    private Button editInterventiButton;
    @FXML
    private Button editKeynoteButton;
    @FXML
    private Button fineButton;
    @FXML
    private TableColumn<ActivityModel, Speaker> speakerTableColumn;
    @FXML
    private TableColumn<ActivityModel, String> descrizioneTableColumn;
    @FXML
    private TableColumn<ActivityModel, String> appuntamentoTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> fineTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> inizioTableColumn;
    @FXML
    private TableView<ActivityModel> programmaTableView;

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    public SubScene getSubscene() {
        return subscene;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setManageSessioniController(ManageSessioniController manageSessioniController) {
        this.manageSessioniController = manageSessioniController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProgrammaTableView();
    }
    @FXML
    private void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaSessioni.fxml"));
        loader.setController(manageSessioniController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    void editIntervalliButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditIntervalli.fxml"));
        EditIntervalliController controller = new EditIntervalliController();
        controller.setEditProgrammaController(this);
        controller.setProgramma(programma);
        controller.setSessione(sessione);
        controller.setSubScene(subscene);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void editInterventiButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditInterventi.fxml"));
        EditInterventiController controller = new EditInterventiController();
        controller.setEditProgrammaController(this);
        controller.setProgramma(programma);
        controller.setSessione(sessione);
        controller.setSubScene(subscene);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    void editKeynoteButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditKeynote.fxml"));
        EditKeynoteController controller = new EditKeynoteController();
        controller.setEditProgrammaController(this);
        controller.setProgramma(programma);
        controller.setSubScene(subscene);
        controller.setKeynote(programma.getKeynote());
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    void editEventiButtonOnAction(ActionEvent event) {

    }
    private void setProgrammaTableView(){
        try{

            programma.loadProgramaSessione();
            appuntamentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("appuntamento"));
            inizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
            fineTableColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
            speakerTableColumn.setCellValueFactory(new PropertyValueFactory<>("speaker"));
            descrizioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
            programmaTableView.setItems(programma.getProgrammaSessione());

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
