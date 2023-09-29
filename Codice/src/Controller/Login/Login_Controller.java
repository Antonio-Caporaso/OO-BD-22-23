package Controller.Login;

import Controller.Landing_Controller;
import Exceptions.BlankFieldException;
import Interfaces.FormChecker;
import Model.DAO.UtenteDAO;
import Model.Entities.Utente;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login_Controller implements Initializable, FormChecker {
    @FXML
    private AnchorPane anchorPanel;
    @FXML
    private Label errorLabel;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordTextField;
    private Utente user;
    @FXML
    private TextField usernameTextField;

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank())
            throw new BlankFieldException();
    }

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defineEventHandlerForAnchorPane();
    }

    private void changeToLandingWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Landing.fxml"));
        Landing_Controller controller = new Landing_Controller(user);
        loader.setController(controller);
        Parent root = loader.load();
        Scene landingScene = new Scene(root);
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(landingScene);
    }

    private void defineEventHandlerForAnchorPane() {
        anchorPanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    checkFieldsAreBlank();
                    validateLogin();
                } catch (BlankFieldException e) {
                    errorLabel.setText("Inserire nome utente e password");
                }
            }
        });
    }

    private void validateLogin() {
        String username = usernameTextField.getText();
        String pwd = passwordTextField.getText();
        UtenteDAO userdao = new UtenteDAO();
        try {
            user = userdao.retrieveUtentebyUsername(username);
            if (pwd.equals(user.getPassword()))
                changeToLandingWindow();
            else
                errorLabel.setText("Password errata");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NullPointerException e) {
            errorLabel.setText("Utente non presente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loginButtonOnAction(ActionEvent event) {
        try {
            checkFieldsAreBlank();
            validateLogin();
        } catch (BlankFieldException e) {
            errorLabel.setText("Inserire nome utente e password");
        }
    }

    @FXML
    void registratiButtonOnAction(ActionEvent event) {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Login/Registrazione.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
