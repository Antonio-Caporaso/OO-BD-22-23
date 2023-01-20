package app.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button registerButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    public void loginButtonOnAction(ActionEvent event) throws IOException {


        if (usernameTextField.getText().isBlank() && passwordTextField.getText().isBlank()) {

            loginMessageLabel.setText("Please enter username and password");

        } else {
            validateLogin();
        }
    }

    public void registrazioneButtonOnAction (ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Registrazione.fxml"));
        root=loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void validateLogin() {
    }
}
