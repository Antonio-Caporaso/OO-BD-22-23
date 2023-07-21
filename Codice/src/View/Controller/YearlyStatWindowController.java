package View.Controller;

import Persistence.DAO.InterventoDao;
import Utilities.Stats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class YearlyStatWindowController implements Initializable {
    @FXML
    private Spinner<Integer> annoSpinner;
    @FXML
    private BorderPane pieChartPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpinners();
    }

    private void setSpinners() {
        SpinnerValueFactory<Integer> anniValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2023,3000,1,1);
        annoSpinner.setValueFactory(anniValueFactory);
    }
    private static void showAlert(Alert.AlertType information, String s) {
        Alert alert = new Alert(information);
        alert.setContentText(s);
        alert.showAndWait();
    }
    private void createPieChart(ObservableList<Stats> stats, BorderPane pane) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setLegendVisible(false);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Stats stat : stats) {
            series.getData().add(new XYChart.Data<>(stat.getIstituzione(), stat.getPercentuale()));
        }
        barChart.getData().add(series);
        pane.setCenter(barChart);
    }
    private LinkedList<Stats> retrieveIstituzioniByYear(int i) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByYear(i);
    }
    @FXML
    void calcolaGrafico(ActionEvent event) {
        try {
            int anno = annoSpinner.getValue();
            ObservableList<Stats> stats = FXCollections.observableArrayList();
            stats.clear();
            stats.addAll(retrieveIstituzioniByYear(anno));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            if (stats.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Non risultano interventi nell'anno cercato");
            } else {
                createPieChart(stats, pieChartPane);
            }
        } catch (InputMismatchException e) {
            showAlert(Alert.AlertType.ERROR, "Inserire un input valido");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }
}
