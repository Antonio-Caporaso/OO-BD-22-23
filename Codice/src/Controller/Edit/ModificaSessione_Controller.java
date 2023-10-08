package Controller.Edit;

import Controller.View.ShowInfoEventoSociale_Controller;
import Controller.View.ShowInfoIntervallo_Controller;
import Controller.View.ShowInfoIntervento_Controller;
import Model.DAO.ProgrammaDao;
import Model.Entities.*;
import Model.Utilities.ActivityModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
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

public class ModificaSessione_Controller implements Initializable {
    @FXML
    private TableColumn<ActivityModel, String> appuntamentoTableColumn;
    private Conferenza conferenza;
    @FXML
    private Label coordinatoreLabel;
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
    private ModificaSessioni_Controller manageSessioniController;
    @FXML
    private Label nomeLabel;
    private Programma programma;
    @FXML
    private TableView<ActivityModel> programmaTableView;
    @FXML
    private Label salaLabel;
    private Sessione sessione;
    private SubScene subScene;
    @FXML
    private Label titleLabel;

    public ModificaSessione_Controller(Sessione s, Conferenza conferenza, ModificaSessioni_Controller modificaSessioniController, SubScene subscene) {
        this.sessione = s;
        this.conferenza = conferenza;
        this.manageSessioniController = modificaSessioniController;
        this.subScene = subscene;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
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
        if(!(programma.getKeynote().getIdSpeaker()==0)){
            keynoteSpeakerLabel.setText(programma.getKeynote().toString());
        } else
            keynoteSpeakerLabel.setText("");
    }

    private void goToEditDettagliSessione() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaDettagliSessione.fxml"));
        ModificaDettagliSessione_Controller controllerS = new ModificaDettagliSessione_Controller(sessione, conferenza, subScene, this);
        loader.setController(controllerS);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    private void goToEditProgrammaWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaProgrammaSessione.fxml"));
        ModificaProgrammaSessione_Controller controller = new ModificaProgrammaSessione_Controller(sessione, subScene, manageSessioniController);
        controller.setProgramma(programma);
        loader.setController(controller);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    private void goToEditSessionsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaSessioni.fxml"));
        loader.setController(manageSessioniController);
        manageSessioniController.reloadSessioni();
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    private void loadInfoEventoSociale(ActivityModel activityModel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/View/ShowInfoEventoSociale.fxml"));
            ShowInfoEventoSociale_Controller controller = new ShowInfoEventoSociale_Controller();
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

    private void loadInfoIntervallo(ActivityModel activityModel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/View/ShowInfoIntervallo.fxml"));
            ShowInfoIntervallo_Controller controller = new ShowInfoIntervallo_Controller();
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

    private void loadInfoIntervento(ActivityModel activityModel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/View/ShowInfoIntervento.fxml"));
            ShowInfoIntervento_Controller controller = new ShowInfoIntervento_Controller();
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
            appuntamentoTableColumn.setReorderable(false);
            inizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
            inizioTableColumn.setReorderable(false);
            fineTableColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
            fineTableColumn.setReorderable(false);
            programmaTableView.setItems(programma.getProgrammaSessione());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        goToEditSessionsWindow();
    }

    @FXML
    void editProgrammaOnAction(ActionEvent event) throws IOException {
        goToEditProgrammaWindow();
    }

    @FXML
    void editDetailsOnAction(ActionEvent event) throws IOException {
        goToEditDettagliSessione();
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
}