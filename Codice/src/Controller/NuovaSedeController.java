/**
 * Sample Skeleton for 'NuovaSede.fxml' Controller Class
 */

package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.SedeDao;
import Exceptions.BlankFieldException;
import Model.conferenza.Sede;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NuovaSedeController implements FormChecker{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="ConfermaButton"
    private Button ConfermaButton; // Value injected by FXMLLoader

    @FXML // fx:id="annullaButton"
    private Button annullaButton; // Value injected by FXMLLoader

    @FXML // fx:id="capSedeTF"
    private TextField capSedeTF; // Value injected by FXMLLoader

    @FXML // fx:id="citySedeTF"
    private TextField citySedeTF; // Value injected by FXMLLoader

    @FXML // fx:id="costoAffittoTF"
    private TextField costoAffittoTF; // Value injected by FXMLLoader

    @FXML // fx:id="indirizzoSedeTF"
    private TextField indirizzoSedeTF; // Value injected by FXMLLoader

    @FXML // fx:id="nomeSedeTF"
    private TextField nomeSedeTF; // Value injected by FXMLLoader
    @FXML
    private Label errorLabel;

    @FXML
    void annullaButtonOnAction(ActionEvent event) {
        errorLabel.setText("");
        nomeSedeTF.setText("");
        indirizzoSedeTF.setText("");
        costoAffittoTF.setText("");
        citySedeTF.setText("");
        capSedeTF.setText("");
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) {
        try {
            textFieldsAreBlank();
            SedeDao sedeDao = new SedeDao();
            Sede sede = new Sede();
            sede.setNomeSede(nomeSedeTF.getText());
            sede.setCity(citySedeTF.getText());
            sede.setCap(capSedeTF.getText());
            sede.setCostoAffitto(Double.parseDouble(costoAffittoTF.getText()));
            sedeDao.saveSede(sede);
        }catch (BlankFieldException e) {
            errorLabel.setText(e.getMessage());
        }
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert ConfermaButton != null : "fx:id=\"ConfermaButton\" was not injected: check your FXML file 'NuovaSede.fxml'.";
        assert annullaButton != null : "fx:id=\"annullaButton\" was not injected: check your FXML file 'NuovaSede.fxml'.";
        assert capSedeTF != null : "fx:id=\"capSedeTF\" was not injected: check your FXML file 'NuovaSede.fxml'.";
        assert citySedeTF != null : "fx:id=\"citySedeTF\" was not injected: check your FXML file 'NuovaSede.fxml'.";
        assert costoAffittoTF != null : "fx:id=\"costoAffittoTF\" was not injected: check your FXML file 'NuovaSede.fxml'.";
        assert indirizzoSedeTF != null : "fx:id=\"indirizzoSedeTF\" was not injected: check your FXML file 'NuovaSede.fxml'.";
        assert nomeSedeTF != null : "fx:id=\"nomeSedeTF\" was not injected: check your FXML file 'NuovaSede.fxml'.";
    }

    @Override
    public void textFieldsAreBlank() throws BlankFieldException {
        if (nomeSedeTF.getText().isBlank() || citySedeTF.getText().isBlank()
                || capSedeTF.getText().isBlank()
                || indirizzoSedeTF.getText().isBlank()
                || costoAffittoTF.getText().isBlank())
            throw new BlankFieldException();
    }
}
