package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LandingController {
    @FXML
    private Button backButton;
@FXML
    void backButtonOnAction(ActionEvent event) {

        try {
            /*Si chiama l'evento di navigazione passando l'FXML della scena di login.
            Si recupera la finestra principale corrente utilizzando il metodo getScene().getWindow()
             e la passa all'istanza della classe di navigazione utilizzando il metodo setStage().*/
            NavigationController.getInstance().setStage((Stage) backButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("/View/FXML/Registrazione.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
