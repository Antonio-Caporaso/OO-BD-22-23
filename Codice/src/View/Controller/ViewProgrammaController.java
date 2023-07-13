package View.Controller;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import Persistence.DAO.ProgrammaDao;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewProgrammaController implements Initializable
{
    @FXML
    private Button addPuntoButton;
    @FXML
    private Button removePuntoButton;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addInterventoButton;
    @FXML
    private Button addIntervalloButton;
    @FXML
    private Button addEventoSocialeButton;
    @FXML
    private Button backButton;
    @FXML
    private Button riepilogoButton;
    @FXML
    private Button rimuoviButton;
    @FXML
    private Label sessioneLabel;
    @FXML
    private TableColumn<ActivityModel, Speaker> speakerTableColumn;
    @FXML
    private TableColumn<ActivityModel, String> descrizioneTableColumn;
    @FXML
    private TableColumn<ActivityModel, String> appuntamentoTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> fineTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> inizioTableColumn;
    @FXML
    private TableView<ActivityModel> programmaTableView;
    @FXML
    private SubScene subscene;
    private Conferenza conferenza;
    private Sessione sessione;
    private Utente user;
    private Programma programma;
    //Public setters
    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setUser(Utente utente){
        this.user=utente;
    }
    public void setSessione(Sessione sessione){
        this.sessione=sessione;
    }
    //Button Methods
    @FXML
    void addIntervalloOnAction(ActionEvent event){
        loadAddIntervallo();
        setProgrammaTableView();
    }
    @FXML
    void addInterventoOnAction(ActionEvent event){
        loadAddIntervento();
        setProgrammaTableView();
    }
    @FXML
    void addEventoSocialeOnAction(ActionEvent event){
        loadAddEventoSociale();
    }
    @FXML
    void removeButtonOnAction(ActionEvent event){
        removePuntoProgramma(programmaTableView.getSelectionModel().getSelectedItem());
    }
    @FXML
    void riepilogoButtonOnAction(ActionEvent event){
        loadRiepilogo();
    }
    @FXML
    void backButtonOnAction(ActionEvent event){
        loadVisualizzaSessione();
    }


    //Private Methods
    private void loadAddIntervallo(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervallo_Create.fxml"));
            Parent root = loader.load();
            AddIntervalloController_Create controller = loader.getController();
            controller.setProgramma(programma);
            Stage stage = new Stage();
            Scene scene = new Scene(root, 608, 400);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void loadAddIntervento(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddIntervento_Create.fxml"));
            Parent root = loader.load();
            AddInterventoController_Create controller = loader.getController();
            controller.setProgramma(programma);
            Stage stage = new Stage();
            Scene scene = new Scene(root, 523, 627);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setX(860);
            stage.setY(360);
            stage.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void loadAddEventoSociale(){

    }
    private void removePuntoProgramma(ActivityModel activityModel){

    }
    private void loadRiepilogo(){

    }
    private void loadVisualizzaSessione(){
    }
    private void setProgrammaTableView(){
        try{
            programma.loadProgramaSessione();
            appuntamentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("appuntamento"));
            inizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
            fineTableColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
            speakerTableColumn.setCellValueFactory(new PropertyValueFactory<>("speaker"));
            descrizioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
            programmaTableView.setItems(programma.getProgrammaSessione());

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void loadSessioneLabel(){
        sessioneLabel.setText(sessione.getTitolo());
    }
    private void setProgramma(){
        programma=new Programma(sessione);
    }
    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProgramma();
        loadSessioneLabel();
        setProgrammaTableView();
    }
}
