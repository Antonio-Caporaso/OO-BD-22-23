package Controller;

import DAO.ConferenzaDao;
import Model.Conferenze.Conferenza;
import Presenter.ConferenceModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.List;

public class ViewConferencesController {
    private ConferenceModel conferenze;
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