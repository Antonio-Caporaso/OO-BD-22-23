package Controller;

import Model.Entities.Conferenze.Intervento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class VisualizzaInterventoController implements Initializable {
    @FXML
    private TextArea abstractTextField;
    @FXML
    private Label fineLabel;
    @FXML
    private Label inizioLabel;
    private Intervento intervento;
    @FXML
    private Label speakerLabel;
    @FXML
    private Label titoloLabel;

    public VisualizzaInterventoController(Intervento intervento) {
        this.intervento = intervento;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDettagliIntervento();
    }

    private void setDettagliIntervento() {
        titoloLabel.setText(intervento.getTitolo());
        speakerLabel.setText(intervento.getSpeaker().toString());
        abstractTextField.setText(intervento.getEstratto());
        inizioLabel.setText(intervento.getInizio().toString());
        fineLabel.setText(intervento.getFine().toString());
    }
}
