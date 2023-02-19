package Model.organizzazione.Sponsor;

import DAO.SponsorDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SponsorModel {
    private ObservableList<String> sponsor;
    public SponsorModel(){
        sponsor = FXCollections.observableArrayList();
    }
    public void loadSponsorNames(){
        SponsorDao dao = new SponsorDao();
        sponsor.clear();
        sponsor.addAll(dao.retrieveNomiSponsor());
    }

    public ObservableList<String> getSponsor() {
        return sponsor;
    }

    public void setSponsor(ObservableList<String> sponsor) {
        this.sponsor = sponsor;
    }
}
