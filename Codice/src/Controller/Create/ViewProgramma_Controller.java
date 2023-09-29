package Controller.Create;

import Controller.AlertWindowController;
import Controller.Edit.ChooseKeynote_Controller;
import Controller.View.ShowInfoEventoSociale_Controller;
import Controller.View.ShowInfoIntervallo_Controller;
import Controller.View.ShowInfoIntervento_Controller;
import Model.Entities.*;
import Model.Entities.Speaker;
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

public class ViewProgramma_Controller extends AlertWindowController implements Initializable {
    @FXML
    private TableColumn<ActivityModel, String> appuntamentoTableColumn;
    private Conferenza conferenza;
    @FXML
    private TableColumn<ActivityModel, Timestamp> fineTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> inizioTableColumn;
    private Programma programma;
    @FXML
    private TableView<ActivityModel> programmaTableView;
    private Sessione sessione;
    @FXML
    private Label sessioneLabel;
    @FXML
    private Label keynoteLabel;
    @FXML
    private SubScene subscene;
    @FXML
    private Button keynoteButton;
    private Utente user;
    public ViewProgramma_Controller(SubScene subscene, Conferenza conferenza, Utente user, Sessione sessione){
        this.subscene = subscene;
        this.conferenza = conferenza;
        this.user = user;
        this.sessione = sessione;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProgramma();
        setSessioneLabel();
        setProgrammaTableView();
        disableKeynote();
    }

    private void goBackToVisualizzaSessioniScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/VisualizzaSessioniConferenza.fxml"));
            VisualizzaSessioniConferenza_Controller controller = new VisualizzaSessioniConferenza_Controller(subscene, conferenza, user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
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
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openAddEventoSocialeWindow() {
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
            stage.setAlwaysOnTop(true);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAddIntervalloWindow() {
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
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAddInterventoWindow() {
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
            stage.setAlwaysOnTop(true);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openAddKeynoteSpeaker() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ChoiceKeynote.fxml"));
            ChooseKeynote_Controller controller = new ChooseKeynote_Controller(programma);
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Selezione Keynote Speaker");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
            setKeynoteLabel();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openInfoIntervalloWindow(ActivityModel activityModel) {
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
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openInfoInterventoWindow(ActivityModel activityModel) {
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
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removePuntoProgramma(ActivityModel activityModel) {
        programma.removeActivity(activityModel);
        disableKeynote();
        setKeynoteLabel();
    }
    private void setKeynoteLabel(){
        Speaker keynote=programma.getKeynote();
        if(!(keynote.getIdSpeaker()==0)){
            keynoteLabel.setText("Keynote Speaker: " + programma.getKeynote().toString());
        } else
            keynoteLabel.setText("");
    }
    private void setProgramma() {
        programma = new Programma(sessione);
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

    private void setSessioneLabel() {
        sessioneLabel.setText(sessione.getTitolo());
    }
    private void disableKeynote(){
        keynoteButton.setDisable(true);
        for (ActivityModel activityModel: programmaTableView.getItems())
            if (activityModel instanceof Intervento)
                keynoteButton.setDisable(false);
    }

    @FXML
    void addIntervalloOnAction(ActionEvent event) {
        openAddIntervalloWindow();
        setProgrammaTableView();
    }

    @FXML
    void addInterventoOnAction(ActionEvent event) {
        openAddInterventoWindow();
        setProgrammaTableView();
        disableKeynote();
    }

    @FXML
    void addEventoSocialeOnAction(ActionEvent event) {
        openAddEventoSocialeWindow();
        setProgrammaTableView();
    }

    @FXML
    void removeButtonOnAction(ActionEvent event) {
        removePuntoProgramma(programmaTableView.getSelectionModel().getSelectedItem());
        setProgrammaTableView();
    }

    @FXML
    void showInfoScreen(MouseEvent event) {
        ActivityModel selected = programmaTableView.getSelectionModel().getSelectedItem();
        if (programma.getProgrammaSessione().contains(selected)) {
            if (selected instanceof Intervento)
                openInfoInterventoWindow(selected);
            if (selected instanceof EventoSociale)
                loadInfoEventoSociale(selected);
            else if (selected instanceof Intervallo)
                openInfoIntervalloWindow(selected);
        }
    }
    @FXML
    void choiceKeynoteOnAction(ActionEvent event) throws IOException{
        openAddKeynoteSpeaker();
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        goBackToVisualizzaSessioniScene();
    }
}
