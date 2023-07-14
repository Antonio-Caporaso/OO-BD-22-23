package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class VisualizzaConferenzaController implements Initializable {
    private Conferenza conferenza;
    private SubScene subScene;
    private VisualizzaConferenzeController visualizzaConferenzeController;
    @FXML
    private Label budgetLabel;
    @FXML
    private Button confermaButton;
    @FXML
    private Label dataFineLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private TextArea descrizioneArea;
    @FXML
    private Button editSessioniButton;
    @FXML
    private TextArea entiView;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label sedeLabel;
    @FXML
    private TableView<Ente> entiTable;
    @FXML
    private TableColumn<Ente, String> siglaEnte;
    @FXML
    private TableColumn<Ente, String> nomeEnte;
    @FXML
    private TextArea sponsorizzazioniView;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private TableColumn<Sessione,String> nomeSessioneColumn;
    @FXML
    private TableColumn<Sessione, Timestamp> inizioSessioneColumn;
    @FXML
    private TableColumn<Sessione, Timestamp> fineSessioneColumn;
    @FXML
    private TableColumn<Sessione, String> salaSessioneColumn;
    @FXML
    private TableView<Sessione> sessioniTable;
    @FXML
    private TableView<Sponsorizzazione> sponsorTable;
    @FXML
    private TableColumn<Sponsorizzazione, String> sponsorColumn;
    @FXML
    private TableColumn<Sponsorizzazione, Float> contributoColumn;
    @FXML
    private TableColumn<Sponsorizzazione,String> valutaColumn;

    @FXML
    private Label titleLabel;
    public VisualizzaConferenzaController(Conferenza conferenza,SubScene subScene, VisualizzaConferenzeController controller){
        this.conferenza = conferenza;
        this.subScene = subScene;
        this.visualizzaConferenzeController = controller;
    }

    public VisualizzaConferenzeController getViewConferencesController() {
        return visualizzaConferenzeController;
    }

    public void setViewConferencesController(VisualizzaConferenzeController visualizzaConferenzeController) {
        this.visualizzaConferenzeController = visualizzaConferenzeController;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    @FXML
    void viewSessioniButtonOnAction(ActionEvent event) throws IOException {
        Sessione s = sessioniTable.getSelectionModel().getSelectedItem();
        if(s.equals(null)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selezionare una sessione");
            alert.setContentText("Nessuna sessione selezionata");
            alert.showAndWait();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ViewSessione.fxml"));
            ViewSessioneController controller = new ViewSessioneController();
            controller.setSessione(s);
            controller.setVisualizzaConferenzaController(this);
            controller.setSubScene(subScene);
            loader.setController(controller);
            Parent root = loader.load();
            subScene.setRoot(root);
        }
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaConferenze.fxml"));
        loader.setController(visualizzaConferenzeController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDetails();
        setOrganizzatori();
        setSponsorizzazioni();
        setSessioniTable();
    }
    public void setDetails() {
        this.setTitleLabel();
        nomeLabel.setText(conferenza.getTitolo());
        descrizioneArea.setText(conferenza.getDescrizione());
        dataInizioLabel.setText(conferenza.getInizio().toString());
        dataFineLabel.setText(conferenza.getFine().toString());
        sedeLabel.setText(conferenza.getSede().toString());
        indirizzoLabel.setText(conferenza.getSede().getIndirizzo().toString());
    }
    public void setOrganizzatori() {
        try {
            conferenza.loadOrganizzatori();
            entiTable.setEditable(false);
            nomeEnte.setCellValueFactory(new PropertyValueFactory<>("nome"));
            siglaEnte.setCellValueFactory(new PropertyValueFactory<>("sigla"));
            entiTable.getItems().addAll(conferenza.getEnti());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void setSponsorizzazioni() {
        try {
            conferenza.loadSponsorizzazioni();
            sponsorColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione,String>("sponsor"));
            contributoColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione,Float>("contributo"));
            valutaColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione,String>("valuta"));
            sponsorTable.setItems(conferenza.getSponsorizzazioni());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setTitleLabel(){
        if(conferenza!=null)
            titleLabel.setText("Conferenza: "+ conferenza.getTitolo());
    }

    private void setSessioniTable(){
        sessioniTable.setEditable(false);
        try{
            conferenza.loadSessioni();
            nomeSessioneColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
            inizioSessioneColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
            fineSessioneColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
            salaSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,String>("locazione"));
            sessioniTable.getItems().addAll(conferenza.getSessioni());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    void viewSessione(MouseEvent event) {
        try {
            Sessione s = sessioniTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/VisualizzaSessione.fxml"));
            VisualizzaSessioneController controller = new VisualizzaSessioneController(s,subScene,this);
            loader.setController(controller);
            Parent root = loader.load();
            subScene.setRoot(root);
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna sessione selezionata");
            alert.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
