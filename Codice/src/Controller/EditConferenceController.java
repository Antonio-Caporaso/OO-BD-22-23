package Controller;

import Model.Conferenze.Conferenza;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class EditConferenceController implements Initializable {
    private Conferenza conferenza;
    private SubScene subscene;
    @FXML
    private Button annullaButton;
    @FXML
    private Label budgetLabel;
    @FXML
    private Button confermaButton;
    @FXML
    private Label dataFineLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private Label descrizioneLabel;
    @FXML
    private Button editDetailsButton;
    @FXML
    private Button editEntiButton;
    @FXML
    private Button editSessionsButton;
    @FXML
    private Button editSponsorshipsButton;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label sedeLabel;
    @FXML
    private Label titleLabel;
    @FXML
    void annullaButtonOnAction(ActionEvent event) {

    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) {

    }

    @FXML
    void editDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void editEntiOnAction(ActionEvent event) {

    }

    @FXML
    void editSessionsOnAction(ActionEvent event) {

    }

    @FXML
    void editSponsorshipOnAction(ActionEvent event) {

    }

    public SubScene getsubscene() {
        return subscene;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTitleLabel();
        nomeLabel.setText(conferenza.getNome());
        descrizioneLabel.setText(conferenza.getDescrizione());
        budgetLabel.setText(Float.toString(conferenza.getBudget()));
        dataInizioLabel.setText(conferenza.getDataInizio().toString());
        dataFineLabel.setText(conferenza.getDataFine().toString());
        sedeLabel.setText(conferenza.getSede().toString());
    }
    public void setTitleLabel(){
        if(conferenza!=null)
            titleLabel.setText("Modifica della conferenza: "+ conferenza.getNome());
    }
}
