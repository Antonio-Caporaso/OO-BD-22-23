package Controller.View;

import Model.DAO.EventoSocialeDao;
import Model.Utilities.ActivityModel;
import Model.Entities.EventoSociale;
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

public class ShowInfoEventoSociale_Controller implements Initializable {
    private ActivityModel activityModel;
    @FXML
    private Button closeButton;
    private EventoSociale eventoSociale;
    @FXML
    private Label fineLabel;
    @FXML
    private Label inizioLabel;
    @FXML
    private AnchorPane popUpWindowAnchor;
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
        EventoSocialeDao eventoSocialeDao = new EventoSocialeDao();
        eventoSociale = eventoSocialeDao.retrieveEventoByID(activityModel.getId_entry());
        loadLabels();
    }

    private void loadLabels() {
        titoloLabel.setText(eventoSociale.getTipologia());
        inizioLabel.setText(eventoSociale.getInizio().toString());
        fineLabel.setText(eventoSociale.getFine().toString());
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
