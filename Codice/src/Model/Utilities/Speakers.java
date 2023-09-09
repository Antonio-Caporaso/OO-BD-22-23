package Model.Utilities;

import Model.DAO.SpeakerDao;
import Model.Entities.partecipanti.Speaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Speakers {
    private ObservableList<Speaker> speakers;

    public Speakers() {
        speakers = FXCollections.observableArrayList();
    }
    public ObservableList<Speaker> getSpeakers() {
        return speakers;
    }
    public void setSpeakers(ObservableList<Speaker> speakers) {
        this.speakers = speakers;
    }
    public void loadSpeakers() throws SQLException {
        SpeakerDao dao = new SpeakerDao();
        speakers.clear();
        speakers.addAll(dao.retreiveAllSpeakers());
    }
    public void addSpeaker(Speaker speaker) throws SQLException {
        SpeakerDao dao = new SpeakerDao();
        if(!speakers.contains(speaker)){
            dao.createSpeaker(speaker);
            speakers.add(speaker);
        }
    }
}
