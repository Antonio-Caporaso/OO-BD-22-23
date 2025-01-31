package Controller.View;

import Model.Entities.*;
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

public class VisualizzaConferenza_Controller implements Initializable {
    @FXML
    private TableColumn<Organizzatore, String> cognomeOrganizzatoreColumn1;
    @FXML
    private TableColumn<Organizzatore, String> cognomeOrganizzatoreColumn2;
    @FXML
    private TableView<Organizzatore> comitatoLocaleTable;
    @FXML
    private TableView<Organizzatore> comitatoScientificoTable;
    private Conferenza conferenza;
    @FXML
    private TableColumn<Sponsorizzazione, Float> contributoColumn;
    @FXML
    private Label dataFineLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private TextArea descrizioneArea;
    @FXML
    private TableColumn<Organizzatore, String> emailOrganizzatoreColumn1;
    @FXML
    private TableColumn<Organizzatore, String> emailOrganizzatoreColumn2;
    @FXML
    private TableView<Ente> entiTable;
    @FXML
    private TableColumn<Sessione, Timestamp> fineSessioneColumn;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private TableColumn<Sessione, Timestamp> inizioSessioneColumn;
    @FXML
    private TableColumn<Organizzatore, Ente> istituzioneOrganizzatoreColumn1;
    @FXML
    private TableColumn<Organizzatore, Ente> istituzioneOrganizzatoreColumn2;
    @FXML
    private TableColumn<Ente, String> nomeEnte;
    @FXML
    private Label nomeLabel;
    @FXML
    private TableColumn<Organizzatore, String> nomeOrganizzatoreColumn1;
    @FXML
    private TableColumn<Organizzatore, String> nomeOrganizzatoreColumn2;
    @FXML
    private TableColumn<Sessione, String> nomeSessioneColumn;
    @FXML
    private TableColumn<Sessione, String> salaSessioneColumn;
    @FXML
    private Label sedeLabel;
    @FXML
    private TableView<Sessione> sessioniTable;
    @FXML
    private TableColumn<Ente, String> siglaEnte;
    @FXML
    private TableColumn<Sponsorizzazione, String> sponsorColumn;
    @FXML
    private TableView<Sponsorizzazione> sponsorTable;
    private SubScene subScene;
    @FXML
    private Label titleLabel;
    @FXML
    private TableColumn<Organizzatore, String> titoloOrganizzatoreColumn1;
    @FXML
    private TableColumn<Organizzatore, String> titoloOrganizzatoreColumn2;
    @FXML
    private TableColumn<Sponsorizzazione, String> valutaColumn;
    private final VisualizzaConferenze_Controller visualizzaConferenzeController;

    public VisualizzaConferenza_Controller(Conferenza conferenza, SubScene subScene, VisualizzaConferenze_Controller controller) {
        this.conferenza = conferenza;
        this.subScene = subScene;
        this.visualizzaConferenzeController = controller;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDetails();
        setOrganizzatori();
        setSponsorizzazioni();
        setSessioniTable();
        setComitatoScientiticoTable();
        setComitatoLocaleTable();
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
            conferenza.loadEnti();
            entiTable.setEditable(false);
            nomeEnte.setCellValueFactory(new PropertyValueFactory<>("nome"));
            nomeEnte.setReorderable(false);
            siglaEnte.setCellValueFactory(new PropertyValueFactory<>("sigla"));
            siglaEnte.setReorderable(false);
            entiTable.getItems().addAll(conferenza.getEnti());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTitleLabel() {
        if (conferenza != null)
            titleLabel.setText("Conferenza: " + conferenza.getTitolo());
    }

    private void setComitatoLocaleTable() {
        Comitato comitato_L = conferenza.getComitato_l();
        try {
            comitato_L.loadMembri();
            nomeOrganizzatoreColumn1.setCellValueFactory(new PropertyValueFactory<>("nome"));
            nomeOrganizzatoreColumn1.setReorderable(false);
            cognomeOrganizzatoreColumn1.setCellValueFactory(new PropertyValueFactory<>("cognome"));
            cognomeOrganizzatoreColumn1.setReorderable(false);
            titoloOrganizzatoreColumn1.setCellValueFactory(new PropertyValueFactory<>("titolo"));
            titoloOrganizzatoreColumn1.setReorderable(false);
            emailOrganizzatoreColumn1.setCellValueFactory(new PropertyValueFactory<>("email"));
            emailOrganizzatoreColumn1.setReorderable(false);
            istituzioneOrganizzatoreColumn1.setCellValueFactory(new PropertyValueFactory<>("istituzione"));
            istituzioneOrganizzatoreColumn1.setReorderable(false);
            comitatoLocaleTable.getItems().addAll(comitato_L.getMembri());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void setComitatoScientiticoTable() {
        Comitato comitato_S = conferenza.getComitato_s();
        try {
            comitato_S.loadMembri();
            nomeOrganizzatoreColumn2.setCellValueFactory(new PropertyValueFactory<>("nome"));
            nomeOrganizzatoreColumn2.setReorderable(false);
            cognomeOrganizzatoreColumn2.setCellValueFactory(new PropertyValueFactory<>("cognome"));
            cognomeOrganizzatoreColumn2.setReorderable(false);
            titoloOrganizzatoreColumn2.setCellValueFactory(new PropertyValueFactory<>("titolo"));
            titoloOrganizzatoreColumn2.setReorderable(false);
            emailOrganizzatoreColumn2.setCellValueFactory(new PropertyValueFactory<>("email"));
            emailOrganizzatoreColumn2.setReorderable(false);
            istituzioneOrganizzatoreColumn2.setCellValueFactory(new PropertyValueFactory<>("istituzione"));
            istituzioneOrganizzatoreColumn2.setReorderable(false);
            comitatoScientificoTable.getItems().addAll(comitato_S.getMembri());
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void setSessioniTable() {
        sessioniTable.setEditable(false);
        try {
            conferenza.loadSessioni();
            nomeSessioneColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
            nomeSessioneColumn.setReorderable(false);
            inizioSessioneColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
            inizioSessioneColumn.setReorderable(false);
            fineSessioneColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
            fineSessioneColumn.setReorderable(false);
            salaSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione, String>("locazione"));
            salaSessioneColumn.setReorderable(false);
            sessioniTable.getItems().addAll(conferenza.getSessioni());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setSponsorizzazioni() {
        try {
            conferenza.loadSponsorizzazioni();
            sponsorColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("sponsor"));
            sponsorColumn.setReorderable(false);
            contributoColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, Float>("contributo"));
            contributoColumn.setReorderable(false);
            valutaColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("valuta"));
            valutaColumn.setReorderable(false);
            sponsorTable.setItems(conferenza.getSponsorizzazioni());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/View/VisualizzaConferenze.fxml"));
        VisualizzaConferenze_Controller controller = new VisualizzaConferenze_Controller(subScene);
        loader.setController(controller);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @FXML
    void viewSessione(MouseEvent event) {
        try {
            Sessione s = sessioniTable.getSelectionModel().getSelectedItem();
            if(s==null){
                throw new NullPointerException();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/View/VisualizzaSessione.fxml"));
            VisualizzaSessione_Controller controller = new VisualizzaSessione_Controller(s, subScene, this);
            loader.setController(controller);
            Parent root = loader.load();
            subScene.setRoot(root);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna sessione selezionata");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
