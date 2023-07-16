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
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class VisualizzaStatisticheController implements Initializable {
    @FXML
    private Pane currentMonthPieChartPanel;

    @FXML
    private Pane currentYearPieChartPane;

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

    private void setCurrentYearPieChart(){
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

    private void setCurrentMonthPieChart() {
        Month mese = LocalDate.now().getMonth();
        int anno = LocalDate.now().getYear();
        try {
            ObservableList<Stats> stats = FXCollections.observableArrayList();
            stats.addAll(retrieveIstituzioniByMonth(mese.getValue(),anno));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            if (stats.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Non risultano interventi nel mese cercato");
            } else {
                createPieChart(stats, pieChartData, currentMonthPieChartPanel, "Statistica mensile");
            }
        }catch (SQLException e){
            e.printStackTrace();
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

    private LinkedList<Stats> retrieveIstituzioniByMonth(int m, int y) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByMonth(m, y);
    }

    private LinkedList<Stats> retrieveIstituzioniByYear(int i) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByYear(i);
    }

    private void createPieChart(ObservableList<Stats> stats, ObservableList<PieChart.Data> pieChartData, Pane pane, String title) {
        for (Stats dataPoint : stats) {
            PieChart.Data data = new PieChart.Data(dataPoint.getIstituzione(), dataPoint.getPercentuale());
            pieChartData.add(data);
        }
        PieChart pieChart = new PieChart(pieChartData);
        pane.getChildren().add(pieChart);
        pieChart.setMaxHeight(247);
        pieChart.setMaxWidth(247);
        pieChart.setLabelsVisible(true);
        pieChart.setTitle(title);
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(10); // Adjust the length of the label lines as needed
        pieChart.getData().forEach(data -> data.setName((String.valueOf(data.getPieValue()))));
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
    }

    private static void showAlert(Alert.AlertType information, String s) {
        Alert alert = new Alert(information);
        alert.setContentText(s);
        alert.showAndWait();
    }
}