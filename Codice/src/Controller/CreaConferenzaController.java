package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreaConferenzaController {

    @FXML
    private TitledPane DettagliTitle;

    @FXML
    private TitledPane PromotoriTitle;

    @FXML
    private TitledPane SedeTitle;

    @FXML
    private ImageView image;

    @FXML
    void DettagliTitleOnAction(MouseEvent event){
        Image img = new Image("View/Resources/conference.png");
        image.setImage(img);
    }
    @FXML
    void PromotoriTitleOnAction(MouseEvent event) {
        Image img = new Image("View/Resources/meeting.png");
        image.setImage(img);
    }

    @FXML
    void SedeTitleOnAction(MouseEvent event) {
        Image img = new Image("View/Resources/city.png");
        image.setImage(img);
    }

    @FXML
    void initialize() {
        assert DettagliTitle != null : "fx:id=\"DettagliTitle\" was not injected: check your FXML file 'CreaConferenza.fxml'.";
        assert PromotoriTitle != null : "fx:id=\"PromotoriTitle\" was not injected: check your FXML file 'CreaConferenza.fxml'.";
        assert SedeTitle != null : "fx:id=\"SedeTitle\" was not injected: check your FXML file 'CreaConferenza.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'CreaConferenza.fxml'.";
    }
}