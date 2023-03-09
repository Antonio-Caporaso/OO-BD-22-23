package View.Controller;

import Exceptions.SessioneNotSelectedException;
import Persistence.DAO.ConferenzaDao;
import Persistence.DAO.SessioneDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Services.Sessioni;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;


import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VisualizzaSessioneController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private Button inserisciButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button rimuoviButton;
    @FXML
    private SubScene subscene;
    private Conferenza conferenza;
    private Sessioni sessioni = new Sessioni(conferenza);

    @FXML
    void backButtonOnAction(ActionEvent event) {

    }

    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
    loadInserisciSessione(conferenza);
    }

    @FXML
    void nextButtonOnAction(ActionEvent event) {

    }

    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        try {
            Sessione s = sessioniListView.getSelectionModel().getSelectedItem();
            if (s == null) {
                throw new SessioneNotSelectedException();
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Sicuro di voler rimuovere la sessione '" + s.getTitolo() + "'?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    sessioni.removeSessione(s);
                    setSessioni();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }catch (SessioneNotSelectedException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    private ListView<Sessione> sessioniListView;

    public void loadInserisciSessione(Conferenza c){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/InserisciSessione.fxml"));
            InserisciSessioneController controller = new InserisciSessioneController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(c);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setSessioni() {
    SessioneDao sessioneDao= new SessioneDao();
    try {
        LinkedList<Sessione> sessioni = sessioneDao.retrieveSessioni(conferenza);
        sessioniListView.setItems(FXCollections.observableArrayList(sessioni));
    }catch(SQLException e){
        e.printStackTrace();
    }

}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSessioni();
    }

}
