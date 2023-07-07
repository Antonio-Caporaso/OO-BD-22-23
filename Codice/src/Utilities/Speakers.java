package Utilities;

import Persistence.DAO.SpeakerDao;
import Persistence.Entities.partecipanti.Speaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Speakers {
    private ObservableList<Speaker> speakers;
    public Speakers(){
        speakers = FXCollections.observableArrayList();
    }
    public void loadSpeakers(){
        SpeakerDao dao = new SpeakerDao();
        try {
            speakers.clear();
            speakers.addAll(dao.retreiveAllSpeakers());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void getSpeakerById(int id){
        SpeakerDao dao = new SpeakerDao();
        try {
            dao.retrieveSpeakerByID(id);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public ObservableList<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(ObservableList<Speaker> speakers) {
        this.speakers = speakers;
    }
}
