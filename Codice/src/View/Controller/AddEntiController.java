package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Ente;
import Utilities.Enti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddEntiController implements Initializable {
    private AddConferenceController addConferenceController;
    @FXML
    private ChoiceBox<Ente> entiChoiceBox;
    @FXML
    private ListView<Ente> entiListView;
    @FXML
    private SubScene subscene;
    private Conferenza conferenza;
    private Utente user;
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
        Ente enteSelezionato = entiChoiceBox.getSelectionModel().getSelectedItem();
        try{
            if(enteSelezionato==null)
                throw new NullPointerException();
            conferenza.addEnte(enteSelezionato);
        }catch(SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Questo organizzatore è già presente!");
            alert.showAndWait();
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
        Ente enteSelezionato = entiListView.getSelectionModel().getSelectedItem();
        try{
            if (enteSelezionato == null){
                throw new NullPointerException();
            }
            Optional<ButtonType> result = showDeleteDialog();
            if(result.get() == ButtonType.OK) {
                try{
                    conferenza.removeEnte(enteSelezionato);
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
        alert.setContentText("Sicuro di voler eliminare il seguente ente?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
    //Private Methods
    private void loadAggiungiSponsor(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddSponsor.fxml"));
            AddSponsorController controller = new AddSponsorController();
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
            conferenza.loadOrganizzatori();
            entiListView.setItems(conferenza.getEnti());
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
            entiChoiceBox.setItems(enti.getEnti());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Overrides
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOrganizzatoriListView();
        setOrganizzatoriChoiceBox();
    }

    public AddConferenceController getAddConferenceController() {
        return addConferenceController;
    }

    public void setAddConferenceController(AddConferenceController addConferenceController) {
        this.addConferenceController = addConferenceController;
    }
}
