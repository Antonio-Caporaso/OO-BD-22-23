package Controller;
import DAO.UtenteDAO;
import Model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    //private Stage primaryStage;
    @FXML
    private Label errorLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button registratiButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private MediaView mediaView;
    private Media media;
    private File file;
    private MediaPlayer mediaPlayer;

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
            NavigationController.getInstance().loadScene("../View/FXML/Registrazione.fxml");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void validateLogin(ActionEvent event) {
        String username = usernameTextField.getText();
        String pwd = passwordTextField.getText();
        UtenteDAO userdao = new UtenteDAO();
        if(pwd.equals(userdao.getPasswordByUsername(username))){
            NavigationController.getInstance().setStage((Stage) loginButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("../View/FXML/Landing.fxml");
        }else
            errorLabel.setText("Password errata");
    }
    @FXML
    void initialize() {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'Login.fxml'.";
        assert registratiButton != null : "fx:id=\"registratiButton\" was not injected: check your FXML file 'Login.fxml'.";
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'Login.fxml'.";

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        file = new File ("src/View/Resources/Scientists.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }
}
