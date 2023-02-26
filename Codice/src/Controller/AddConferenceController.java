package Controller;

import DAO.ConferenzaDao;
import Model.Conferenze.Conferenza;
import Model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
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
    @FXML
    private SubScene subscene;

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
//        String nome = nomeConferenzaTF.getText();
//        float budget = Float.parseFloat(budgetTextField.getText());
//        String descrizione = descrizioneTextArea.getText();
//        LocalDate dataIselected = dataInizioDP.getValue();
//        LocalDate dataFselected = dataFineDP.getValue();
//        Date dataI = java.sql.Date.valueOf(dataIselected);
//        Date dataF = java.sql.Date.valueOf(dataFselected);
//        Conferenza c = new Conferenza(nome, dataI, dataF,descrizione,budget,user);
//        ConferenzaDao dao = new ConferenzaDao();
//        try {
//            dao.saveConferenza(c);
//            openAddedConferenceDialogWindow();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/CreaConferenzaNext.fxml"));
            AddConferenceNextController controller = new AddConferenceNextController();
            controller.setSubscene(subscene);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void openAddedConferenceDialogWindow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialog");
        alert.setHeaderText("Conferenza aggiunta correttamente");
        alert.getButtonTypes().remove(ButtonType.CANCEL);

        /*
        *   Per modificare l'immagine si usa il metodo setGraphic()
        *   Image image = new Image("path/to/image.png");
        *   ImageView imageView = new ImageView(image);
        *   imageView.setFitWidth(64);
        *   imageView.setFitHeight(64);
        *   alert.setGraphic(imageView);
         */
        alert.showAndWait();
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

