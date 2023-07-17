package View.Controller;

import Persistence.DAO.ConferenzaDao;
import Persistence.DAO.InterventoDao;
import Persistence.DAO.SessioneDao;
import Persistence.DAO.SponsorDao;
import Utilities.Stats;
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
import javafx.scene.layout.Pane;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrentMonthPieChart();
        setCurrentYearPieChart();
        setLabels();
    }

    private void createPieChart(ObservableList<Stats> stats, ObservableList<PieChart.Data> pieChartData, BorderPane pane, String title) {
        for (Stats dataPoint : stats) {
            PieChart.Data data = new PieChart.Data(dataPoint.getIstituzione(), dataPoint.getPercentuale());
            pieChartData.add(data);
        }
        PieChart pieChart = new PieChart(pieChartData);
        pane.setCenter(pieChart);
        pieChart.setMaxHeight(247);
        pieChart.setMaxWidth(247);
        pieChart.setLabelsVisible(true);
        pieChart.setTitle(title);
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(10); // Adjust the length of the label lines as needed
        for (PieChart.Data data : pieChart.getData()) {
            data.setName(data.getName() + " (" + Math.round(data.getPieValue()) + "%)");
        }
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
    }

    private LinkedList<Stats> retrieveIstituzioniByMonth(int m, int y) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByMonth(m, y);
    }

    private LinkedList<Stats> retrieveIstituzioniByYear(int i) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByYear(i);
    }

    private void setCurrentMonthPieChart() {
        Month mese = LocalDate.now().getMonth();
        int anno = LocalDate.now().getYear();
        try {
            ObservableList<Stats> stats = FXCollections.observableArrayList();
            stats.addAll(retrieveIstituzioniByMonth(mese.getValue(), anno));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            if (stats.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Non risultano interventi nel mese cercato");
            } else {
                createPieChart(stats, pieChartData, currentMonthPieChartPanel, "Statistica mensile");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentYearPieChart() {
        int anno = LocalDate.now().getYear();
        try {
            ObservableList<Stats> stats = FXCollections.observableArrayList();
            stats.addAll(retrieveIstituzioniByYear(anno));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            if (!(stats.isEmpty())) {
                createPieChart(stats, pieChartData, currentYearPieChartPane, "Statistica annuale");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLabels() {
        ConferenzaDao conferenzaDao = new ConferenzaDao();
        SessioneDao sessioneDao = new SessioneDao();
        SponsorDao sponsorDao = new SponsorDao();
        try {
            totalConferenzeLabel.setText(String.valueOf(conferenzaDao.getConferenceNumber()));
            totalPartecipantiLabel.setText(String.valueOf(sessioneDao.getNumeroPartecipanti()));
            totalSponsorizzazioniLabel.setText(String.valueOf(sponsorDao.getSponsorNumber()));
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
    void openStatisticheAnnualiButton(ActionEvent event) {

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