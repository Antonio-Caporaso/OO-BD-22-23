package View.Controller;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import Persistence.Entities.Conferenze.*;
import Persistence.Entities.Utente;
import Persistence.Entities.partecipanti.Speaker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.*;
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
    private Button aggiungiButton;
    @FXML
    private Button backButton;
    @FXML
    private Button riepilogoButton;
    @FXML
    private Button rimuoviButton;
    @FXML
    private TableColumn<Activity, Speaker> SpeakerColumn;
    @FXML
    private TableColumn<Activity, String> descrizioneColumn;
    @FXML
    private TableColumn<Activity, String> entryColumn;
    @FXML
    private TableColumn<Activity, Timestamp> fineColumn;
    @FXML
    private TableColumn<Activity, Timestamp> inizioColumn;
    @FXML
    private TableView<Activity> programmaTable;
    @FXML
    private SubScene subscene;
    private Conferenza conferenza;
    private Sessione sessione;
    private Utente user;
    private Programma programma= new Programma();
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
