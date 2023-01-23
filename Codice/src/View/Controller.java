package View;
/*
 * Controller Class: contiene la logica dell'intera GUI
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Label errorLabel;
    @FXML // fx:id="accediBtn"
    private Button accediBtn; // Value injected by FXMLLoader

    @FXML // fx:id="usernameTxt"
    private TextField usernameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="usernametxt1"
    private PasswordField passwordTxt; // Value injected by FXMLLoader

    @FXML
    void validate(ActionEvent event) {
        if(usernameTxt.getText().isBlank() || passwordTxt.getText().isBlank())
            errorLabel.setText("Inserisci nome utente e password");
        else{
            //validateLogin();
        }
    }

    @FXML
    void doAnnulla(ActionEvent event) {
        usernameTxt.setText("");
        passwordTxt.setText("");
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert accediBtn != null : "fx:id=\"accediBtn\" was not injected: check your FXML file 'Login.fxml'.";
        assert usernameTxt != null : "fx:id=\"usernameTxt\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordTxt != null : "fx:id=\"passwordTxt\" was not injected: check your FXML file 'Login.fxml'.";

    }

}
