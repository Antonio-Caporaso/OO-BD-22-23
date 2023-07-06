package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.organizzazione.Sponsorizzazione;
import Services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class VisualizzaConferenzaController_Edit implements Initializable {
    private Conferenza conferenza;
    private SubScene subScene;
    private EntiOrganizzatori entiOrganizzatori;
    private InterventiSessione interventiSessione;
    private EventiSocialiSessione eventiSocialiSessione;
    private IntervalliSessione intervalliSessione;
    private Sessioni sessioni;
    private SponsorizzazioniConferenza sponsorizzazioniConferenza;
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
    private Label descrizioneLabel;
    @FXML
    private Button editSessioniButton;
    @FXML
    private TextArea entiView;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label sedeLabel;
    @FXML
    private TextArea sponsorizzazioniView;
    @FXML
    private TableColumn<Sessione,String> nomeSessioneColumn;
    @FXML
    private TableColumn<Sessione, Date> inizioSessioneColumn;
    @FXML
    private TableColumn<Sessione, Date> fineSessioneColumn;
    @FXML
    private TableColumn<Sessione, String> salaSessioneColumn;
    @FXML
    private TableColumn<Sessione, Time> oraInizioColumn;
    @FXML
    private TableColumn<Sessione, Time> orarioFineSessioneColumn;
    @FXML
    private TableView<Sessione> table;
    @FXML
    private Label titleLabel;

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
        Sessione s = table.getSelectionModel().getSelectedItem();
        if(s.equals(null)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selezionare una sessione");
            alert.setContentText("Nessuna sessione selezionata");
            alert.showAndWait();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ViewSessione.fxml"));
            ViewSessioneController_Manage controller = new ViewSessioneController_Manage();
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
        setTable();
    }
    public void setDetails() {
        this.setTitleLabel();
        nomeLabel.setText(conferenza.getNome());
        descrizioneLabel.setText(conferenza.getDescrizione());
        budgetLabel.setText(Float.toString(conferenza.getBudget())+""+conferenza.getValuta());
        dataInizioLabel.setText(conferenza.getDataInizio().toString());
        dataFineLabel.setText(conferenza.getDataFine().toString());
        sedeLabel.setText(conferenza.getSede().toString());
    }
    public void setOrganizzatori() {
        entiOrganizzatori = new EntiOrganizzatori(conferenza);
        try{
            entiOrganizzatori.loadOrganizzatori();
            entiView.setText("");
            for(Ente e: entiOrganizzatori.getEnti()){
                entiView.appendText(e.toString()+"\n");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void setTitleLabel(){
        if(conferenza!=null)
            titleLabel.setText("Modifica della conferenza: "+ conferenza.getNome());
    }
    public void setSponsorizzazioni(){
        sponsorizzazioniConferenza = new SponsorizzazioniConferenza(conferenza);
        try{
            sponsorizzazioniConferenza.loadSponsorizzazioni();
            sponsorizzazioniView.setText("");
            for(Sponsorizzazione s : sponsorizzazioniConferenza.getSponsorizzazioni()){
                sponsorizzazioniView.appendText(s.toString()+"\n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void setTable(){
        table.setEditable(false);
        sessioni = new Sessioni(conferenza);
        try{
            sessioni.loadSessioni();
        }catch (SQLException e){
            e.printStackTrace();
        }
        nomeSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,String>("titolo"));
        inizioSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Date>("dataInizio"));
        fineSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Date>("dataFine"));
        oraInizioColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Time>("orarioInizio"));
        orarioFineSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Time>("orarioFine"));
        salaSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,String>("locazione"));
        table.getItems().addAll(sessioni.getSessioni());
        nomeSessioneColumn.isSortable();
        oraInizioColumn.isSortable();
    }
}
