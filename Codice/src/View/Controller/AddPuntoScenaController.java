package View.Controller;

import Persistence.Entities.Conferenze.Programma;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPuntoScenaController implements Initializable {
    private Programma programma;
    @FXML
    private SubScene subScene;

    public AddPuntoScenaController(Programma programma) {
        this.programma = programma;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddPunto.fxml"));
            AddPuntoController controller = new AddPuntoController(programma);
            loader.setController(controller);
            Parent root = loader.load();
            subScene.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
