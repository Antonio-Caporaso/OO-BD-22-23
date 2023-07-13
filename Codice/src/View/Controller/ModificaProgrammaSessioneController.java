package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.*;
import Persistence.Entities.partecipanti.Speaker;
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
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ModificaProgrammaSessioneController implements Initializable {
    private Programma programma;
    private SubScene subscene;
    private ManageSessioniController manageSessioniController;
    private Sessione sessione;
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
    void addPuntoOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddPunto.fxml"));
        AddPuntoController controller = new AddPuntoController(programma);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Aggiunta nuovo punto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deletePuntoOnAction(ActionEvent event) {

    }

    @FXML
    void editPuntoOnAction(ActionEvent event) {

    }
    private void setProgrammaTableView(){
        try{

            programma.loadProgramaSessione();
            appuntamentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
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
