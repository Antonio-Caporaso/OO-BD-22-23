package Controller.View;

import Model.DAO.InterventoDao;
import Model.Entities.Conferenze.ActivityModel;
import Model.Entities.Conferenze.Intervento;
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
    private ActivityModel activityModel;
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
    @FXML
    private Label speakerLabel;
    @FXML
    private TextArea abstractTextArea;

    private Intervento intervento;
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

    //Public methods
    private void initializeData() throws SQLException {
        InterventoDao interventoDao = new InterventoDao();
        intervento = interventoDao.retrieveInterventoByID(activityModel.getId_entry());
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

