package View;
/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Label errorLabel;
    @FXML // fx:id="accedibtn"
    private Button accedibtn; // Value injected by FXMLLoader

    @FXML // fx:id="usernametxt"
    private TextField usernametxt; // Value injected by FXMLLoader

    @FXML // fx:id="usernametxt1"
    private TextField usernametxt1; // Value injected by FXMLLoader

    @FXML
    void validate(ActionEvent event) {
        if(usernametxt.getText().isBlank() || usernametxt1.getText().isBlank())
            errorLabel.setText("Inserisci nome utente e password");
        else{
            //validateLogin();
        }
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert accedibtn != null : "fx:id=\"accedibtn\" was not injected: check your FXML file 'Login.fxml'.";
        assert usernametxt != null : "fx:id=\"usernametxt\" was not injected: check your FXML file 'Login.fxml'.";
        assert usernametxt1 != null : "fx:id=\"usernametxt1\" was not injected: check your FXML file 'Login.fxml'.";

    }

}
