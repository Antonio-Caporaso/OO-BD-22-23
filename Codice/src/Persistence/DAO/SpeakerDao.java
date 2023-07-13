package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.partecipanti.Speaker;

import java.sql.*;
import java.util.LinkedList;

public class SpeakerDao {
    private DBConnection dbcon;
    private Connection conn;
    public Speaker retrieveSpeakerByID(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        Speaker sp = new Speaker();
        String query = "SELECT * FROM Speaker where id_speaker = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
        EnteDao dao = new EnteDao();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            sp.setIdSpeaker(id);
            sp.setNome(rs.getString(2));
            sp.setCognome(rs.getString(3));
            sp.setTitolo(rs.getString(4));
            sp.setIstituzione(dao.retrieveEnte(rs.getInt("id_ente")));
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
        EnteDao dao = new EnteDao();
        while(rs.next()){
            Speaker speaker = new Speaker();
            speaker.setIdSpeaker(rs.getInt("id_speaker"));
            speaker.setNome(rs.getString("nome"));
            speaker.setCognome(rs.getString("cognome"));
            speaker.setTitolo(rs.getString("titolo"));
            speaker.setIstituzione(dao.retrieveEnte(rs.getInt(("id_ente"))));
            speaker.setEmail(rs.getString("email"));
            speakers.add(speaker);
        }
        return speakers;
    }

    public int createSpeaker(Speaker s) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "INSERT INTO speaker (nome, cognome, titolo, email, id_ente) VALUES (?, ?, ?, ?, ?) RETURNING id_speaker";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1,s.getNome());
        stm.setString(2,s.getCognome());
        stm.setObject(3,s.getTitolo(), Types.OTHER);
        stm.setString(4,s.getEmail());
        stm.setInt(5,s.getIstituzione().getId_ente());
        ResultSet rs = stm.executeQuery();
        int result = 0;
        while(rs.next()){
            result = rs.getInt("id_speaker");
        }
        return result;
    }
}
