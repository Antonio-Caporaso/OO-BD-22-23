package Model.Utilities;

import Model.DAO.SponsorDao;
import Model.Entities.Sponsor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sponsors {
    private ObservableList<Sponsor> sponsors;

    public Sponsors() {
        sponsors = FXCollections.observableArrayList();
    }

    public ObservableList<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(ObservableList<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public void loadSponsor() {
        SponsorDao dao = new SponsorDao();
        sponsors.clear();
        sponsors.addAll(dao.retrieveSponsors());
    }
}
