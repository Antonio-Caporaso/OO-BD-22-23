package Controller;

import DAO.ConferenzaDao;
import Model.Conferenze.Conferenza;
import Presenter.PlaceModel;
import Model.Utente;
import Model.Conferenze.Sede;
import Model.organizzazione.Ente;
import Presenter.OrganizationModel;
import Model.organizzazione.Sponsor;
import Presenter.SponsorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class AddConferenceController implements Initializable{

    private Utente user;
    private PlaceModel sedi = new PlaceModel();
    private OrganizationModel enti = new OrganizationModel();
    private SponsorModel sponsor = new SponsorModel();
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
        LocalDate data1 = dataInizioDP.getValue();
        LocalDate data2 = dataFineDP.getValue();
        Date dataI = java.sql.Date.valueOf(data1);
        Date dataF = java.sql.Date.valueOf(data2);
        Conferenza c = new Conferenza(nome, dataI, dataF,descrizione,budget, this.user);
        ConferenzaDao dao = new ConferenzaDao();
        dao.saveConferenza(c);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sedi.loadSedi();
        enti.loadEnti();
        sponsor.loadSponsor();
    }
}

