package View.Controller;

import Persistence.DAO.InterventoDao;
import Services.Stats;
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
    private Button pulisciButton;
    @FXML
    private TextField annoTextField;
    @FXML
    private Pane pane;
    @FXML
    private Pane pane2;
    @FXML
    private TextField meseTextField;
    @FXML
    private Button monthlyButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    void monthlyButtonOnAction(ActionEvent event) {
        ObservableList<Stats> stats = FXCollections.observableArrayList();
        try {
            stats.addAll(retrieveIstituzioniByMonth(Integer.parseInt(meseTextField.getText())));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            if(stats.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Non risultano interventi nel mese cercato");
                alert.showAndWait();
            }else{
                for (Stats dataPoint : stats) {
                    PieChart.Data data = new PieChart.Data(dataPoint.getIstituzione(), dataPoint.getPercentuale());
                    pieChartData.add(data);
                }
                PieChart pieChart = new PieChart(pieChartData);
                pane.getChildren().add(pieChart);

                pieChart.setTitle("Statistica mensile");
                pieChart.setClockwise(true);
                pieChart.setLabelLineLength(50);
                pieChart.setLabelsVisible(true);
                pieChart.setStartAngle(180);
            }
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("La stringa inserita non corrisponde ad un numero, riprovare.");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private LinkedList<Stats> retrieveIstituzioniByYear(int i) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByYear(i);
    }
    private LinkedList<Stats> retrieveIstituzioniByMonth(int i) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByMonth(i);
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
            for(Stats s: stats){
                System.out.println(s);
            }
            if(stats.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Non risultano interventi nell'anno cercato");
                alert.showAndWait();
            }else{
                for (Stats dataPoint : stats) {
                    PieChart.Data data = new PieChart.Data(dataPoint.getIstituzione(), dataPoint.getPercentuale());
                    pieChartData.add(data);
                }
                PieChart pieChart = new PieChart(pieChartData);
                pane2.getChildren().add(pieChart);

                pieChart.setTitle("Statistica annuale");
                pieChart.setClockwise(true);
                pieChart.setLabelLineLength(50);
                pieChart.setLabelsVisible(true);
                pieChart.setStartAngle(180);
            }
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("La stringa inserita non corrisponde ad un numero, riprovare.");
            alert.showAndWait();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}