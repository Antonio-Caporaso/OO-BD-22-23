package View.Controller;

import Persistence.DAO.SpeakerDao;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Ente;
import Persistence.Entities.partecipanti.Speaker;
import Utilities.Enti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSpeakerController_Create implements Initializable {

    @FXML
    private HBox hBox;
    @FXML
    private AnchorPane popUpWindowAnchor;
    @FXML
    private Button cancelButton;

    @FXML
    private TextField cognomeTextField;

    @FXML
    private Button confirmaButton;

    @FXML
    private TextField emailtextField;

    @FXML
    private ChoiceBox<Ente> enteChoiceBox;

    @FXML
    private TextField nomeTextField;

    @FXML
    private ChoiceBox<String> titoloChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChoiceBoxes();
    }

    private void loadChoiceBoxes() {
        Enti enti = new Enti();
        String[] titoli = {"Dottore", "Dottoressa", "Professore", "Professoressa", "Assistente", "Ricercatore", "Ricercatrice", "Ingegnere"};
        enti.loadEnti();
        enteChoiceBox.setItems(enti.getEnti());
        Conferenza conferenza = new Conferenza();
        titoloChoiceBox.getItems().setAll(titoli);
    }

    private void loadExceptionWindow(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ExceptionWindow.fxml"));
            Parent root = loader.load();
            ExceptionWindowController controller = loader.getController();
            controller.setErrorMessageLabel(message);
            Stage stage = new Stage();
            stage.setTitle("Errore");
            Scene scene = new Scene(root, 400, 200);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    double x, y;

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmButtonOnAction(ActionEvent event) {
        Speaker speaker = new Speaker();
        SpeakerDao speakerDao = new SpeakerDao();
        speaker.setNome(nomeTextField.getText());
        speaker.setCognome(cognomeTextField.getText());
        speaker.setIstituzione(enteChoiceBox.getSelectionModel().getSelectedItem());
        speaker.setTitolo(titoloChoiceBox.getSelectionModel().getSelectedItem());
        speaker.setEmail(emailtextField.getText());
        try {
            int id = speakerDao.createSpeaker(speaker);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            loadExceptionWindow(e.getMessage());
        }

    }

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
}
