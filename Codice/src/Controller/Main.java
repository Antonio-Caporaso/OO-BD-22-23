package Controller;

import DAO.UtenteDAO;
import DbConfig.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.sql.*;

public class Main extends Application {
    public static void main(String[]args){

        Connection conn = null;
        DBConnection dbConnection;
        dbConnection = DBConnection.getDBconnection();
        conn = dbConnection.getConnection();

        if (conn == null) {
            System.out.println("Connessione NON riuscita!");
            System.exit(0);
        }else{
        System.out.println("Connessione OK!");
        launch(args);
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        //Definiamo oggetti delle classi modello da passare ai vari controller
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/Login.fxml"));
            HBox root = (HBox) loader.load();

            // Ottengo un riferimento al controller
            LoginController controller = loader.getController();
            //controller.setPrimaryStage(stage);

            // Settaggio della scena
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image("View/Resources/IconAtom.png"));
            stage.setTitle("Symposium");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}