package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.DTO.Conferenze.Programma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgrammaDao {
    private DBConnection dbcon;
    private Connection conn;
    public Programma retrieveProgrammaByID(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from programma where idprogramma = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        SpeakerDao speakerDao = new SpeakerDao();
        stm.setInt(1,id);
        ResultSet rs = stm.executeQuery();
        Programma p = new Programma();
        while(rs.next()){
            p.setProgrammaID(id);
            p.setKeynote(speakerDao.retrieveSpeakerByID(rs.getInt(2)));
        }
        return p;
    }
}
