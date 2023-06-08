package View.Controller;

import Persistence.Entities.Conferenze.Intervallo;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import Services.IntervalliSessione;
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

public class EditIntervalliController implements Initializable {
    private EditProgrammaController editProgrammaController;
    private Programma programma;
    private IntervalliSessione intervalliSessione;
    private SubScene subScene;
    private Sessione sessione;
    private ManageSessioniController manageSessioniController;
    @FXML
    private Button addIntervalloButton;

    @FXML
    private Button deleteIntervalloButton;

    @FXML
    private Button editIntervalloOnAction;

    @FXML
    private Button fineButton;

    @FXML
    private TableView<Intervallo> intervalliTable;

    @FXML
    private TableColumn<Intervallo, Timestamp> orarioIntervalloColumn;

    @FXML
    private TableColumn<Intervallo, String> tipologiaIntervalloColumn;

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

    public IntervalliSessione getIntervalliSessione() {
        return intervalliSessione;
    }

    public void setIntervalliSessione(IntervalliSessione intervalliSessione) {
        this.intervalliSessione = intervalliSessione;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIntervalliTable();
    }

    private void setIntervalliTable() {
        try {
            intervalliSessione.loadIntervalli();
            tipologiaIntervalloColumn.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
            orarioIntervalloColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            intervalliTable.setItems(intervalliSessione.getIntervalli());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addIntervalloButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervallo.fxml"));
        AddIntervalloController controller = new AddIntervalloController();
        controller.setIntervalliSessione(intervalliSessione);
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
    void deleteIntervalloOnAction(ActionEvent event) {
        Intervallo intervallo = intervalliTable.getSelectionModel().getSelectedItem();
        if (intervallo == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nessun intervallo selezionato");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Sicuro di voler rimuovere questo intervallo?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    intervalliSessione.removeIntervallo(intervallo);
                } catch (SQLException e) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setContentText(e.getMessage());
                    alerta.showAndWait();
                }
            }
        }
    }

    @FXML
    void editIntervalloOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditIntervallo.fxml"));
        Intervallo intervallo = intervalliTable.getSelectionModel().getSelectedItem();
        EditIntervalloController controller = new EditIntervalloController();
        controller.setIntervalliSessione(intervalliSessione);
        controller.setIntervallo(intervallo);
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
    void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditProgramma.fxml"));
        loader.setController(editProgrammaController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }
}
