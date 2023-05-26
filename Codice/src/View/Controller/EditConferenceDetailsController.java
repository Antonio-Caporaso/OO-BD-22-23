package View.Controller;

import Persistence.DAO.ConferenzaDao;
import Persistence.DAO.SponsorizzazioneDAO;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sede;
import Services.Sedi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditConferenceDetailsController implements Initializable {
    private Conferenza conferenza;
    private ModificaConferenzaController modificaConferenzaController;
    private SubScene subScene;
    private Sedi sedi = new Sedi();
    @FXML
    private Button annullaButton;
    @FXML
    private TextField budgetTextField;
    @FXML
    private TextField descrizioneTF;
    @FXML
    private TextField nomeTF;
    @FXML
    private Button okButton;
    @FXML
    private DateTimePicker dataFineDP;
    @FXML
    private DateTimePicker dataInizioDP;
    @FXML
    private ChoiceBox<Sede> sedeChoice;
    @FXML
    private ChoiceBox<String> valutaChoice;

    public void setEditConferenceController(ModificaConferenzaController modificaConferenzaController) {
        this.modificaConferenzaController = modificaConferenzaController;
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
        loader.setController(modificaConferenzaController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @FXML
    void okOnAction(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler modificare i dettagli della conferenza?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
            loader.setController(modificaConferenzaController);
            conferenza.setNome(nomeTF.getText());
            conferenza.setDescrizione(descrizioneTF.getText());
            conferenza.setBudget(Float.parseFloat(budgetTextField.getText()));
            conferenza.setDataInizio(Timestamp.valueOf(dataInizioDP.getDateTimeValue()));
            conferenza.setDataFine(Timestamp.valueOf(dataFineDP.getDateTimeValue()));
            conferenza.setSede(sedeChoice.getValue());
            conferenza.setValuta(valutaChoice.getValue());
            ConferenzaDao dao = new ConferenzaDao();
            dao.updateDettagliConferenza(conferenza);
            modificaConferenzaController.setConferenza(conferenza);
            modificaConferenzaController.setDetails();
            Parent root = loader.load();
            subScene.setRoot(root);
        }
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeTF.setText(conferenza.getNome());
        descrizioneTF.setText(conferenza.getDescrizione());
        dataInizioDP.setValue(conferenza.getDataInizio().toLocalDateTime().toLocalDate());
        dataFineDP.setValue(conferenza.getDataFine().toLocalDateTime().toLocalDate());
        budgetTextField.setText(Float.toString(conferenza.getBudget()));
        sedi.loadSedi();
        sedeChoice.setItems(sedi.getSedi());
        sedeChoice.setValue(conferenza.getSede());
        setValute();
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    private void setValute() {
        SponsorizzazioneDAO dao = new SponsorizzazioneDAO();
        ObservableList<String> valute = FXCollections.observableArrayList();
        try{
            valute.addAll(dao.retrieveSimboloValute());
            valutaChoice.setItems(valute);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
