package Controller.Edit;

import Controller.Create.AddEventoSociale_Controller;
import Controller.Create.AddIntervallo_Controller;
import Controller.Create.AddIntervento_Controller;
import Controller.View.ShowInfoEventoSociale_Controller;
import Controller.View.ShowInfoIntervallo_Controller;
import Controller.View.ShowInfoIntervento_Controller;
import Model.Entities.*;
import Model.Utilities.ActivityModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
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

public class ModificaProgrammaSessione_Controller implements Initializable {
    @FXML
    private TableColumn<ActivityModel, String> appuntamentoTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> fineTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> inizioTableColumn;
    @FXML
    private Label fineSessioneLabel;
    @FXML
    private Label inizioSessioneLabel;
    @FXML
    private Label keynoteLabel;
    @FXML
    private Button keynoteButton;
    private ModificaSessioni_Controller manageSessioniController;
    private Programma programma;
    @FXML
    private TableView<ActivityModel> programmaTableView;
    private Sessione sessione;
    private SubScene subscene;

    public ModificaProgrammaSessione_Controller(Sessione s, SubScene subscene, ModificaSessioni_Controller modificaSessioniController) {
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
        disableKeynote();
        setOrarioSessioneLabel();
        setKeynoteLabel();
    }

    private void setOrarioSessioneLabel() {
        inizioSessioneLabel.setText(sessione.getInizio().toString());
        fineSessioneLabel.setText(sessione.getFine().toString());
    }

    public void setManageSessioniController(ModificaSessioni_Controller manageSessioniController) {
        this.manageSessioniController = manageSessioniController;
    }

    private void disableKeynote() {
        keynoteButton.setDisable(true);
        for (ActivityModel activityModel : programmaTableView.getItems())
            if (activityModel instanceof Intervento)
                keynoteButton.setDisable(false);
    }
    private void setKeynoteLabel(){
        Speaker keynote=programma.getKeynote();
        if(!(keynote.getIdSpeaker()==0)){
            keynoteLabel.setText(programma.getKeynote().toString());
        } else{
            keynoteLabel.setText("");
        }
    }
    @FXML
    private void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaSessioni.fxml"));
        loader.setController(manageSessioniController);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    private void loadAddEventoSociale() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddEventoSociale.fxml"));
            AddEventoSociale_Controller controller = new AddEventoSociale_Controller(programma);
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
            //stage.setAlwaysOnTop(true);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAddIntervallo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddIntervallo.fxml"));
            AddIntervallo_Controller controller = new AddIntervallo_Controller(programma);
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
            //stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAddIntervento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddIntervento.fxml"));
            AddIntervento_Controller controller = new AddIntervento_Controller(programma);
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
            //stage.setAlwaysOnTop(true);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            //stage.setAlwaysOnTop(true);
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
            //stage.setAlwaysOnTop(true);
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
            //stage.setAlwaysOnTop(true);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removePuntoProgramma(ActivityModel activityModel) {
        programma.removeActivity(activityModel);
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
        disableKeynote();
    }

    @FXML
    void deletePuntoOnAction(ActionEvent event) {
        removePuntoProgramma(programmaTableView.getSelectionModel().getSelectedItem());
        setProgrammaTableView();
        disableKeynote();
    }

    @FXML
    void choiceKeynoteOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ChoiceKeynote.fxml"));
        ChooseKeynote_Controller controller = new ChooseKeynote_Controller(programma);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Selezione Keynote Speaker");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
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
                loadInfoEventoSociale(selected);
            else if (selected instanceof Intervallo)
                loadInfoIntervallo(selected);
        }
    }
}
