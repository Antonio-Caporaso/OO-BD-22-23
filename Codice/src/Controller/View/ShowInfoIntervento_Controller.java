package Controller.View;

import Model.DAO.InterventoDao;
import Model.Utilities.ActivityModel;
import Model.Entities.Intervento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowInfoIntervento_Controller implements Initializable {
    @FXML
    private TextArea abstractTextArea;
    private ActivityModel activityModel;
    @FXML
    private Button closeButton;
    @FXML
    private Label fineLabel;
    @FXML
    private Label inizioLabel;
    private Intervento intervento;
    @FXML
    private AnchorPane popUpWindowAnchor;
    @FXML
    private Label speakerLabel;
    @FXML
    private Label titoloLabel;
    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setActivityModel(ActivityModel activityModel) {
        this.activityModel = activityModel;
    }

    private void initializeData() throws SQLException {
        InterventoDao interventoDao = new InterventoDao();
        intervento = interventoDao.retrieveInterventoByID(activityModel.getId_entry());
        loadLabels();
    }

    private void loadLabels() {
        String speaker = intervento.getSpeaker().getNome() + " " + intervento.getSpeaker().getCognome();
        titoloLabel.setText(intervento.getTitolo());
        inizioLabel.setText(intervento.getInizio().toString());
        fineLabel.setText(intervento.getFine().toString());
        abstractTextArea.setText(intervento.getEstratto());
        speakerLabel.setText(speaker);

    }

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


