package Controller;

import Presenter.SedeModel;
import Model.Utente;
import Model.Conferenze.Sede;
import Model.organizzazione.Ente;
import Presenter.EnteModel;
import Model.organizzazione.Sponsor;
import Presenter.SponsorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CreaConferenzaController implements Initializable{

    private Utente user;
    private SedeModel sedi = new SedeModel();
    private EnteModel enti = new EnteModel();
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
    private ComboBox<Ente> entiSelection;
    @FXML
    private TextField nomeConferenzaTF;
    @FXML
    private TextArea descrizioneTextArea;
    @FXML
    private ChoiceBox<Sede> sedeSelection;
    @FXML
    private ComboBox<Sponsor> sponsorSelection;

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }
    @FXML
    void annullaOnAction(ActionEvent event) {
        nomeConferenzaTF.setText("");
        entiSelection.setValue(null);
        entiSelection.hide();
        sedeSelection.setValue(null);
        sedeSelection.hide();
        descrizioneTextArea.setText("");
        dataFineDP.setValue(null);
        dataFineDP.hide();
        dataInizioDP.setValue(null);
        dataInizioDP.hide();
        sponsorSelection.setValue(null);
        sponsorSelection.hide();
    }

    @FXML
    public void creaButtonOnAction(ActionEvent event){

    }
    @FXML
    public void showSedi(MouseEvent event){
        sedeSelection.show();
    }
    @FXML
    public void showEnti(ActionEvent event){
        entiSelection.show();
    }
    @FXML
    public void showSponsor(ActionEvent event){
        sponsorSelection.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sedi.loadSedi();
        enti.loadEnti();
        sponsor.loadSponsor();
        sedeSelection.getItems().addAll(sedi.getSedi());
        entiSelection.getItems().addAll(enti.getEnti());
        sponsorSelection.getItems().addAll(sponsor.getSponsors());
    }
}

