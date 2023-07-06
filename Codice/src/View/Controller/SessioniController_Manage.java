package View.Controller;

import Exceptions.SessioneNotSelectedException;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Services.Sessioni;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class SessioniController_Manage implements Initializable {
    private Conferenza conferenza;
    private Sessioni sessioni ;
    private SubScene subscene;
    private Utente user;
    private ConferenzaController_Edit conferenzaControllerEdit;
    @FXML
    private TableView<Sessione> table;
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
    private Button addSessioneButton;

    @FXML
    private Button confermaButton;

    @FXML
    private Button editSessionsButton;

    @FXML
    private Button rimuoviSessioneButton;
    private void setTable(){
        table.setEditable(false);
        nomeSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,String>("titolo"));
        inizioSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Date>("dataInizio"));
        fineSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Date>("dataFine"));
        oraInizioColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Time>("orarioInizio"));
        orarioFineSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,Time>("orarioFine"));
        salaSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione,String>("locazione"));
        table.setItems(sessioni.getSessioni());
        nomeSessioneColumn.isSortable();
        oraInizioColumn.isSortable();
    }
    public void reloadSessioni(){
        try {
            sessioni.loadSessioni();
            setTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void addSessioneOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddSessione.fxml"));
        AddSessioneController_Edit controller = new AddSessioneController_Edit();
        loader.setController(controller);
        controller.setConferenza(conferenza);
        controller.setManageSessioniController(this);
        controller.setSubscene(subscene);
        controller.setSessioni(sessioni);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    public void editSessionsOnAction(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditSessione.fxml"));
            SessioneController_Edit controller = new SessioneController_Edit();
            loader.setController(controller);
            Sessione s = table.getSelectionModel().getSelectedItem();
            if(s == null)
                throw new SessioneNotSelectedException();
            controller.setSessione(s);
            controller.setConferenza(conferenza);
            controller.setManageSessioniController(this);
            controller.setSubScene(subscene);
            Parent root = loader.load();
            subscene.setRoot(root);
        }catch(SessioneNotSelectedException e){
            showSessioneNotSelectedAlert(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTable();
    }

    public void setEditConferenceController(ConferenzaController_Edit conferenzaControllerEdit) {
        this.conferenzaControllerEdit = conferenzaControllerEdit;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }
    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
        loader.setController(conferenzaControllerEdit);
        conferenzaControllerEdit.setSubscene(subscene);
        conferenzaControllerEdit.setConferenza(conferenza);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    void rimuoviSessioneOnAction(ActionEvent event) {
        try {
            Sessione s = table.getSelectionModel().getSelectedItem();
            if (s == null) {
                throw new SessioneNotSelectedException();
            }
            Optional<ButtonType> result = showConfirmationAlert(s);
            if (result.get() == ButtonType.OK) {
                try {
                    sessioni.removeSessione(s);
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                }
            }
        }catch (SessioneNotSelectedException e){
            showSessioneNotSelectedAlert(e);
        }
    }

    private static void showSessioneNotSelectedAlert(SessioneNotSelectedException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmationAlert(Sessione s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler rimuovere la sessione '" + s.getTitolo() + "'?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    public void setSessioni(Sessioni sessioni) {
        this.sessioni = sessioni;
    }
}
