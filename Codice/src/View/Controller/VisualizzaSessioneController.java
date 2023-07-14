package View.Controller;

import Persistence.DAO.ProgrammaDao;
import Persistence.Entities.Conferenze.ActivityModel;
import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.partecipanti.Speaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class VisualizzaSessioneController implements Initializable {
    private Sessione sessione;
    private SubScene subScene;
    private VisualizzaConferenzaController visualizzaConferenzaController;
    private Programma programma;
    @FXML
    private Label inizioLabel;
    @FXML
    private Label fineLabel;
    @FXML
    private Button confermaButton;
    @FXML
    private Label coordinatoreLabel;
    @FXML
    private Button editDetailsButton;
    @FXML
    private Button editProgrammaButton;
    @FXML
    private Label keynoteSpeakerLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label salaLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private TableColumn<ActivityModel, Speaker> speakerTableColumn;
    @FXML
    private TableColumn<ActivityModel, String> descrizioneTableColumn;
    @FXML
    private TableColumn<ActivityModel, String> appuntamentoTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> fineTableColumn;
    @FXML
    private TableColumn<ActivityModel, Timestamp> inizioTableColumn;
    @FXML
    private TableView<ActivityModel> programmaTableView;

    public VisualizzaSessioneController(Sessione sessione, SubScene subScene, VisualizzaConferenzaController visualizzaConferenzaController) {
        this.sessione = sessione;
        this.subScene = subScene;
        this.visualizzaConferenzaController = visualizzaConferenzaController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        retrieveProgrammaSessione();
        setDettagliSessione();
        setProgrammaTableView();
    }
    private void retrieveProgrammaSessione() {
        ProgrammaDao programmaDao = new ProgrammaDao();
        try {
            programma = programmaDao.retrieveProgrammaBySessione(sessione);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setDettagliSessione() {
        titleLabel.setText(sessione.getTitolo());
        nomeLabel.setText(sessione.getTitolo());
        inizioLabel.setText(sessione.getInizio().toString());
        fineLabel.setText(sessione.getFine().toString());
        salaLabel.setText(sessione.getLocazione().getNomeSala());
        coordinatoreLabel.setText(sessione.getCoordinatore().toString());
        keynoteSpeakerLabel.setText(programma.getKeynote().toString());
    }
    private void setProgrammaTableView(){
        try{
            programma.loadProgramaSessione();
            appuntamentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            inizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
            fineTableColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
            speakerTableColumn.setCellValueFactory(new PropertyValueFactory<>("speaker"));
            descrizioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
            programmaTableView.setItems(programma.getProgrammaSessione());

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    void fineButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaConferenza.fxml"));
        loader.setController(visualizzaConferenzaController);
        subScene.setRoot(loader.load());
    }
    @FXML
    void showDetails(MouseEvent event) throws IOException {
        ActivityModel activityModel = programmaTableView.getSelectionModel().getSelectedItem();
        if(activityModel instanceof Intervento){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaIntervento.fxml"));
            VisualizzaInterventoController controller = new VisualizzaInterventoController((Intervento) activityModel);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Dettagli intervento");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Non ci sono ulteriori informazioni da visualizzare");
            alert.showAndWait();
        }
    }
}
