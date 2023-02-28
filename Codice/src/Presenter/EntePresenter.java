package Presenter;

import DAO.EnteDao;
import Model.organizzazione.Ente;
import Model.organizzazione.Organizzatore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EntePresenter {
    private ObservableList<Ente> enti;
    public EntePresenter(){
        this.enti = FXCollections.observableArrayList();
    }
    public void loadEnti(){
        EnteDao dao = new EnteDao();
        enti.clear();
        enti.addAll(dao.retrieveEnti());
    }
    public ObservableList<Ente> getEnti() {
        return enti;
    }

    public void setEnti(ObservableList<Ente> enti) {
        this.enti = enti;
    }
}
