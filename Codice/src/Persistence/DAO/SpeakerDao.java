package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.DTO.Conferenze.Speaker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpeakerDao {
    private DBConnection dbcon;
    private Connection conn;
    public Speaker retrieveSpeakerByID(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        Speaker sp = new Speaker();
        String query = "SELECT * FROM Speaker where idspeker = ?";
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
}
