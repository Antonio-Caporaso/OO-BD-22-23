package Controller;

import Model.DAO.ProgrammaDao;
import Model.DAO.SpeakerDao;
import Model.Entities.Conferenze.*;
import Model.Entities.partecipanti.Speaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ViewSessioneController implements Initializable {
    @FXML
    private TableColumn<Intervento, String> abstractColumn;
    @FXML
    private TableColumn<Speaker, String> cognomeKeynote;
    private Conferenza conferenza;
    @FXML
    private Button confermaButton;
    @FXML
    private Label coordinatoreLabel;
    @FXML
    private Label dataFineLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private TableColumn<Speaker, String> emailKeynoteColumn;
    @FXML
    private TableView<EventoSociale> eventiTable;
    @FXML
    private TableView<Intervallo> intervalliTable;
    @FXML
    private TableView<Intervento> interventiTable;
    @FXML
    private TableColumn<Speaker, String> istituzioneKeynoteColumn;
    @FXML
    private TableView<Speaker> keynoteSpeakerTable;
    @FXML
    private TableColumn<Speaker, String> nomeKeynoteColumn;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label oraFineLabel;
    @FXML
    private Label oraInizioLabel;
    @FXML
    private TableColumn<EventoSociale, Timestamp> orarioEventoColumn;
    @FXML
    private TableColumn<Intervallo, Timestamp> orarioIntervalloColumn;
    @FXML
    private TableColumn<Intervento, Timestamp> orarioInterventoColumn;
    private Programma programma;
    @FXML
    private Label salaLabel;
    private Sessione sessione;
    @FXML
    private TableColumn<Intervento, String> speakerColumn;
    private SubScene subScene;
    @FXML
    private TableColumn<EventoSociale, String> tipologiaEventoColumn;
    @FXML
    private TableColumn<Intervallo, String> tipologiaIntervalloColumn;
    @FXML
    private Label titleLabel;
    private VisualizzaConferenzaController visualizzaConferenzaController;

    public ViewSessioneController(Sessione s, VisualizzaConferenzaController visualizzaConferenzaController, SubScene subScene) {
        this.sessione = s;
        this.visualizzaConferenzaController = visualizzaConferenzaController;
        this.subScene = subScene;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    public Sessione getSessione() {
        return sessione;
    }

    public void setSessione(Sessione sessione) {
        this.sessione = sessione;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    public VisualizzaConferenzaController getVisualizzaConferenzaController() {
        return visualizzaConferenzaController;
    }

    public void setVisualizzaConferenzaController(VisualizzaConferenzaController visualizzaConferenzaController) {
        this.visualizzaConferenzaController = visualizzaConferenzaController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(sessione.getTitolo());
        retrieveProgrammaSessione();
        setDettagliSessione();
        setIntervalliTable();
        setEventiTable();
        setInterventiTable();
        setKeynoteTable();
    }

    public void setDettagliSessione() {
        titleLabel.setText(sessione.getTitolo());
        nomeLabel.setText(sessione.getTitolo());
        dataInizioLabel.setText(String.valueOf(sessione.getInizio()));
        dataFineLabel.setText(String.valueOf(sessione.getFine()));
        salaLabel.setText(sessione.getLocazione().getNomeSala());
        coordinatoreLabel.setText(sessione.getCoordinatore().toString());
    }

    private void retrieveProgrammaSessione() {
        ProgrammaDao programmaDao = new ProgrammaDao();
        try {
            programma = programmaDao.retrieveProgrammaBySessione(sessione);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEventiTable() {
        try {
            programma.loadEventiSociali();
            orarioEventoColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            tipologiaEventoColumn.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
            eventiTable.setItems(programma.getEventi());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setIntervalliTable() {
        try {
            programma.loadIntervalli();
            orarioIntervalloColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            tipologiaIntervalloColumn.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
            intervalliTable.setItems(programma.getIntervalli());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setInterventiTable() {
        try {
            programma.loadInterventi();
            orarioInterventoColumn.setCellValueFactory(new PropertyValueFactory<>("orario"));
            speakerColumn.setCellValueFactory(new PropertyValueFactory<>("speaker"));
            abstractColumn.setCellValueFactory(new PropertyValueFactory<>("estratto"));
            interventiTable.setItems(programma.getInterventi());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setKeynoteTable() {
        ProgrammaDao programmaDao = new ProgrammaDao();
        SpeakerDao dao = new SpeakerDao();
        Speaker keynote;
        try {
            int id = programmaDao.retrieveKeynoteSpeakerID(sessione);
            if (id != 0) {
                keynote = dao.retrieveSpeakerByID(id);
                nomeKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
                cognomeKeynote.setCellValueFactory(new PropertyValueFactory<>("cognome"));
                emailKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
                istituzioneKeynoteColumn.setCellValueFactory(new PropertyValueFactory<>("istituzione"));
                keynoteSpeakerTable.getItems().add(keynote);
            } else
                keynoteSpeakerTable.setDisable(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/VisualizzaConferenza.fxml"));
        loader.setController(visualizzaConferenzaController);
        subScene.setRoot(loader.load());
    }
}
