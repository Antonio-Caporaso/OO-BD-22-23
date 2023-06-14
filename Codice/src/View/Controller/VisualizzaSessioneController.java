package View.Controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;

import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.organizzazione.Organizzatore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import Exceptions.SessioneNotSelectedException;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Services.Sessioni;
import javafx.scene.control.cell.PropertyValueFactory;

public class VisualizzaSessioneController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button inserisciButton;
    @FXML
    private Button riepilogoButton;
    @FXML
    private Button aggiungiProgrammaButton;
    @FXML
    private Button rimuoviButton;
    @FXML
    private TableView<Sessione> sessioniTableView;
    @FXML
    private TableColumn<Sessione,String> titoloTableColumn;
    @FXML
    private TableColumn<Sessione, Time > inizioTableColumn;
    @FXML
    private TableColumn<Sessione, Time> fineTableColumn;
    @FXML
    private TableColumn<Sessione, Date> dataInizioTableColumn;
    @FXML
    private TableColumn<Sessione, Date> dataFineTableColumn;
    @FXML
    private TableColumn<Sessione, Sala> salaTableColumn;
    @FXML
    private TableColumn<Sessione, Organizzatore> chairTableColumn;
    @FXML
    private SubScene subscene;
    private Conferenza conferenza;
    private Sessioni sessioni = new Sessioni(conferenza);
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
    //Button Methods
    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        loadInserisciSessione();
    }
    @FXML
    void riepilogoButtonOnAction(ActionEvent event) {
        loadEditConferenza();
    }
    @FXML
    void aggiungiProgrammaButtonOnAction(ActionEvent event){loadViewProgramma();}
    @FXML
    void backButtonOnAction(ActionEvent event){
        loadAggiungiSponsor();
    }
    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        try {
            Sessione s = sessioniTableView.getSelectionModel().getSelectedItem();
            if (s == null) {
                throw new SessioneNotSelectedException();
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Sicuro di voler rimuovere la sessione '" + s.getTitolo() + "'?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    sessioni.removeSessione(s);
                    setSessioni();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }catch (SessioneNotSelectedException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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
    private void loadAggiungiSponsor(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddSponsor.fxml"));
            AggiungiSponsorController controller = new AggiungiSponsorController();
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
    private void loadViewProgramma(){
        try{
            Sessione s = sessioniTableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ViewProgrammaSessione.fxml"));
            ViewProgrammaController controller = new ViewProgrammaController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUser(user);
            controller.setSessione(s);
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
    private void setSessioni() {
    Sessioni sessioni= new Sessioni(conferenza);
    try {
        sessioni.loadSessioni();
        sessioni.orderSessioni();
        titoloTableColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        dataInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
        inizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("orarioInizio"));
        dataFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataFine"));
        fineTableColumn.setCellValueFactory(new PropertyValueFactory<>("orarioFine"));
        salaTableColumn.setCellValueFactory(new PropertyValueFactory<>("locazione"));
        chairTableColumn.setCellValueFactory(new PropertyValueFactory<>("coordinatore"));
        sessioniTableView.setItems(sessioni.getSessioni());
    }catch(SQLException e){
        e.printStackTrace();
    }
}
    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSessioni();
    }

}
