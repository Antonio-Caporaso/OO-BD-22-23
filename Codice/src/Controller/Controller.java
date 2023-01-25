package Controller;
/*
 * Controller Class: contiene la logica dell'intera GUI
 */
import java.net.URL;
import java.security.spec.ECField;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class Controller {
    private Stage primaryStage;
    private Scene loginScene;
    private Scene mainScene;
    private Parent root;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML
    private Button annullaBtn;
    @FXML
    private Label errorLabel;
    @FXML // fx:id="accediBtn"
    private Button accediBtn; // Value injected by FXMLLoader
    @FXML // fx:id="usernameTxt"
    private TextField usernameTxt; // Value injected by FXMLLoader
    @FXML // fx:id="passwordTxt"
    private PasswordField passwordTxt; // Value injected by FXMLLoader
    @FXML
    private Button backButton1;
    @FXML
    void doBackToLoginScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Login.fxml"));
            root = loader.load();
            primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginScene = new Scene(root);
            primaryStage.setScene(loginScene);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void validate(ActionEvent event) {
        if(usernameTxt.getText().isBlank() || passwordTxt.getText().isBlank())
            setErrorLabelOnBlankPasswordAndUsernameFields();
        else{
            validateLogin(event);
        }
    }

    private void validateLogin(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Demo2Finestra.fxml"));
            root=loader.load();
            primaryStage=(Stage)((Node) event.getSource()).getScene().getWindow();
            mainScene = new Scene(root);
            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setErrorLabelOnBlankPasswordAndUsernameFields() {
        errorLabel.setText("Inserisci nome utente e password");
    }

    @FXML
    void doAnnulla(ActionEvent event) {
        usernameTxt.setText("");
        passwordTxt.setText("");
        errorLabel.setText("");
    }
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert accediBtn != null : "fx:id=\"accediBtn\" was not injected: check your FXML file 'Login.fxml'.";
        assert usernameTxt != null : "fx:id=\"usernameTxt\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordTxt != null : "fx:id=\"passwordTxt\" was not injected: check your FXML file 'Login.fxml'.";

    }
}
