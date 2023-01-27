package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LandingController {

    @FXML
    private Button creaConferenzaButton;

    @FXML
    private Button gestisciConferenzaButton;

    @FXML
    private Button visualizzaConferenza;
//    private NavigationController navigationController = NavigationController.getInstance();
//
//    @FXML
//    void creaConferenzaOnAction(ActionEvent event) {
//        loadScene("/View/FXML/Registrazione.fxml", creaConferenzaButton);
//    }

    @FXML
    void creaConferenzaOnAction(ActionEvent event) {
        try {
            NavigationController.getInstance().setStage((Stage) creaConferenzaButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("/View/FXML/CreaConferenza.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gestisciConferenzaOnAction(ActionEvent event) {
        try {
            NavigationController.getInstance().setStage((Stage) gestisciConferenzaButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("/View/FXML/GestisciConferenza.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void visualizzaConferenzaOnAction(ActionEvent event) {
        try {
            NavigationController.getInstance().setStage((Stage) visualizzaConferenza.getScene().getWindow());
            NavigationController.getInstance().loadScene("/View/FXML/VisualizzaConferenza.fxml");
        } catch (Exception e) {
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
