package Controller;

import DAO.UtenteDAO;
import Exceptions.UtentePresenteException;
import Model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrazioneController implements Initializable{

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
    void registraUtente() throws UtentePresenteException {

        String titoloUtente = titoloChoiceBox.getValue();
        String emailUtente = emailTextField.getText();
        String nomeUtente = nomeTextField.getText();
        String usernameUtente = usernameTextField.getText();
        String cognomeUtente = cognomeTextField.getText();
        String passwordUtente = passwordTextField.getText();
        String conferma = confermaPasswordTextField.getText();
        if(passwordUtente.equals(conferma)){
            UtenteDAO dao = new UtenteDAO();
            Utente newUser = new Utente(nomeUtente,cognomeUtente,titoloUtente,usernameUtente,passwordUtente,emailUtente);
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

        try {
            /*Si chiama l'evento di navigazione passando l'FXML della scena di login.
            Si recupera la finestra principale corrente utilizzando il metodo getScene().getWindow()
             e la passa all'istanza della classe di navigazione utilizzando il metodo setStage().*/
            registraUtente();
            NavigationController.getInstance().setStage((Stage) confirmButton.getScene().getWindow());
            NavigationController.getInstance().loadScene("/View/FXML/Landing.fxml");
        }catch (UtentePresenteException e){
            // Settare label che notifica l'errore
        }
    }
    //serve per inizializzare il controller dopo che l'elemento di root è stato inizializzato
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titoloChoiceBox.getItems().addAll(titoli);
    }
}
