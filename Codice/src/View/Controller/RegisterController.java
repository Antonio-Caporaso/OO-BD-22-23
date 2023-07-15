package View.Controller;

import Exceptions.BlankFieldException;
import Exceptions.PasswordMismatchException;
import Exceptions.UtentePresenteException;
import Persistence.DAO.UtenteDAO;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Ente;
import Utilities.Enti;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable, FormChecker {
    @FXML
    private Button backButton;
    @FXML
    private TextField cognomeTextField;
    @FXML
    private PasswordField confermaPasswordTextField;
    @FXML
    private TextField emailTextField;
    private Enti enti = new Enti();
    @FXML
    private Label errorLabel;
    private File file;
    @FXML
    private ChoiceBox<Ente> istituzioneChoice;
    private Media media;
    private MediaPlayer mediaPlayer;
    @FXML
    private MediaView mediaView;
    @FXML
    private TextField nomeTextField;
    @FXML
    private PasswordField passwordTextField;
    private String[] titoli = {"Dottore", "Dottoressa", "Professore", "Professoressa", "Assistente", "Ricercatore", "Ricercatrice", "Ingegnere"};
    @FXML
    private ChoiceBox<String> titoloChoiceBox;
    @FXML
    private TextField usernameTextField;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titoloChoiceBox.getItems().addAll(titoli);
        setIstituzioni();
    }

    private Utente getUserDetails() {
        String titoloUtente = titoloChoiceBox.getValue();
        String emailUtente = emailTextField.getText();
        String nomeUtente = nomeTextField.getText();
        String usernameUtente = usernameTextField.getText();
        String cognomeUtente = cognomeTextField.getText();
        String passwordUtente = passwordTextField.getText();
        Ente istituzioneUtente = istituzioneChoice.getSelectionModel().getSelectedItem();
        return new Utente(titoloUtente, usernameUtente, passwordUtente, nomeUtente, cognomeUtente, emailUtente, istituzioneUtente);
    }

    private void goToLoginWindow() {
        NavigationController.getInstance().setStage((Stage) backButton.getScene().getWindow());
        NavigationController.getInstance().loadScene("../FXML/Login.fxml");
    }

    private void passwordMatcher() throws PasswordMismatchException {
        Utente user = this.getUserDetails();
        String conferma = confermaPasswordTextField.getText();
        if (!conferma.equals(user.getPassword())) {
            throw new PasswordMismatchException();
        }
    }

    private void setIstituzioni() {
        enti.loadEnti();
        istituzioneChoice.setItems(enti.getEnti());
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        try {
            NavigationController.getInstance().setStage((Stage) backButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("../FXML/Login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void registraUtente() throws UtentePresenteException {
        UtenteDAO dao = new UtenteDAO();
        Utente newUser = getUserDetails();
        try {
            dao.saveUtente(newUser);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void confirmButtonOnAction(ActionEvent event) {
        try {
            checkFieldsAreBlank();
            passwordMatcher();
            registraUtente();
            goToLoginWindow();
        } catch (BlankFieldException e) {
            errorLabel.setText(e.getMessage());
        } catch (PasswordMismatchException e) {
            errorLabel.setText(e.getMessage());
        } catch (UtentePresenteException e) {
            errorLabel.setText(e.getMessage());
        }
    }
}
