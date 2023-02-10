package Controller;

import Model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LandingController {

    public Utente user;
    @FXML
    private Button creaConferenzaButton;
    @FXML
    private Button gestisciConferenzaButton;
    @FXML
    private Button visualizzaConferenza;
    @FXML
    private Button visualizzaStatisticheButton;
    @FXML
    private SubScene subscene;
    @FXML
    private Label labelWelcome;

    @FXML
    void creaConferenzaOnAction(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("../View/FXML/CreaConferenza.fxml"));
            subscene.setRoot(loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gestisciConferenzaOnAction(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("../View/FXML/GestisciConferenza.fxml"));
            subscene.setRoot(loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void visualizzaConferenzaOnAction(ActionEvent event) {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("../View/FXML/VisualizzaConferenza.fxml"));
            subscene.setRoot(loader);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    void visualizzaStatisticheOnAction(ActionEvent event){
        try{
            Parent loader = FXMLLoader.load(getClass().getResource("../View/FXML/VisualizzaStatistiche.fxml"));
            subscene.setRoot(loader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        assert creaConferenzaButton != null : "fx:id=\"creaConferenzaButton\" was not injected: check your FXML file 'Landing.fxml'.";
        assert gestisciConferenzaButton != null : "fx:id=\"gestisciConferenzaButton\" was not injected: check your FXML file 'Landing.fxml'.";
        assert visualizzaConferenza != null : "fx:id=\"visualizzaConferenza\" was not injected: check your FXML file 'Landing.fxml'.";
        assert visualizzaStatisticheButton != null:"fx:id=\"visualizzaStatisticheButton\" was not injected: check your FXML file 'Landing.fxml'.";
    }
    public void initData(Utente user){
        this.user = user;
    }
}
