package View.Controller;

import Persistence.DAO.InterventoDao;
import Services.Stats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ViewStatsController implements Initializable {
    @FXML
    private Button annoButton;
    @FXML
    private Button pulisciButton;
    @FXML
    private TextField annoTextField;
    @FXML
    private BarChart<String, Number> chart1;

    @FXML
    private TextField meseTextField;
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Button monthlyButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yAxis.setUpperBound(100);
        yAxis.setLabel("Percentuale %");
        xAxis.setLabel("Istituzioni");
    }
    @FXML
    void monthlyButtonOnAction(ActionEvent event) {
        ObservableList<Stats> stats = FXCollections.observableArrayList();
        try {
            stats.addAll(retrieveIstituzioniByMonth(Integer.parseInt(meseTextField.getText())));
            for(Stats dataPoint :stats){
                System.out.println(dataPoint);
            }
            XYChart.Series<String,Number> series = new XYChart.Series<>();
            for (Stats dataPoint : stats) {
                XYChart.Data<String, Number> data = new XYChart.Data<>(dataPoint.getIstituzione(),dataPoint.getPercentuale());
                series.getData().add(data);
            }
            chart1.getData().add(series);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private LinkedList<Stats> retrieveIstituzioniByMonth(int i) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByMonth(i);
    }
    @FXML
    void pulisciButtonOnAction(ActionEvent event) {
        meseTextField.setText("");
        chart1.getData().removeAll();
    }

    @FXML
    void yearlyButtonOnAction(ActionEvent event) {

    }

}