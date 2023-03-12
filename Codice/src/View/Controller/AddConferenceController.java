package View.Controller;
import Exceptions.BlankFieldException;
import Persistence.DAO.ConferenzaDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Utente;
import Services.Conferenze;
import Services.Sedi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import Persistence.Entities.Conferenze.Sede;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddConferenceController implements Initializable,FormChecker{
    private Utente user;
    private Sedi sedi = new Sedi();
    private String nome;
    private int idConferenza;
    private Conferenza conferenza;
    private Conferenze conference = new Conferenze();
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
    private ChoiceBox<Sede> sedeChoice;
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
        try {
            checkFieldsAreBlank();
            String nome = nomeConferenzaTF.getText();
            float budget = Float.parseFloat(budgetTextField.getText());
            String descrizione = descrizioneTextArea.getText();
            LocalDate dataIselected = dataInizioDP.getValue();
            LocalDate dataFselected = dataFineDP.getValue();
            Date dataI = java.sql.Date.valueOf(dataIselected);
            Date dataF = java.sql.Date.valueOf(dataFselected);
            Sede sede = sedeChoice.getValue();
            Conferenza c = new Conferenza(nome, dataI, dataF, descrizione, budget, sede, user);
            conference.addConferenza(c);
            openAddedConferenceDialogWindow();
            loadInserisciSessione(c);
        }catch (BlankFieldException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void openAddedConferenceDialogWindow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialog");
        alert.setHeaderText("Conferenza aggiunta correttamente");
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.showAndWait();
    }

    public void loadInserisciSessione(Conferenza c){
        ConferenzaDao conferenzaDao= new ConferenzaDao();
        conferenza= conferenzaDao.retrieveConferenzaByNomeAndIdUtente(c.getNome(),user.getIdUtente());
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/InserisciSessione.fxml"));
            InserisciSessioneController controller = new InserisciSessioneController();
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUtente(user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sedi.loadSedi();
        sedeChoice.setItems(sedi.getSedi());
    }
    @FXML
    void showSedi(MouseEvent event) {
        sedeChoice.show();
    }

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if(nomeConferenzaTF.getText().isBlank() || descrizioneTextArea.getText().isBlank() ||
                budgetTextField.getText().isBlank() || sedeChoice.getValue().equals(null) ||
                dataInizioDP.getValue().equals(null) || dataFineDP.getValue().equals(null))
            throw new BlankFieldException();
    }
}

