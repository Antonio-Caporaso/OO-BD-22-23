package View.Controller;

import Persistence.DAO.ConferenzaDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Services.Sessioni;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;


import java.net.URL;
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

    }

    @FXML
    private ListView<Sessione> sessioniListView;

    public void loadInserisciSessione(Conferenza c){
        ConferenzaDao conferenzaDao= new ConferenzaDao();
        conferenza= conferenzaDao.retrieveConferenzaByNome(c.getNome());
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/InserisciSessione.fxml"));
            InserisciSessioneController controller = new InserisciSessioneController();
            controller.setSubscene(subscene);
            loader.setController(controller);
            controller.setSubscene(subscene);
            Parent root = loader.load();
            controller.setConferenza(conferenza);
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
//    public void setSessioni()  {
//        try {
//            sessioni.loadSessioni2(conferenza);
//            sessioniListView.setItems(sessioni.getSessioni());
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        setSessioni();
    }
}
