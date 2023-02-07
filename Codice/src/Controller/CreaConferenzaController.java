package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import java.io.IOException;

public class CreaConferenzaController {
    @FXML
    private DatePicker dataFine;
    @FXML
    private DatePicker dataInizio;
    @FXML
    private TextArea descrizioneConferenza;
    @FXML
    private TextField nomeConferenza;
    @FXML
    private Button inserisciDettagli;
    @FXML
    private Button inserisciEnti;
    @FXML
    private Button inserisciSede;
    @FXML
    private Button inserisciSessioni;
    @FXML
    private Button inserisciSponsorizzazioni;
    @FXML
    private SubScene subscene;
    @FXML
    private Button annulla;
    @FXML
    private Button salva;
    @FXML
    private TextField numeroEnti;
    @FXML
    private Button okButton;
    @FXML
    private Button nextButton;
    @FXML
    private TextField nomeEnte;
    @FXML
    private Button prevButton;
    @FXML
    private Label aggiuntaEnteLabel;
    @FXML
    void inserisciDettagliOnAction(ActionEvent event) {
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/InserisciDettagliConferenza.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subscene.setRoot(loader);
    }
    @FXML
    void inserisciEntiOnAction(ActionEvent event) {
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/InserisciEnti.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subscene.setRoot(loader);
    }
    @FXML
    void inserisciSedeOnAction(ActionEvent event) {
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/InserisciSede.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subscene.setRoot(loader);
    }

    @FXML
    void inserisciSessioniOnAction(ActionEvent event) {
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/InserisciSessioni.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subscene.setRoot(loader);
    }

    @FXML
    void inserisciSponsorizzazioniOnAction(ActionEvent event) {
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/InserisciSponsor.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subscene.setRoot(loader);
    }
}
