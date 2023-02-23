package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;

public class AddConferenceNextController {
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;
    @FXML
    private SubScene subscene;
    @FXML
    void backButtonOnAction(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/CreaConferenza.fxml"));
            AddConferenceController controller = new AddConferenceController();
            controller.setSubscene(subscene);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void nextButtonOnAction(ActionEvent event) {
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
}
