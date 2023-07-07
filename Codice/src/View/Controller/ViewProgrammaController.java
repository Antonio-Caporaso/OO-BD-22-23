package View.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import Persistence.DAO.*;
import Persistence.Entities.Conferenze.*;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.ActivityModel;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import Services.ProgrammaSessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewProgrammaController implements Initializable {
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
    private Label nomeSessione;
    @FXML
    private Button rimuoviButton;
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
    private Utente user;
    private Programma programma= new Programma();
    ProgrammaSessione programmaSessione;
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
        loadAggiungiPuntoProgramma();
    }
    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadInserisciSessione();
    }
    @FXML
    void riepilogoButtonOnAction(ActionEvent event) {
        loadEditConferenza();
    }
    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        try{
            ActivityModel selected =programmaTableView.getSelectionModel().getSelectedItem();
            if (selected == null){
                throw new NullPointerException();
            }
            Optional<ButtonType> result = showDeleteDialog();
            if(result.get() == ButtonType.OK) {
                removeActivity(selected);
            }
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessun punto selezionato");
            alert.showAndWait();
        }
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
    private void loadAggiungiPuntoProgramma(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/addActivity.fxml"));
            AddActivityController controller = new  AddActivityController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUser(user);
            controller.setSessione(sessione);
            controller.setProgramma(programma);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadEditConferenza(){
        try{
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
    private void setProgramma() {
        try {
            ProgrammaDao programmaSessioneDao=new ProgrammaDao();

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
    private void removeActivity (ActivityModel activityModel){
        programmaSessione.removeActivity(activityModel);
        setProgramma();
    }
    private Optional<ButtonType> showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler eliminare il seguente punto del programma?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programmaSessione= new ProgrammaSessione(sessione);
        setProgramma();
    }
    @FXML
    void initialize() {
        assert aggiungiButton != null : "fx:id=\"aggiungiButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert riepilogoButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert nomeSessione != null : "fx:id=\"nomeSessione\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
        assert rimuoviButton != null : "fx:id=\"rimuoviButton\" was not injected: check your FXML file 'ViewProgrammaSessione.fxml'.";
    }
}
