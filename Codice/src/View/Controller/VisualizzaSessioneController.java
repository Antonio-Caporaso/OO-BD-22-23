package View.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import Exceptions.SessioneNotSelectedException;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Services.Sessioni;
public class VisualizzaSessioneController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button inserisciButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button rimuoviButton;
    @FXML
    private ListView<Sessione> sessioniListView;
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
    void nextButtonOnAction(ActionEvent event) {
    }
    @FXML
    void backButtonOnAction(ActionEvent event){
        loadAggiungiSponsor();
    }
    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        try {
            Sessione s = sessioniListView.getSelectionModel().getSelectedItem();
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
    private void setSessioni() {
    Sessioni sessioni= new Sessioni(conferenza);
    try {
        sessioni.loadSessioni();
        sessioniListView.setItems(sessioni.getSessioni());
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
