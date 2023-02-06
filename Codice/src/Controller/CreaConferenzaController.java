package Controller;

import DAO.ConferenzaDao;
import DAO.SedeDao;
import Model.conferenza.Sede;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

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
    private ChoiceBox<String> sediPicker;
    private SedeDao sededao;
    @FXML
    private Button newSedeButton;
    private ConferenzaDao conferenzaDAO;
    @FXML
    private SubScene newSedeScene;
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
    void newSedeButtonOnAction(ActionEvent event){
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/NuovaSede.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newSedeScene.setRoot(loader);
    }
    @FXML
    void initialize() {
        sededao = new SedeDao();
        assert DettagliTitle != null : "fx:id=\"DettagliTitle\" was not injected: check your FXML file 'CreaConferenza.fxml'.";
        assert PromotoriTitle != null : "fx:id=\"PromotoriTitle\" was not injected: check your FXML file 'CreaConferenza.fxml'.";
        assert SedeTitle != null : "fx:id=\"SedeTitle\" was not injected: check your FXML file 'CreaConferenza.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'CreaConferenza.fxml'.";
        sediPicker.getItems().setAll(sededao.retrieveNomeSedi());
    }
}