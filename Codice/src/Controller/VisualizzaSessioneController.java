package Controller;

import Model.DAO.ProgrammaDao;
import Model.Entities.Conferenze.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
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

public class VisualizzaSessioneController implements Initializable {
    @FXML
    private TableColumn<ActivityModel, String> appuntamentoTableColumn;
    @FXML
    private Button confermaButton;
    @FXML
    private Label coordinatoreLabel;
    @FXML
    private Button editDetailsButton;
    @FXML
    private Button editProgrammaButton;
    @FXML
    private Label fineLabel;
    @FXML
    private TableColumn<ActivityModel, Timestamp> fineTableColumn;
    @FXML
    private Label inizioLabel;
    @FXML
    private TableColumn<ActivityModel, Timestamp> inizioTableColumn;
    @FXML
    private Label keynoteSpeakerLabel;
    @FXML
    private Label nomeLabel;
    private Programma programma;
    @FXML
    private TableView<ActivityModel> programmaTableView;
    @FXML
    private Label salaLabel;
    @FXML
    private Label titleLabel;
    private Sessione sessione;
    private SubScene subScene;
    private VisualizzaConferenzaController visualizzaConferenzaController;

    public VisualizzaSessioneController(Sessione sessione, SubScene subScene, VisualizzaConferenzaController visualizzaConferenzaController) {
        this.sessione = sessione;
        this.subScene = subScene;
        this.visualizzaConferenzaController = visualizzaConferenzaController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        retrieveProgrammaSessione();
        setDettagliSessione();
        setProgrammaTableView();
    }

    public void setDettagliSessione() {
        titleLabel.setText(sessione.getTitolo());
        nomeLabel.setText(sessione.getTitolo());
        inizioLabel.setText(sessione.getInizio().toString());
        fineLabel.setText(sessione.getFine().toString());
        salaLabel.setText(sessione.getLocazione().getNomeSala());
        coordinatoreLabel.setText(sessione.getCoordinatore().toString());
        keynoteSpeakerLabel.setText(programma.getKeynote().toString());
    }

    private void retrieveProgrammaSessione() {
        ProgrammaDao programmaDao = new ProgrammaDao();
        try {
            programma = programmaDao.retrieveProgrammaBySessione(sessione);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/VisualizzaConferenza.fxml"));
        loader.setController(visualizzaConferenzaController);
        subScene.setRoot(loader.load());
    }

    @FXML
    void showInfoScreen(MouseEvent event) {
        ActivityModel selected = programmaTableView.getSelectionModel().getSelectedItem();
        if (programma.getProgrammaSessione().contains(selected)) {
            if (selected instanceof Intervento)
                loadInfoIntervento(selected);
            if (selected instanceof EventoSociale)
                loadInfoEventoSociale(selected);
            else if (selected instanceof Intervallo)
                loadInfoIntervallo(selected);
        }
    }

    private void loadInfoIntervento(ActivityModel activityModel){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./View/FXML/ShowInfoIntervento_Create.fxml"));
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
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadInfoIntervallo(ActivityModel activityModel){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./View/FXML/ShowInfoIntervallo_Create.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./View/FXML/ShowInfoEventoSociale_Create.fxml"));
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
}
