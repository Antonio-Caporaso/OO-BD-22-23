package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LandingController {
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
//    private NavigationController navigationController = NavigationController.getInstance();
//
//    @FXML
//    void creaConferenzaOnAction(ActionEvent event) {
//        loadScene("/View/FXML/Registrazione.fxml", creaConferenzaButton);
//    }

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
    }
}
