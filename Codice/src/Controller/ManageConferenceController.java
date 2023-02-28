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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageConferenceController implements Initializable {
    @FXML
    private ListView<Conferenza> conferenzeView;
    @FXML
    private Button deleteConferenzaButton;
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
            conferenzeView.setItems(conferenze.getConferenzeUtente());
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
            controller.setUser(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void enableButtons(MouseEvent event) {
        modificaButton.setDisable(false);
        deleteConferenzaButton.setDisable(false);
    }
    public void deleteOnAction(ActionEvent event){
        Conferenza c = conferenzeView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminare conferenza");
        alert.setHeaderText("Sicuro di voler eliminare la conferenza "+c.getNome()+"?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try{
                eliminaConferenza(c);
                showInformationAlert();
            }catch (SQLException e){
                showErrorAlert(e);
            }
        }
    }

    private static void showInformationAlert() {
        Alert noErrorOnDelete = new Alert(Alert.AlertType.INFORMATION);
        noErrorOnDelete.setTitle("Eliminazione conferenza");
        noErrorOnDelete.setContentText("Eliminazione effettuata correttamente");
        noErrorOnDelete.showAndWait();
    }

    private static void showErrorAlert(SQLException e) {
        Alert errorOnDelete = new Alert(Alert.AlertType.ERROR);
        errorOnDelete.setTitle("Eliminazione conferenza");
        errorOnDelete.setContentText(e.getMessage());
        errorOnDelete.showAndWait();
    }

    private void eliminaConferenza(Conferenza c) throws SQLException {
        conferenze.removeConferenza(c);
    }
}
