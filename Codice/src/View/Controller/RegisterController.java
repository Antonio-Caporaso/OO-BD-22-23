package View.Controller;

import Persistence.DAO.UtenteDAO;
import Exceptions.BlankFieldException;
import Exceptions.PasswordMismatchException;
import Exceptions.UtentePresenteException;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Ente;
import Utilities.Enti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable, FormChecker{
    @FXML
    private Button backButton;
    @FXML
    private TextField cognomeTextField;
    @FXML
    private PasswordField confermaPasswordTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField nomeTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ChoiceBox<String> titoloChoiceBox;

    @FXML
    private TextField usernameTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private ChoiceBox<Ente> istituzioneChoice;
    @FXML
    private MediaView mediaView;
    private Enti enti = new Enti();
    private Media media;
    private File file;
    private MediaPlayer mediaPlayer;
    private String[]titoli={"Dottore","Dottoressa","Professore","Professoressa","Assistente","Ricercatore","Ricercatrice","Ingegnere"};
    @FXML
    void backButtonOnAction(ActionEvent event) {
        try {
            NavigationController.getInstance().setStage((Stage) backButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("../FXML/Login.fxml");
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
        Ente istituzioneUtente = istituzioneChoice.getSelectionModel().getSelectedItem();
        return new Utente(titoloUtente,usernameUtente,passwordUtente,nomeUtente,cognomeUtente,emailUtente,istituzioneUtente);
    }
    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (titoloChoiceBox.getItems().isEmpty()
                || emailTextField.getText().isBlank()
                || nomeTextField.getText().isBlank()
                || usernameTextField.getText().isBlank()
                || cognomeTextField.getText().isBlank()
                || passwordTextField.getText().isBlank())
            throw new BlankFieldException();
    }

    private void passwordMatcher() throws PasswordMismatchException {
        Utente user = this.getUserDetails();
        String conferma = confermaPasswordTextField.getText();
        if(!conferma.equals(user.getPassword())){
            throw new PasswordMismatchException();
        }
    }

    void registraUtente() throws UtentePresenteException {
            UtenteDAO dao = new UtenteDAO();
            Utente newUser = getUserDetails();
            try{
                dao.saveUtente(newUser);
            }catch (UtentePresenteException e){
                throw e;
            }
    }
    @FXML
    void confirmButtonOnAction(ActionEvent event){
        try{
            checkFieldsAreBlank();
            passwordMatcher();
            registraUtente();
        } catch (BlankFieldException e) {
                errorLabel.setText(e.getMessage());
        } catch (PasswordMismatchException e) {
            errorLabel.setText(e.getMessage());
        }catch (UtentePresenteException e){
            errorLabel.setText(e.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titoloChoiceBox.getItems().addAll(titoli);
        setIstituzioni();
        setMediaPlayer();
    }

    private void setIstituzioni() {
        enti.loadEnti();
        istituzioneChoice.setItems(enti.getEnti());
    }

    private void setMediaPlayer() {
        try{
        //         file = new File ("Codice/src/View/Resources/Scientists1.mp4");
        file = new File ("src/View/Resources/Scientists1.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }catch (MediaException e){
        System.out.println(e.getMessage());
    }
    }
}
