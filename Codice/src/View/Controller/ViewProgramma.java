package View.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import Persistence.DAO.*;
import Persistence.Entities.Conferenze.*;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.ActivityModel;
import Services.ProgrammaSessione;
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
        loadInserisciSessione();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaSessione.fxml"));
            VisualizzaSessioneController controller = new  VisualizzaSessioneController();
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
    private void setProgramma() {
        try {
            sessione.setSessioneID(98);//Solo per testing, poiché la quantita di attività è limitata
            ProgrammaSessione programmaSessione= new ProgrammaSessione(sessione);
            ProgrammaDao programmaSessioneDao=new ProgrammaDao();
            Programma programma= new Programma();
            try {
                programma = programmaSessioneDao.retrieveProgrammaBySessione(sessione);
            }catch (SQLException e){
                e.printStackTrace();
            }
            attivitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("attivita"));
            orarioInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
            orarioFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataFine"));
            programmaTableView.setItems(programmaSessione.loadProgrammi());
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
