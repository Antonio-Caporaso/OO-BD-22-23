package Controller;

import Model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LandingController implements Initializable{

    public Utente user;
    @FXML
    private Button LogoutButton;
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
    private Label welcomeLabel;

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
        setWelcomeLabel();
    }

    @FXML
    void creaConferenzaOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/CreaConferenza.fxml"));
            AddConferenceController controller = new AddConferenceController();
            controller.setUser(user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
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
    void logoutButtonOnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Login.fxml"));
            Parent root = loader.load();
            Scene login = new Scene(root);
            LoginController controller = loader.getController();
            controller.setUser(null);
            Stage stage = (Stage) LogoutButton.getScene().getWindow();
            stage.setScene(login);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setWelcomeLabel() {
        if(user != null)
            welcomeLabel.setText("Welcome " + user.getUsername() + "!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
