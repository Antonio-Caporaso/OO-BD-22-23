package View.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.ActivityModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewProgramma implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button aggiungiButton;

    @FXML
    private Button backButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label nomeSessione;
    @FXML
    private Button rimuoviButton;
    @FXML
    private ListView<Programma> programmaListView;
    @FXML
    private TableView<ActivityModel> programmaTableView;
    @FXML
    private TableColumn<ActivityModel, String> attivitaTableColumn;
    @FXML
    private TableColumn<ActivityModel, LocalDateTime> orarioFineTableColumn;

    @FXML
    private TableColumn<ActivityModel, LocalDateTime> orarioInizioTableColumn;
    @FXML
    private SubScene subscene;
    ObservableList<ActivityModel> activityList = FXCollections.observableArrayList();
    private Conferenza conferenza;
    private Sessione sessione;
    private ProgrammaDao programmaDao= new ProgrammaDao();
    private Utente user;
    //Public Setters
    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setUser(Utente utente){
        this.user=utente;
    }
    public void setSessione(Sessione sessione){this.sessione=sessione;}
    //Button Methods
    @FXML
    void aggiungiButtonOnAction(ActionEvent event) {
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
    }
    @FXML
    void confirmButtonOnAction(ActionEvent event) {
    }
    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
    }
    //Private methods
    private void loadInserisciSessione(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/InserisciSessione.fxml"));
            InserisciSessioneController controller = new InserisciSessioneController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUtente(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setProgramma() {
        try {
            // Retrieve the required data from the database tables (Intervento, Intervallo, Eventosociale)
            // and populate an ObservableList of ActivityModel objects with the combined data
//            ObservableList<ActivityModel> activityList = FXCollections.observableArrayList();
            // Populate the activityList with data from the database


            attivitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("attivita"));


           orarioInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));


            orarioFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataFine"));

            programmaTableView.getColumns().addAll(attivitaTableColumn, orarioInizioTableColumn, orarioFineTableColumn);

            programmaTableView.setItems(activityList);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProgramma();
    }
    @FXML
    void initialize() {
        assert aggiungiButton != null : "fx:id=\"aggiungiButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert nomeSessione != null : "fx:id=\"nomeSessione\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert rimuoviButton != null : "fx:id=\"rimuoviButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
    }
}
