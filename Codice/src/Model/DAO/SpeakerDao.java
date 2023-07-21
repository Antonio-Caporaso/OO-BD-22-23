package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.partecipanti.Speaker;

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
            setSpeaker(sp, dao, rs);
        }
        return sp;
    }

    private static void setSpeaker(Speaker sp, EnteDao dao, ResultSet rs) throws SQLException {
        sp.setIdSpeaker(rs.getInt("id_speaker"));
        sp.setNome(rs.getString("nome"));
        sp.setCognome(rs.getString("cognome"));
        sp.setTitolo(rs.getString("titolo"));
        sp.setIstituzione(dao.retrieveEnte(rs.getInt("id_ente")));
        sp.setEmail(rs.getString("email"));
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
            setSpeaker(speaker,dao,rs);
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
