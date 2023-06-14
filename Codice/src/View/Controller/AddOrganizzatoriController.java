package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Ente;
import Services.Enti;
import Services.EntiOrganizzatori;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import org.postgresql.util.PSQLException;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddOrganizzatoriController implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button backButton;
    @FXML
    private Button inserisciButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button rimuoviButton;
    @FXML
    private ChoiceBox<Ente> organizzatoriChoiceBox;
    @FXML
    private ListView<Ente> organizzatoriListView;
    @FXML
    private SubScene subscene;
    private Conferenza conferenza;
    private Utente user;
    private EntiOrganizzatori organizzatori ;
    private Enti enti = new Enti();
    //Public Setters
    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setUtente(Utente utente){
        this.user=utente;
    }
    //Button methods
    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadEditConferenza();
    }
    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        Ente enteSelezionato = organizzatoriChoiceBox.getSelectionModel().getSelectedItem();
        try{
            if(enteSelezionato==null){
                throw new NullPointerException();
            }
            organizzatori.addEnte(enteSelezionato);
            setOrganizzatoriListView();
        }catch(PSQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Questo organizzatore è già presente!");
            alert.showAndWait();
        }catch (SQLException e){
            e.printStackTrace();
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessun ente selezionato");
            alert.showAndWait();
        }
    }
    @FXML
    void nextOnAction(ActionEvent event) {
        loadAggiungiSponsor();
    }
    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        Ente enteSelezionato = organizzatoriListView.getSelectionModel().getSelectedItem();
        try{
            if (enteSelezionato == null){
                throw new NullPointerException();
            }
            Optional<ButtonType> result = showDeleteDialog();
            if(result.get() == ButtonType.OK) {
                try{
                    organizzatori.removeEnte(enteSelezionato);
                    setOrganizzatoriListView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessun ente selezionato");
            alert.showAndWait();
        }
    }
    private Optional<ButtonType> showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler eliminare il seguente organizzatore?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
    //Private Methods
    private void loadAggiungiSponsor(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddSponsor.fxml"));
            AggiungiSponsorController controller = new AggiungiSponsorController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUtente(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setOrganizzatoriListView() {
        try{
            organizzatori.loadOrganizzatori();
            organizzatoriListView.setItems(organizzatori.getEnti());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadEditConferenza(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
            ModificaConferenzaController controller = new ModificaConferenzaController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUser(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setOrganizzatoriChoiceBox() {
        try {
            enti.loadEnti();
            organizzatoriChoiceBox.setItems(enti.getEnti());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        organizzatori=new EntiOrganizzatori(conferenza);
        setOrganizzatoriListView();
        setOrganizzatoriChoiceBox();
    }

}
