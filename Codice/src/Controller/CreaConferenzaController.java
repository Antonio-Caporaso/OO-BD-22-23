package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class CreaConferenzaController {
    @FXML
    private Button inserisciDettagli;
    @FXML
    private Button inserisciEnti;
    @FXML
    private Button inserisciSede;
    @FXML
    private Button inserisciSessioni;
    @FXML
    private Button inserisciSponsor;
    @FXML
    private SubScene subscene;
    @FXML
    void inserisciDettagliOnAction(ActionEvent event) {
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/InserisciDettagliConferenza.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subscene.setRoot(loader);
    }
    @FXML
    void inserisciEntiOnAction(ActionEvent event) {
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/InserisciEnti.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subscene.setRoot(loader);
    }
    @FXML
    void inserisciSedeOnAction(ActionEvent event) {
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/InserisciSede.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subscene.setRoot(loader);
    }

    @FXML
    void inserisciSessioniOnAction(ActionEvent event) {
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/InserisciSessioni.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subscene.setRoot(loader);
    }

    @FXML
    void inserisciSponsorOnAction(ActionEvent event) {
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource("../View/FXML/InserisciSponsor.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subscene.setRoot(loader);
    }

}
