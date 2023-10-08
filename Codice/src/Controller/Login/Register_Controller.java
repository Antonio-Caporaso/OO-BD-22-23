package Controller.Login;

import Interfaces.FormChecker;
import Exceptions.BlankFieldException;
import Exceptions.PasswordMismatchException;
import Exceptions.UtentePresenteException;
import Model.DAO.UtenteDAO;
import Model.Entities.Utente;
import Model.Entities.Ente;
import Model.Utilities.Enti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Register_Controller implements Initializable, FormChecker {
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
    @FXML
    private ChoiceBox<Ente> istituzioneChoice;
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
        if (emailTextField.getText().isBlank()
                || nomeTextField.getText().isBlank()
                || usernameTextField.getText().isBlank()
                || cognomeTextField.getText().isBlank()
                || passwordTextField.getText().isBlank()
                || istituzioneChoice.getValue()==null)
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
        try{
            Stage stage = (Stage) backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Login/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
        }catch (IOException e){
            e.printStackTrace();
        }
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
            Stage stage = (Stage) backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Login/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
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
