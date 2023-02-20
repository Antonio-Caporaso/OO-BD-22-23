package Controller;

import DAO.ConferenzaDao;
import Model.Conferenze.Conferenza;
import Model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddConferenceController implements Initializable{
    private Utente user;
    @FXML
    private Button annullaButton;
    @FXML
    private DatePicker dataFineDP;
    @FXML
    private DatePicker dataInizioDP;
    @FXML
    private Button creaButton;
    @FXML
    private TextField nomeConferenzaTF;
    @FXML
    private TextField budgetTextField;
    @FXML
    private TextArea descrizioneTextArea;

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }
    @FXML
    void annullaOnAction(ActionEvent event) {
        dataFineDP.setValue(null);
        dataFineDP.hide();
        dataInizioDP.setValue(null);
        dataInizioDP.hide();
        nomeConferenzaTF.setText("");
        budgetTextField.setText("");
        descrizioneTextArea.setText("");
    }

    @FXML
    public void creaButtonOnAction(ActionEvent event){
        String nome = nomeConferenzaTF.getText();
        float budget = Float.parseFloat(budgetTextField.getText());
        String descrizione = descrizioneTextArea.getText();
        LocalDate dataIselected = dataInizioDP.getValue();
        LocalDate dataFselected = dataFineDP.getValue();
        Date dataI = java.sql.Date.valueOf(dataIselected);
        Date dataF = java.sql.Date.valueOf(dataFselected);
        Conferenza c = new Conferenza(nome, dataI, dataF,descrizione,budget,user);
        ConferenzaDao dao = new ConferenzaDao();
        try {
            dao.saveConferenza(c);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

