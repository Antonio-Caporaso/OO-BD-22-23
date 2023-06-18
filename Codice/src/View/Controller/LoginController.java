package View.Controller;
import Persistence.DAO.UtenteDAO;
import Exceptions.BlankFieldException;
import Persistence.Entities.Utente;
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
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, FormChecker {
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
    private Utente user;

    @FXML
    void loginButtonOnAction(ActionEvent event) {
        try{
        checkFieldsAreBlank();
        validateLogin();
        }catch (BlankFieldException e){
            errorLabel.setText("Inserire nome utente e password");
        }
    }
    @FXML
    void registratiButtonOnAction(ActionEvent event){
        try{
            NavigationController.getInstance().setStage((Stage) loginButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("../FXML/Registrazione.fxml");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void validateLogin() {
        String username = usernameTextField.getText();
        String pwd = passwordTextField.getText();
        UtenteDAO userdao = new UtenteDAO();
        try {
            user = userdao.retrieveUtentebyUsername(username);
            if(pwd.equals(user.getPassword())){
                try {
                    changeToLandingWindow();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else
                errorLabel.setText("Password errata");
        }catch (Exception e){
            errorLabel.setText("Utente non presente");
        }

    }

    private void changeToLandingWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Landing.fxml"));
        LandingController controller = new LandingController();
        loader.setController(controller);
        Parent root = loader.load();
        controller.setUser(user);
        Scene landingScene = new Scene(root);
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(landingScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMediaPlayer();
    }

    private void setMediaPlayer() {
        try{
            // file = new File("Codice/src/View/Resources/Scientists.mp4");
            file = new File ("src/View/Resources/Scientists.mp4");
            media = new Media(file.toURI().toString());mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }catch (MediaException e){
            System.out.println(e.getMessage());
        }
    }

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if(usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank())
            throw new BlankFieldException();
    }
}
