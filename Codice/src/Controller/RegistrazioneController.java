package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegistrazioneController {
    @FXML
    private Button backButton1;
    @FXML
    void backButtonOnAction(ActionEvent event) {

        try {
            /*Si chiama l'evento di navigazione passando l'FXML della scena di login.
            Si recupera la finestra principale corrente utilizzando il metodo getScene().getWindow()
             e la passa all'istanza della classe di navigazione utilizzando il metodo setStage().*/
            NavigationController.getInstance().setStage((Stage) backButton1.getScene().getWindow());
            NavigationController.getInstance().loadScene("/View/FXML/Login.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
