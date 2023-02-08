package Controller;
import DAO.UtenteDAO;
import Model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
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
    private Button skipButton;
    @FXML
    private MediaView mediaView;
    private Media media;
    private File file;
    private MediaPlayer mediaPlayer;
    public Utente user;

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
        user = userdao.retrieveUtentebyUsername(username);
        if(pwd.equals(user.getPassword())){
            try {
            //NavigationController.getInstance().setStage((Stage) loginButton.getScene().getWindow());
            //NavigationController.getInstance().loadScene("../View/FXML/Landing.fxml");
            changeToLandingWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else
            errorLabel.setText("Password errata");
    }

    private void changeToLandingWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Landing.fxml"));
        Parent root = loader.load();
        Scene landingScene = new Scene(root);

        LandingController controller = loader.getController();
        controller.initData(user);

        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(landingScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //file = new File("Codice/src/View/Resources/Scientists.mp4");
        file = new File ("src/View/Resources/Scientists.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }
    @FXML
    private void skipButtonOnAction(ActionEvent event){
        try{
            NavigationController.getInstance().setStage((Stage) loginButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("../View/FXML/Landing.fxml");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
