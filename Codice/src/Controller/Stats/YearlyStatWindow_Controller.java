package Controller.Stats;

import Controller.AlertWindowController;
import Model.DAO.InterventoDao;
import Model.Utilities.Stats;
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

public class YearlyStatWindow_Controller extends AlertWindowController implements Initializable {
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
    private void createBarChart(ObservableList<Stats> stats, BorderPane pane) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Istituzioni");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Percentuale %");
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
            if (stats.isEmpty()) {
                showAlertWindow(Alert.AlertType.INFORMATION, "Attenzione","Non risultano interventi nell'anno cercato");
            } else {
                createBarChart(stats, pieChartPane);
            }
        } catch (InputMismatchException e) {
            showAlertWindow(Alert.AlertType.ERROR, "Errore","Inserire un input valido");
        } catch (SQLException e) {
            showAlertWindow(Alert.AlertType.ERROR,"Errore", e.getMessage());
        }
    }
}
