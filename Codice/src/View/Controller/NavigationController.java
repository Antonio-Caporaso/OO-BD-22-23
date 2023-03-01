package View.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


    /*Utilizzo
    È necessario prima impostare la finestra principale utilizzando il metodo setStage(Stage stage).
    Per caricare una scena, utilizzare il metodo loadScene(String fxml) passando il percorso del file fxml della scena desiderata.*/
public class NavigationController {
    private static final NavigationController instance = new NavigationController();
    private Stage stage;

    public static NavigationController getInstance() {
        return instance;
    }
    // imposta la finestra principale per la classe di navigazione
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    //carica la scena del fxml e la imposta sulla finestra principale.
    public void loadScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
