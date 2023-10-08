import Model.DbConfig.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    public static void main(String[] args) {

        DBConnection dbConnection = DBConnection.getDBconnection();
        Connection conn = dbConnection.getConnection();

        if (conn == null) {
            System.out.println("Connessione NON riuscita!");
            System.exit(0);
        } else {
            System.out.println("Connessione OK!");
            launch(args);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        loadMainWindow(stage);
    }

    private void loadMainWindow(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Login/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/View/Resources/IconAtom.png"));
        stage.setTitle("Symposium");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}