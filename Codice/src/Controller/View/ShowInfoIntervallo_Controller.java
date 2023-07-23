package Controller.View;

import Model.DAO.IntervalloDao;
import Model.Entities.Conferenze.ActivityModel;
import Model.Entities.Conferenze.Intervallo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowInfoIntervallo_Controller implements Initializable {

    private ActivityModel activityModel;
    @FXML
    private AnchorPane popUpWindowAnchor;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Public methods
    private void initializeData() throws SQLException {
        IntervalloDao intervalloDao = new IntervalloDao();
        intervallo = intervalloDao.retrieveIntervalloByID(activityModel.getId_entry());
        loadLabels();
    }

    public void setActivityModel(ActivityModel activityModel) {
        this.activityModel = activityModel;
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
