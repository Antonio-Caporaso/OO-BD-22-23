package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sede;
import Services.Sedi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class EditConferenceDetailsController implements Initializable {
    private Conferenza conferenza;
    private EditConferenceController editConferenceController;
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
    private DatePicker dataFineDP;
    @FXML
    private DatePicker dataInizioDP;
    @FXML
    private ChoiceBox<Sede> sedeChoice;
    @FXML
    private ChoiceBox<String> valutaChoice;

    public void setEditConferenceController(EditConferenceController editConferenceController) {
        this.editConferenceController = editConferenceController;
    }

    @FXML
    void annullaOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        loader.setController(editConferenceController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @FXML
    void okOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditConference.fxml"));
        loader.setController(editConferenceController);
        conferenza.setNome(nomeTF.getText());
        conferenza.setDescrizione(descrizioneTF.getText());
        conferenza.setBudget(Float.parseFloat(budgetTextField.getText()));
        conferenza.setDataInizio(Date.valueOf(dataInizioDP.getValue()));
        conferenza.setDataFine(Date.valueOf(dataFineDP.getValue()));
        conferenza.setSede(sedeChoice.getValue());
        editConferenceController.setConferenza(conferenza);
        editConferenceController.setDetails();
        Parent root = loader.load();
        subScene.setRoot(root);
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
        dataInizioDP.setValue(conferenza.getDataInizio().toLocalDate());
        dataFineDP.setValue(conferenza.getDataFine().toLocalDate());
        budgetTextField.setText(Float.toString(conferenza.getBudget()));
        sedi.loadSedi();
        sedeChoice.setItems(sedi.getSedi());
        sedeChoice.setValue(conferenza.getSede());
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }
}
