package Controller;

import DAO.UtenteDAO;
import Exceptions.BlankFieldException;
import Exceptions.UtentePresenteException;
import Model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrazioneController implements Initializable, FormChecker{
    @FXML
    private Button backButton;
    @FXML
    private Label cognomeLabel;
    @FXML
    private TextField cognomeTextField;
    @FXML
    private Label confermaPasswordLabel;
    @FXML
    private PasswordField confermaPasswordTextField;
    @FXML
    private Button confirmButton;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label nomeLabel;
    @FXML
    private TextField nomeTextField;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ChoiceBox<String> titoloChoiceBox;
    @FXML
    private Label titoloLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private MediaView mediaView;
    private Media media;
    private File file;
    private MediaPlayer mediaPlayer;
    private String[]titoli={"Dr.","Dott.","Dott.ssa","Prof.","Prof.ssa","Ing.","Sig","Sig.ra","Altro"};
    @FXML
    void backButtonOnAction(ActionEvent event) {
        try {
            /*Si chiama l'evento di navigazione passando l'FXML della scena di login.
            Si recupera la finestra principale corrente utilizzando il metodo getScene().getWindow()
             e la passa all'istanza della classe di navigazione utilizzando il metodo setStage().*/
            NavigationController.getInstance().setStage((Stage) backButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("/View/FXML/Login.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private Utente getUserDetails(){
        String titoloUtente = titoloChoiceBox.getValue();
        String emailUtente = emailTextField.getText();
        String nomeUtente = nomeTextField.getText();
        String usernameUtente = usernameTextField.getText();
        String cognomeUtente = cognomeTextField.getText();
        String passwordUtente = passwordTextField.getText();
        return new Utente(nomeUtente,cognomeUtente,titoloUtente,usernameUtente,passwordUtente,emailUtente);
    }

    @Override
    public boolean textFieldsAreBlank() {
        return titoloChoiceBox.getItems().isEmpty()
                || emailTextField.getText().isBlank()
                || nomeTextField.getText().isBlank()
                || usernameTextField.getText().isBlank()
                || cognomeTextField.getText().isBlank()
                || passwordTextField.getText().isBlank();
    }

    private boolean passwordMatcher(){
        Utente user = this.getUserDetails();
        String conferma = confermaPasswordTextField.getText();
        return conferma.equals(user.getPassword());
    }

    void registraUtente() throws UtentePresenteException {
        if(passwordMatcher()){
            UtenteDAO dao = new UtenteDAO();
            Utente newUser = getUserDetails();
            try{
                dao.saveUtente(newUser);
            }catch (UtentePresenteException e){
                throw e;
            }
        }else {
            //Settare label che notifica che notifica il fatto che le password non corrispondono
        }
    }
    @FXML
    void confirmButtonOnAction(ActionEvent event) {
        if(!textFieldsAreBlank()) {
            try {
                registraUtente();
                NavigationController.getInstance().setStage((Stage) confirmButton.getScene().getWindow());
                NavigationController.getInstance().loadScene("/View/FXML/Landing.fxml");
            } catch (UtentePresenteException e) {
                // Settare label che notifica l'errore
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titoloChoiceBox.getItems().addAll(titoli);
        file = new File ("codice/src/View/Resources/Scientists1.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }
}
