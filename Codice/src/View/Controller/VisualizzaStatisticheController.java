package View.Controller;

import Persistence.DAO.*;
import Utilities.Stats;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class VisualizzaStatisticheController implements Initializable {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/StatisticheAnnuali.fxml"));
        YearlyStatWindowController controller = new YearlyStatWindowController();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/StatisticheMensili.fxml"));
        MonthlyStatWindowController controller = new MonthlyStatWindowController();
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