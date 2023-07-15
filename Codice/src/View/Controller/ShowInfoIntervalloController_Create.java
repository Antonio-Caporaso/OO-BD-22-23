package View.Controller;

import Persistence.Entities.Conferenze.Intervallo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ShowInfoIntervalloController_Create {
    @FXML
    private HBox HBox;
    @FXML
    private Button closeButton;
    @FXML
    private Label descrizioneLabel;
    @FXML
    private Label inizioLabel;
    @FXML
    private Label fineLabel;

    private Intervallo intervallo;
    private double x, y;

    //Public methods
    public void initializeData(Intervallo intervallo){
        this.intervallo=intervallo;
        loadLabels();
    }
    //Private Methods
    private void loadLabels() {
        descrizioneLabel.setText(intervallo.getTipologia());
        inizioLabel.setText(intervallo.getInizio().toString());
        fineLabel.setText(intervallo.getFine().toString());
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
