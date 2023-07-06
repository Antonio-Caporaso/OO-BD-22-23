package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.Programma;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class KeynoteController_Edit implements Initializable {
    private ProgrammaController_Edit programmaControllerEdit;
    private Speaker keynote;
    private Programma programma;
    private InterventiSessione interventiSessione;
    private SubScene subScene;
    @FXML
    private Button addKeynoteButton;
    @FXML
    private TableColumn<Speaker, String> cognomeKeynoteColumn;
    @FXML
    private Button deleteEventoButton;
    @FXML
    private TableColumn<Speaker, String> emailKeynoteColumn;
    @FXML
    private Button fineButton;
    @FXML
    private TableColumn<Speaker, String> istituzioneKeynoteColumn;
    @FXML
    private TableView<Speaker> keynoteSpeakerTable;
    @FXML
    private TableColumn<Speaker, String> nomeKeynoteColumn;

    public ProgrammaController_Edit getEditProgrammaController() {
        return programmaControllerEdit;
    }

    public void setEditProgrammaController(ProgrammaController_Edit programmaControllerEdit) {
        this.programmaControllerEdit = programmaControllerEdit;
    }

    public Speaker getKeynote() {
        return keynote;
    }

    public void setKeynote(Speaker keynote) {
        this.keynote = keynote;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setKeynoteTable();
    }

    private void setKeynoteTable() {
            nomeKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            cognomeKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
            emailKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            istituzioneKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("istituzione"));
            keynoteSpeakerTable.getItems().add(keynote);
    }

    @FXML
    void addKeynoteButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ChoiceKeynote.fxml"));
        ChooseKeynoteController_Edit controller = new ChooseKeynoteController_Edit();
        controller.setInterventiSessione(interventiSessione);
        controller.setProgramma(programma);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene  = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void deleteEventoOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler eliminare il keynote speaker dal programma?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== ButtonType.OK){
            programma.setKeynote(null);
            ProgrammaDao dao = new ProgrammaDao();
            try {
                dao.removeKeynoteFromProgramma(programma);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditProgramma.fxml"));
        programmaControllerEdit.setProgramma(programma);
        loader.setController(programmaControllerEdit);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

}
