package Controller;

import Model.DAO.ProgrammaDao;
import Model.Entities.Conferenze.Programma;
import Model.Entities.partecipanti.Speaker;
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

public class EditKeynoteController implements Initializable {
    @FXML
    private Button addKeynoteButton;
    @FXML
    private TableColumn<Speaker, String> cognomeKeynoteColumn;
    @FXML
    private Button deleteEventoButton;
    private ModificaProgrammaSessioneController editProgrammaController;
    @FXML
    private TableColumn<Speaker, String> emailKeynoteColumn;
    @FXML
    private Button fineButton;
    @FXML
    private TableColumn<Speaker, String> istituzioneKeynoteColumn;
    private Speaker keynote;
    @FXML
    private TableView<Speaker> keynoteSpeakerTable;
    @FXML
    private TableColumn<Speaker, String> nomeKeynoteColumn;
    private Programma programma;
    private SubScene subScene;

    public ModificaProgrammaSessioneController getEditProgrammaController() {
        return editProgrammaController;
    }

    public void setEditProgrammaController(ModificaProgrammaSessioneController modificaProgrammaSessioneController) {
        this.editProgrammaController = modificaProgrammaSessioneController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ChoiceKeynote.fxml"));
        ChooseKeynoteController controller = new ChooseKeynoteController(programma);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
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
        if (result.get() == ButtonType.OK) {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ModificaProgrammaSessione.fxml"));
        editProgrammaController.setProgramma(programma);
        loader.setController(editProgrammaController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

}
