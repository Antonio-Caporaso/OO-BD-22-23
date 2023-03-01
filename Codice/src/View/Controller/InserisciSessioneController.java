package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sala;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class InserisciSessioneController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private DatePicker dataFineDatePicker;

    @FXML
    private DatePicker dataInizioDatePicker;

    @FXML
    private TextArea descrizioneTextArea;

    @FXML
    private Button creaButton;
    @FXML
    private ChoiceBox<Sala> salaChoiceBox;

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

    @FXML
    void backButtonOnAction(ActionEvent event) {

    }

    @FXML
    void creaButtonOnAction(ActionEvent event) {

    }

    private Conferenza conferenza;
    private SubScene subscene;


    public void setConferenza(Conferenza conferenza){
        this.conferenza=conferenza;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setSpinnersAtLaunch(){

    SpinnerValueFactory<Integer> ValueFactoryInizioOre = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
    SpinnerValueFactory<Integer> ValueFactoryInizioMinuti = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
    SpinnerValueFactory<Integer> ValueFactoryFineOre = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
    SpinnerValueFactory<Integer> ValueFactoryFineMinuti = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);

    TextFormatter<Integer> formatterInizioOre = new TextFormatter<>(ValueFactoryInizioOre.getConverter(), ValueFactoryInizioOre.getValue());
    TextFormatter<Integer> formatterInizioMinuti = new TextFormatter<>(ValueFactoryInizioMinuti.getConverter(), ValueFactoryInizioMinuti.getValue());
    TextFormatter<Integer> formatterFineOre = new TextFormatter<>(ValueFactoryFineOre.getConverter(), ValueFactoryFineOre.getValue());
    TextFormatter<Integer> formatterFineMinuti = new TextFormatter<>(ValueFactoryFineMinuti.getConverter(), ValueFactoryFineMinuti.getValue());

    orarioInizioOreSpinner.getEditor().setTextFormatter(formatterInizioOre);
    orarioInizioMinutiSpinner.getEditor().setTextFormatter(formatterInizioMinuti);
    orarioFineOreSpinner.getEditor().setTextFormatter(formatterFineOre);
    orarioFineMinutiSpinner.getEditor().setTextFormatter(formatterFineMinuti);

    orarioInizioMinutiSpinner.setValueFactory(ValueFactoryInizioMinuti);
    orarioInizioOreSpinner.setValueFactory(ValueFactoryInizioOre);
    orarioFineMinutiSpinner.setValueFactory(ValueFactoryFineMinuti);
    orarioFineOreSpinner.setValueFactory(ValueFactoryFineOre);

    addSpinnerEditorListener(orarioFineOreSpinner);
    addSpinnerEditorListener(orarioFineMinutiSpinner);
    addSpinnerEditorListener(orarioInizioMinutiSpinner);
    addSpinnerEditorListener(orarioInizioOreSpinner);

    orarioFineOreSpinner.setEditable(true);
    orarioFineMinutiSpinner.setEditable(true);
    orarioInizioMinutiSpinner.setEditable(true);
    orarioInizioOreSpinner.setEditable(true);

    orarioFineOreSpinner.getValue();
    orarioFineMinutiSpinner.getValue();
    orarioInizioMinutiSpinner.getValue();
    orarioInizioOreSpinner.getValue();
    }
    private void addSpinnerEditorListener(Spinner<Integer> spinner) {
        spinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer.parseInt(newValue);
            }catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore!");
                alert.setHeaderText("Errore! Puoi inserire solo interi!");
                alert.showAndWait();
                spinner.getEditor().setText(oldValue);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            setSpinnersAtLaunch();
    }
}
