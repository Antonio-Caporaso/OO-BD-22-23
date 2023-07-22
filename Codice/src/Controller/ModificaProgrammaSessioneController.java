package Controller;

import Model.Entities.Conferenze.*;
import Model.Entities.partecipanti.Speaker;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ModificaProgrammaSessioneController implements Initializable {
    @FXML
    private TableColumn<ActivityModel, String> appuntamentoTableColumn;
    @FXML
    private Button fineButton;
    @FXML
    private TableColumn<ActivityModel, Timestamp> fineTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> inizioTableColumn;
    private ModificaSessioniController manageSessioniController;
    private Programma programma;
    @FXML
    private TableView<ActivityModel> programmaTableView;
    private Sessione sessione;
    private SubScene subscene;

    public ModificaProgrammaSessioneController(Sessione s, SubScene subscene, ModificaSessioniController modificaSessioniController) {
        this.sessione = s;
        this.subscene = subscene;
        this.manageSessioniController = modificaSessioniController;
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProgrammaTableView();
    }

    public void setManageSessioniController(ModificaSessioniController manageSessioniController) {
        this.manageSessioniController = manageSessioniController;
    }

    @FXML
    private void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ModificaSessioni.fxml"));
        loader.setController(manageSessioniController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void removePuntoProgramma(ActivityModel activityModel) {
        programma.removeActivity(activityModel);
    }

    private void setProgrammaTableView() {
        try {
            programma.loadProgramaSessione();
            appuntamentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            inizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
            fineTableColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
            programmaTableView.setItems(programma.getProgrammaSessione());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addEventoOnAction(ActionEvent event) {
        loadAddEventoSociale();
    }

    @FXML
    void addIntervalloOnAction(ActionEvent event) {
        loadAddIntervallo();
    }

    @FXML
    void addInterventoOnAction(ActionEvent event) {
        loadAddIntervento();
    }

    @FXML
    void deletePuntoOnAction(ActionEvent event) {
        removePuntoProgramma(programmaTableView.getSelectionModel().getSelectedItem());
        setProgrammaTableView();
    }

    @FXML
    void choiceKeynoteOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ChoiceKeynote.fxml"));
        ChooseKeynoteController controller = new ChooseKeynoteController(programma);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Selezione Keynote Speaker");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void showInfoScreen(MouseEvent event) {
        ActivityModel selected = programmaTableView.getSelectionModel().getSelectedItem();
        if (programma.getProgrammaSessione().contains(selected)) {
            if (selected instanceof Intervento)
                loadInfoIntervento(selected);
            if (selected instanceof EventoSociale)
                loadInfoEventoSociale( selected);
            else if (selected instanceof Intervallo)
                loadInfoIntervallo( selected);
        }
    }
    private void loadInfoIntervento(ActivityModel activityModel){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ShowInfoIntervento_Create.fxml"));
            ShowInfoInterventoController_Create controller = new ShowInfoInterventoController_Create();
            controller.setActivityModel(activityModel);
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadInfoIntervallo(ActivityModel activityModel){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ShowInfoIntervallo_Create.fxml"));
            ShowInfoIntervalloController_Create controller = new ShowInfoIntervalloController_Create();
            controller.setActivityModel(activityModel);
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadInfoEventoSociale(ActivityModel activityModel){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ShowInfoEventoSociale_Create.fxml"));
            ShowInfoEventoSocialeController_Create controller = new ShowInfoEventoSocialeController_Create();
            controller.setActivityModel(activityModel);
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadAddIntervento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/AddIntervento_Create.fxml"));
            AddInterventoController_Create controller = new AddInterventoController_Create(programma);
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadAddIntervallo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/AddIntervallo_Create.fxml"));
            AddIntervalloController_Create controller = new AddIntervalloController_Create(programma);
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadAddEventoSociale() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/AddEventoSociale_Create.fxml"));
            AddEventoSocialeController_Create controller = new AddEventoSocialeController_Create(programma);
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
