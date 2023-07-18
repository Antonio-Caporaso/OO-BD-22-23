package View.Controller;

import Persistence.Entities.Conferenze.*;
import Persistence.Entities.Utente;
import Persistence.Entities.partecipanti.Speaker;
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

public class ViewProgrammaController implements Initializable {
    @FXML
    private Button addEventoSocialeButton;
    @FXML
    private Button addIntervalloButton;
    @FXML
    private Button addInterventoButton;
    @FXML
    private Button addPuntoButton;
    @FXML
    private TableColumn<ActivityModel, String> appuntamentoTableColumn;
    @FXML
    private Button backButton;
    private Conferenza conferenza;
    @FXML
    private TableColumn<ActivityModel, String> descrizioneTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> fineTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> inizioTableColumn;
    @FXML
    private URL location;
    private Programma programma;
    @FXML
    private TableView<ActivityModel> programmaTableView;
    @FXML
    private Button removePuntoButton;
    @FXML
    private ResourceBundle resources;
    @FXML
    private Button riepilogoButton;
    @FXML
    private Button rimuoviButton;
    private Sessione sessione;
    @FXML
    private Label sessioneLabel;
    @FXML
    private TableColumn<ActivityModel, Speaker> speakerTableColumn;
    @FXML
    private SubScene subscene;
    private Utente user;

    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProgramma();
        loadSessioneLabel();
        setProgrammaTableView();
    }

    //Public setters
    public void setConferenza(Conferenza c) {
        this.conferenza = c;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setUser(Utente utente) {
        this.user = utente;
    }

    //Private Methods
    private void loadAddEventoSociale() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddEventoSociale_Create.fxml"));
            Parent root = loader.load();
            AddEventoSocialeController_Create controller = loader.getController();
            controller.setProgramma(programma);
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
    private void loadAddIntervallo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervallo_Create.fxml"));
            Parent root = loader.load();
            AddIntervalloController_Create controller = loader.getController();
            controller.setProgramma(programma);
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

    private void loadAddIntervento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervento_Create.fxml"));
            Parent root = loader.load();
            AddInterventoController_Create controller = loader.getController();
            controller.setProgramma(programma);
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

    private void loadRiepilogo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
            ModificaConferenzaController controller = new ModificaConferenzaController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUser(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSessioneLabel() {
        sessioneLabel.setText(sessione.getTitolo());
    }

    private void loadVisualizzaSessione() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaSessioniConferenza.fxml"));
            VisualizzaSessioniConferenza controller = new VisualizzaSessioniConferenza();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUser(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadInfoIntervento(Intervento intervento){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ShowInfoIntervento_Create.fxml"));
            Parent root = loader.load();
            ShowInfoInterventoController_Create controller = loader.getController();
            controller.initializeData(intervento);

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
    private void loadInfoIntervallo(Intervallo intervallo){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ShowInfoIntervallo_Create.fxml"));
            Parent root = loader.load();
            ShowInfoIntervalloController_Create controller = loader.getController();
            controller.initializeData(intervallo);

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
    private void loadInfoEventoSociale(EventoSociale eventoSociale){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ShowInfoEventoSociale_Create.fxml"));
            Parent root = loader.load();
            ShowInfoEventoSocialeController_Create controller = loader.getController();
            controller.initializeData(eventoSociale);

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
    }

    private void setProgramma() {
        programma = new Programma(sessione);
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

    //Button Methods
    @FXML
    void addIntervalloOnAction(ActionEvent event) {
        loadAddIntervallo();
        setProgrammaTableView();
    }

    @FXML
    void addInterventoOnAction(ActionEvent event) {
        loadAddIntervento();
        setProgrammaTableView();
    }

    @FXML
    void addEventoSocialeOnAction(ActionEvent event) {
        loadAddEventoSociale();
        setProgrammaTableView();
    }

    @FXML
    void removeButtonOnAction(ActionEvent event) {
        removePuntoProgramma(programmaTableView.getSelectionModel().getSelectedItem());
        setProgrammaTableView();
    }
    @FXML
    void showInfoScreen(MouseEvent event) {
        ActivityModel selected=programmaTableView.getSelectionModel().getSelectedItem();
        if (programma.getProgrammaSessione().contains(selected)) {
            if (selected instanceof Intervento)
                loadInfoIntervento((Intervento) selected);
            if (selected instanceof EventoSociale)
                loadInfoEventoSociale((EventoSociale) selected);
            else if (selected instanceof Intervallo)
                loadInfoIntervallo((Intervallo) selected);
        }
    }

    @FXML
    void riepilogoButtonOnAction(ActionEvent event) {
        loadRiepilogo();
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadVisualizzaSessione();
    }
}
