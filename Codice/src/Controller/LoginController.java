package Controller;
/*
 * Controller Class Login
 */

import DAO.UtenteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class LoginController {
    //private Stage primaryStage;
    @FXML
    private Label errorLabel;
    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader
    @FXML // fx:id="loginButton"
    private Button registratiButton; // Value injected by FXMLLoader
    @FXML // fx:id="usernameTextField"
    private TextField usernameTextField; // Value injected by FXMLLoader
    @FXML // fx:id="passwordTextField"
    private PasswordField passwordTextField; // Value injected by FXMLLoader
    private UtenteDAO userdao;
    @FXML
    void loginButtonOnAction(ActionEvent event) {
        if(usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank())
            errorLabel.setText("Inserisci nome utente e password");
        else{
            validateLogin(event);
        }
    }
    @FXML
    void registratiButtonOnAction(ActionEvent event){
        try{
            NavigationController.getInstance().setStage((Stage) loginButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("/View/FXML/Registrazione.fxml");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void validateLogin(ActionEvent event) {
        String username = usernameTextField.getText();
        String pwd = passwordTextField.getText();
        userdao = new UtenteDAO();
        if(pwd.equals(userdao.getPasswordByUsername(username))){
            NavigationController.getInstance().setStage((Stage) loginButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("../View/FXML/Landing.fxml");
        }else
            errorLabel.setText("Password errata");
    }
   /*public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }*/
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'Login.fxml'.";
        assert registratiButton != null : "fx:id=\"registratiButton\" was not injected: check your FXML file 'Login.fxml'.";
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'Login.fxml'.";

    }
}
