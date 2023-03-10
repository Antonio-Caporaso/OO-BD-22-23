package View.Controller;

import Persistence.Entities.Conferenze.EventoSociale;
import Persistence.Entities.Conferenze.Programma;
import Services.EventiSocialiSessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditEventiController implements Initializable {
    private SubScene subScene;
    private EditProgrammaController editProgrammaController;
    private Programma programma;
    private EventiSocialiSessione eventi;
    @FXML
    private Button addEventoButton;
    @FXML
    private Button deleteEventoButton;
    @FXML
    private ListView<EventoSociale> eventiList;
    @FXML
    private Button fineButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventiList.setItems(eventi.getEventi());
    }

    public EditProgrammaController getEditProgrammaController() {
        return editProgrammaController;
    }

    public void setEditProgrammaController(EditProgrammaController editProgrammaController) {
        this.editProgrammaController = editProgrammaController;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    public EventiSocialiSessione getEventi() {
        return eventi;
    }

    public void setEventi(EventiSocialiSessione eventi) {
        this.eventi = eventi;
    }

    @FXML
    void addEventoOnAction(ActionEvent event) throws IOException {
        openAddingModalWindow();
    }

    private void openAddingModalWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddEvento.fxml"));
        AddEventoController controller = new AddEventoController();
        controller.setEditEventiController(this);
        controller.setEventi(eventi);
        controller.setProgramma(programma);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Aggiungi evento");
        stage.show();
    }

    @FXML
    void deleteEventoOnAction(ActionEvent event) {
        EventoSociale e = eventiList.getSelectionModel().getSelectedItem();
        try {
            eventi.removeEvento(e);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void fineOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditProgramma.fxml"));
        loader.setController(editProgrammaController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

}
