package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.partecipanti.Speaker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SpeakerDao {
    private DBConnection dbcon;
    private Connection conn;
    public Speaker retrieveSpeakerByID(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        Speaker sp = new Speaker();
        String query = "SELECT * FROM Speaker where idspeaker = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            sp.setIdSpeaker(id);
            sp.setNome(rs.getString(2));
            sp.setCognome(rs.getString(3));
            sp.setTitolo(rs.getString(4));
            sp.setIstituzione(rs.getString(5));
            sp.setEmail(rs.getString(6));
        }
        return sp;
    }
    public LinkedList<Speaker> retreiveAllSpeakers() throws SQLException{
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from speaker";
        PreparedStatement stm = conn.prepareStatement(query);
        LinkedList<Speaker> speakers = new LinkedList<>();
        ResultSet rs  = stm.executeQuery();
        while(rs.next()){
            Speaker speaker = new Speaker();
            speaker.setIdSpeaker(rs.getInt("idspeaker"));
            speaker.setNome(rs.getString("nome"));
            speaker.setCognome(rs.getString("cognome"));
            speaker.setTitolo(rs.getString("titolo"));
            speaker.setIstituzione(rs.getString(("istituzione")));
            speaker.setEmail(rs.getString("email"));
            speakers.add(speaker);
        }
        return speakers;
    }
}
