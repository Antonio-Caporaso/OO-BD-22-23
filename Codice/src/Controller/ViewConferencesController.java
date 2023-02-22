package Controller;

import DAO.ConferenzaDao;
import Model.Conferenze.Conferenza;
import Presenter.ConferenzaPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
public class ViewConferencesController {
    private ConferenzaPresenter conferenze;
    private ConferenzaDao conferenzaDao;
    @FXML
    private ListView<Conferenza> listConferenze;
    @FXML
    public void initialize(){
       /* try{
            List<Conferenza> conferenze = conferenzaDao.getAllConferenze();
            listConferenze.getItems().addAll(conferenze);
        }catch (SQLException e){
            e.printStackTrace();
        }*/
    }



}
