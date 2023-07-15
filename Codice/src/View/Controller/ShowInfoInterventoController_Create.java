package View.Controller;

import Persistence.Entities.Conferenze.Intervento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ShowInfoInterventoController_Create{
    @FXML
    private HBox HBox;
    @FXML
    private Button closeButton;
    @FXML
    private Label titoloLabel;
    @FXML
    private Label inizioLabel;
    @FXML
    private Label fineLabel;
    @FXML
    private Label speakerLabel;
    @FXML
    private TextArea abstractTextArea;

    private Intervento intervento;
    private double x, y;

    //Public methods
    public void initializeData(Intervento intervento){
        this.intervento=intervento;
        loadLabels();
    }
    //Private Methods
    private void loadLabels() {
        String speaker=intervento.getSpeaker().getNome()+" "+intervento.getSpeaker().getCognome();
        titoloLabel.setText(intervento.getTitolo());
        inizioLabel.setText(intervento.getInizio().toString());
        fineLabel.setText(intervento.getFine().toString());
        abstractTextArea.setText(intervento.getEstratto());
        speakerLabel.setText(speaker);

    }
    //ActionEvent Methods
    @FXML
    void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
}


