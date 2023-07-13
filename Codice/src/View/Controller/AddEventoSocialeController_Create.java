package View.Controller;

import Persistence.Entities.Conferenze.EventoSociale;
import Persistence.Entities.Conferenze.Intervallo;
import Persistence.Entities.Conferenze.Programma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.postgresql.util.PGInterval;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEventoSocialeController_Create implements Initializable {
    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmaButton;
    @FXML
    private HBox HBox;

    @FXML
    private Spinner<Integer> minutiSpinner;

    @FXML
    private Spinner<Integer> oreSpinner;

    @FXML
    private TextField titoloTextField;
    private Programma programma;
    private double x,y;
    //Public Setters
    public Programma getProgramma() {
        return programma;
    }
    public void setProgramma(Programma programma) {
        this.programma = programma;
    }
    //ActionEvent Methods
    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void confirmButtonOnAction(ActionEvent event) {
        EventoSociale eventoSociale=new EventoSociale();
        eventoSociale.setTipologia(titoloTextField.getText());
        eventoSociale.setProgramma(programma);
        try{
            PGInterval durata = new PGInterval(0,0,0,oreSpinner.getValue(), minutiSpinner.getValue(),0);
            programma.addEvento(eventoSociale,durata);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }catch (SQLException e){
            e.printStackTrace();
            loadExceptionWindow(e.getMessage());
        }
    }
    @FXML
    void dragged (MouseEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

    }
    @FXML
    void pressed(MouseEvent event){
        x= event.getSceneX();
        y= event.getSceneY();
    }
    //Private Methods
    private void loadExceptionWindow(String message){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ExceptionWindow.fxml"));
            Parent root = loader.load();
            ExceptionWindowController controller = loader.getController();
            controller.setErrorMessageLabel(message);
            Stage stage = new Stage();
            stage.setTitle("Errore");
            Scene scene = new Scene(root, 400, 200);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){

        }
    }
    private void loadSpinners(){
        // Configurazione oreSpinner
        SpinnerValueFactory<Integer> oreValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0);
        oreSpinner.setValueFactory(oreValueFactory);

        // Configurazione minutiSpinner
        SpinnerValueFactory<Integer> minutiValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        minutiSpinner.setValueFactory(minutiValueFactory);
    }
    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSpinners();
    }

}
