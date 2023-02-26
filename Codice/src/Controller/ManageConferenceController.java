package Controller;

import Model.Conferenze.Conferenza;
import Model.Utente;
import Presenter.ConferenzaPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageConferenceController implements Initializable {
    @FXML
    private ListView<Conferenza> conferenzeView;
    @FXML
    private Button addSessioneButton;
    @FXML
    private Button deleteConferenzaButton;
    @FXML
    private Button editSessioneButton;
    @FXML
    private Button modificaButton;
    private SubScene subscene;
    private ConferenzaPresenter conferenze = new ConferenzaPresenter();
    private Utente user;

    public ManageConferenceController(Utente user) {
        this.user = user;
    }

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conferenze.loadConferenzeUtente(user);
            conferenzeView.getItems().addAll(conferenze.getConferenzeUtente());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public SubScene getSubscene(){
        return subscene;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene=subscene;
    }
    @FXML
    public void editOnAction(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/EditConferenza.fxml"));
            EditConferenceController controller = new EditConferenceController();
            loader.setController(controller);
            controller.setConferenza(conferenzeView.getSelectionModel().getSelectedItem());
            controller.setSubscene(subscene);
            Parent root = loader.load();
            subscene.setRoot(root);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void deleteOnAction(ActionEvent event){

    }
}
