package View.Controller;

import Persistence.Entities.Conferenze.EventoSociale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ShowInfoEventoSocialeController_Create {
    @FXML
    private HBox hBox;
    @FXML
    private AnchorPane popUpWindowAnchor;
    @FXML
    private Button closeButton;
    @FXML
    private Label titoloLabel;
    @FXML
    private Label inizioLabel;
    @FXML
    private Label fineLabel;

    private EventoSociale eventoSociale;
    private double x, y;

    //Public methods
    public void initializeData(EventoSociale eventoSociale){
        this.eventoSociale=eventoSociale;
        loadLabels();
    }
    //Private Methods
    private void loadLabels() {
        titoloLabel.setText(eventoSociale.getTipologia());
        inizioLabel.setText(eventoSociale.getInizio().toString());
        fineLabel.setText(eventoSociale.getFine().toString());
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
