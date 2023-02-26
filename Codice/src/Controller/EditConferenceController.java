package Controller;

import Model.Conferenze.Conferenza;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EditConferenceController implements Initializable {
    private Conferenza conferenza;
    @FXML
    private Label titleLabel;
    @FXML
    private SubScene subscene;

    public SubScene getSubscene() {
        return subscene;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTitleLabel();
    }
    public void setTitleLabel(){
        if(conferenza!=null)
            titleLabel.setText("Modifica della conferenza: "+ conferenza.getNome());
    }
}
