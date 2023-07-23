package Controller.Stats;

import Model.DAO.ConferenzaDao;
import Model.DAO.EnteDao;
import Model.DAO.SessioneDao;
import Model.DAO.SponsorDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VisualizzaStatistiche_Controller implements Initializable {
    @FXML
    private BorderPane currentMonthPieChartPanel;
    @FXML
    private BorderPane currentYearPieChartPane;

    @FXML
    private Label totalConferenzeLabel;

    @FXML
    private Label totalPartecipantiLabel;

    @FXML
    private Label totalSponsorizzazioniLabel;
    @FXML
    private Label totalEntiCounter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();
    }

    private void setLabels() {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        SessioneDao sessioneDao = new SessioneDao();
        SponsorDao sponsorDao = new SponsorDao();
        EnteDao enteDao = new EnteDao();
        try {
            totalConferenzeLabel.setText(String.valueOf(conferenzaDao.getConferenceNumber()));
            totalPartecipantiLabel.setText(String.valueOf(sessioneDao.getNumeroPartecipanti()));
            totalSponsorizzazioniLabel.setText(String.valueOf(sponsorDao.getSponsorNumber()));
            totalEntiCounter.setText(String.valueOf(enteDao.getTotalEnti()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showAlert(Alert.AlertType information, String s) {
        Alert alert = new Alert(information);
        alert.setContentText(s);
        alert.showAndWait();
    }

    @FXML
    void openStatisticheAnnualiButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Stats/StatisticheAnnuali.fxml"));
        YearlyStatWindow_Controller controller = new YearlyStatWindow_Controller();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Calcola statistiche annuali");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void openStatisticheMensiliWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Stats/StatisticheMensili.fxml"));
        MonthlyStatWindow_Controller controller = new MonthlyStatWindow_Controller();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Calcola statistiche mensili");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}