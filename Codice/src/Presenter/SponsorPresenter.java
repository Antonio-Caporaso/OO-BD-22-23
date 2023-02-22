package Presenter;

import DAO.SponsorDao;
import Model.organizzazione.Sponsor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SponsorPresenter {
    private ObservableList<String> sponsorNames;
    private ObservableList<Sponsor> sponsors;
    public SponsorPresenter(){
        sponsorNames = FXCollections.observableArrayList();
        sponsors = FXCollections.observableArrayList();
    }
    public void loadSponsorNames(){
        SponsorDao dao = new SponsorDao();
        sponsorNames.clear();
        sponsorNames.addAll(dao.retrieveNomiSponsor());
    }
    public void loadSponsor(){
        SponsorDao dao = new SponsorDao();
        sponsors.clear();
        sponsors.addAll(dao.retrieveSponsors());
    }

    public ObservableList<String> getSponsor() {
        return sponsorNames;
    }

    public void setSponsor(ObservableList<String> sponsorNames) {
        this.sponsorNames = sponsorNames;
    }

    public ObservableList<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(ObservableList<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }
}
