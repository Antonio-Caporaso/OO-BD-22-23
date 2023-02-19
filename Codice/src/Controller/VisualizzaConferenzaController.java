package Controller;

import DAO.ConferenzaDao;
import Model.Conferenze.Conferenza.Conferenza;
import Model.Conferenze.Conferenza.ConferenzaModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.List;

public class VisualizzaConferenzaController {
    private ConferenzaModel conferenze;
    private ConferenzaDao conferenzaDao;
    @FXML
    private ListView<Conferenza> listConferenze;
    @FXML
    public void initialize(){
        try{
            List<Conferenza> conferenze = conferenzaDao.getAllConferenze();
            listConferenze.getItems().addAll(conferenze);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



}
