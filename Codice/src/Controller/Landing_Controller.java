package Controller;

import Controller.Create.AddConference_Controller;
import Controller.Edit.ModificaConferenze_Controller;
import Controller.Login.Login_Controller;
import Controller.View.VisualizzaConferenze_Controller;
import Controller.Stats.VisualizzaStatistiche_Controller;
import Model.Entities.Utente;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Landing_Controller implements Initializable {
    @FXML
    private Button LogoutButton;
    @FXML
    private Button backToLandingButton;
    @FXML
    private SubScene subscene;
    @FXML
    private Label welcomeLabel;
    public Utente user;

    public Landing_Controller(Utente user){
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setWelcomeLabel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Welcome.fxml"));
        try {
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setWelcomeLabel() {
            welcomeLabel.setText("Welcome " + user.getUsername() + "!");
    }

    @FXML
    void creaConferenzaOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/AddConference.fxml"));
            AddConference_Controller controller = new AddConference_Controller(subscene,user);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Edit/ModificaConferenze.fxml"));
            ModificaConferenze_Controller controller = new ModificaConferenze_Controller(user,subscene);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void visualizzaConferenzaOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/View/VisualizzaConferenze.fxml"));
            VisualizzaConferenze_Controller controller = new VisualizzaConferenze_Controller(subscene);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void visualizzaStatisticheOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Stats/VisualizzaStatistiche.fxml"));
            VisualizzaStatistiche_Controller controller = new VisualizzaStatistiche_Controller();
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logoutButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Login/Login.fxml"));
            Parent root = loader.load();
            Scene login = new Scene(root);
            Login_Controller controller = loader.getController();
            controller.setUser(null);
            Stage stage = (Stage) LogoutButton.getScene().getWindow();
            stage.setScene(login);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backToLandingButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Landing.fxml"));
            Landing_Controller controller = new Landing_Controller(user);
            loader.setController(controller);
            Parent root = loader.load();
            Scene landingScene = new Scene(root);
            Stage stage = (Stage) backToLandingButton.getScene().getWindow();
            stage.setScene(landingScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
