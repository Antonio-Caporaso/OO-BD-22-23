package Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;


public abstract class AlertWindowController {
    protected void showAlertWindow(Alert.AlertType tipologia, String header, String message){
        Alert alert = new Alert(tipologia);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected Optional<ButtonType> showConfirmationDialog(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        return (Optional<ButtonType>) alert.showAndWait();

    }
}
