package View.Controller;

import Persistence.DAO.InterventoDao;
import Utilities.Stats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ViewStatsController implements Initializable {
    @FXML
    private Button annoButton;
    @FXML
    private TextField annoTextField;
    @FXML
    private TextField meseTextField;
    @FXML
    private Button monthlyButton;
    @FXML
    private Pane pane;
    @FXML
    private Pane pane2;
    @FXML
    private Button pulisciButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private LinkedList<Stats> retrieveIstituzioniByMonth(int m, int y) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByMonth(m, y);
    }

    private LinkedList<Stats> retrieveIstituzioniByYear(int i) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByYear(i);
    }

    @FXML
    void monthlyButtonOnAction(ActionEvent event) {
        ObservableList<Stats> stats = FXCollections.observableArrayList();
        try {
            stats.addAll(retrieveIstituzioniByMonth(Integer.parseInt(meseTextField.getText()), Integer.parseInt(annoTextField.getText())));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            if (stats.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Non risultano interventi nel mese cercato");
            } else {
                createPieChart(stats, pieChartData, pane, "Statistica mensile");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "La stringa inserita non corrisponde ad un numero, riprovare.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void createPieChart(ObservableList<Stats> stats, ObservableList<PieChart.Data> pieChartData, Pane pane, String title) {
        for (Stats dataPoint : stats) {
            PieChart.Data data = new PieChart.Data(dataPoint.getIstituzione(), dataPoint.getPercentuale());
            pieChartData.add(data);
        }
        PieChart pieChart = new PieChart(pieChartData);
        pane.getChildren().add(pieChart);
        pieChart.setTitle(title);
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
    }

    private static void showAlert(Alert.AlertType information, String s) {
        Alert alert = new Alert(information);
        alert.setContentText(s);
        alert.showAndWait();
    }

    @FXML
    void pulisciButtonOnAction(ActionEvent event) {
        meseTextField.setText("");
    }

    @FXML
    void yearlyButtonOnAction(ActionEvent event) {
        ObservableList<Stats> stats = FXCollections.observableArrayList();
        try {
            stats.addAll(retrieveIstituzioniByYear(Integer.parseInt(annoTextField.getText())));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Stats s : stats) {
                System.out.println(s);
            }
            if (stats.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Non risultano interventi nell'anno cercato");
            } else {
                createPieChart(stats, pieChartData, pane2, "Statistica annuale");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "La stringa inserita non corrisponde ad un numero, riprovare.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}