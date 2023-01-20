package Controllers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class Main extends Application {
    public static void main(String[]args){
        launch(args);
    }

    @Override
    public void start(Stage loginStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Image icon = new Image("app/resources/Logo.jpg");
            loginStage.getIcons().add(icon);
            loginStage.setTitle("Conferentia");
            Scene scene =new Scene(root);
            loginStage.setResizable(false);
            loginStage.setScene(scene);
            loginStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}