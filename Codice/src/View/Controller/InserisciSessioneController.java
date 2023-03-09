package View.Controller;

import Exceptions.BlankFieldException;
import Persistence.DAO.SalaDao;
import Persistence.DAO.SessioneDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class InserisciSessioneController implements Initializable,FormChecker {
    @FXML
    private Button backButton;
    @FXML
    private DatePicker dataFineDatePicker;
    @FXML
    private DatePicker dataInizioDatePicker;
    @FXML
    private Button inserisciButton;
    @FXML
    private ChoiceBox<String> salaChoiceBox;
    @FXML
    private TextField titoloSessioneTextField;
    @FXML
    private Spinner<Integer> orarioFineMinutiSpinner;
    @FXML
    private Spinner<Integer> orarioFineOreSpinner;
    @FXML
    private Spinner<Integer> orarioInizioMinutiSpinner;
    @FXML
    private Spinner<Integer> orarioInizioOreSpinner;
    private Conferenza conferenza;
    private Sessione sessione;
    private SubScene subscene;
    private Utente user;


    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadViewSessioni(conferenza);

    }

    public void setConferenza(Conferenza conferenza){
        this.conferenza=conferenza;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setUtente(Utente utente){
        this.user=utente;
    }

    public void setSpinnersAtLaunch(){
    SpinnerValueFactory<Integer> ValueFactoryInizioOre = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
    SpinnerValueFactory<Integer> ValueFactoryInizioMinuti = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
    SpinnerValueFactory<Integer> ValueFactoryFineOre = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
    SpinnerValueFactory<Integer> ValueFactoryFineMinuti = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);

    orarioInizioMinutiSpinner.setValueFactory(ValueFactoryInizioMinuti);
    orarioInizioOreSpinner.setValueFactory(ValueFactoryInizioOre);
    orarioFineMinutiSpinner.setValueFactory(ValueFactoryFineMinuti);
    orarioFineOreSpinner.setValueFactory(ValueFactoryFineOre);

    orarioFineOreSpinner.setEditable(true);
    orarioFineMinutiSpinner.setEditable(true);
    orarioInizioMinutiSpinner.setEditable(true);
    orarioInizioOreSpinner.setEditable(true);

    orarioFineOreSpinner.getValue();
    orarioFineMinutiSpinner.getValue();
    orarioInizioMinutiSpinner.getValue();
    orarioInizioOreSpinner.getValue();
    }

    public void loadChoiceBox(){
        SalaDao sala= new SalaDao();
        try {
            int sedeID = conferenza.getSede().getSedeID();
            salaChoiceBox.getItems().setAll(sala.retrieveNomeSalaBySedeID(sedeID));
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        try {
            checkFieldsAreBlank();
            String titolo = titoloSessioneTextField.getText();
            LocalDate dataInizioSelected = dataInizioDatePicker.getValue();
            LocalDate dataFineSelected = dataFineDatePicker.getValue();
            Date dataInizio = Date.valueOf(dataInizioSelected);
            Date dataFine = Date.valueOf(dataFineSelected);
            int oraInizio = orarioInizioOreSpinner.getValue();
            int minutiInizio= orarioInizioMinutiSpinner.getValue();
            int oraFine= orarioFineOreSpinner.getValue();
            int minutiFine= orarioFineMinutiSpinner.getValue();
            Calendar calInizio = Calendar.getInstance();
            Calendar calFine = Calendar.getInstance();
            calInizio.set(Calendar.HOUR_OF_DAY, oraInizio);
            calInizio.set(Calendar.MINUTE, minutiInizio);
            calFine.set(Calendar.HOUR_OF_DAY, oraFine);
            calFine.set(Calendar.MINUTE, minutiFine);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String orarioInizioStr = sdf.format(calInizio.getTime());
            String orarioFineStr = sdf.format(calFine.getTime());
            Time orarioInizio = Time.valueOf(orarioInizioStr + ":00");
            Time orarioFine = Time.valueOf(orarioFineStr + ":00");
            Sala sala=selezionaSala();
            sessione= new Sessione(titolo,dataInizio,dataFine,sala,conferenza,orarioInizio,orarioFine);
            SessioneDao sessioneDao = new SessioneDao();
            sessioneDao.saveSessione(sessione);
            openAddSessioneDialogWindow();
            loadViewSessioni(conferenza);
        }catch (BlankFieldException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadViewSessioni(Conferenza c){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaSessione.fxml"));
            VisualizzaSessioneController controller = new VisualizzaSessioneController();
            controller.setSubscene(subscene);
            loader.setController(controller);
            controller.setConferenza(c);
            controller.setUser(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Sala selezionaSala(){
        SalaDao salaDao = new SalaDao();
        Sala sala = new Sala();
        try {
            String nomeSala = salaChoiceBox.getValue();
            int idSede = conferenza.getSede().getSedeID();
            sala = salaDao.retreiveSalaBySedeIdAndNomeSala(idSede, nomeSala);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return sala;
    }
    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if(titoloSessioneTextField.getText().isBlank() || dataInizioDatePicker.getValue().equals(null) ||
                dataFineDatePicker.getValue().equals(null) || salaChoiceBox.getValue().equals(null))
            throw new BlankFieldException();
    }
    public void openAddSessioneDialogWindow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Successo!");
        alert.setHeaderText("Sessione aggiunta correttamente");
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            setSpinnersAtLaunch();
            loadChoiceBox();
    }
}
