package Controller;

import DAO.ConferenzaDao;
import Model.Conferenze.Conferenza;
import Model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditConferenceController implements Initializable {
    private Conferenza conferenza;
    private SubScene subscene;
    private Utente user;
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
    void annullaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/GestisciConferenza.fxml"));
        ManageConferenceController controller = new ManageConferenceController(user);
        loader.setController(controller);
        controller.setSubscene(subscene);
        Parent root = loader.load();
        subscene.setRoot(root);
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) throws SQLException {
        ConferenzaDao dao = new ConferenzaDao();
        dao.updateDettagliConferenza(conferenza);
    }

    @FXML
    void editDetailsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/EditDetails.fxml"));
        EditDetailController controller = new EditDetailController();
        loader.setController(controller);
        controller.setECcontroller(this);
        controller.setConferenza(conferenza);
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
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
        setParameters();
    }
    public void setTitleLabel(){
        if(conferenza!=null)
            titleLabel.setText("Modifica della conferenza: "+ conferenza.getNome());
    }

    public void setParameters() {
        this.setTitleLabel();
        nomeLabel.setText(conferenza.getNome());
        descrizioneLabel.setText(conferenza.getDescrizione());
        budgetLabel.setText(Float.toString(conferenza.getBudget()));
        dataInizioLabel.setText(conferenza.getDataInizio().toString());
        dataFineLabel.setText(conferenza.getDataFine().toString());
        sedeLabel.setText(conferenza.getSede().toString());
    }

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }
}
