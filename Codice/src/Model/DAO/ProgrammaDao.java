package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Programma;
import Model.Entities.Sessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgrammaDao {
    private Connection conn;
    private DBConnection dbcon;

    public void removeKeynoteFromProgramma(Programma programma) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "update programma set id_keynote=NULL where id_programma=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, programma.getProgrammaID());
        stm.executeUpdate();
    }

    public int retrieveIDProgramma(Sessione sessione) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select id_programma from programma where id_sessione = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, sessione.getId_sessione());
        int id = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    public Programma retrieveProgrammaByID(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from programma where id_programma = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        SpeakerDao speakerDao = new SpeakerDao();
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        SessioneDao dao = new SessioneDao();
        Programma p = new Programma();
        while (rs.next()) {
            p.setProgrammaID(id);
            p.setSessione(dao.retrieveSessioneById(rs.getInt("id_sessione")));
            p.setKeynote(speakerDao.retrieveSpeakerByID(rs.getInt(2)));
        }
        return p;
    }

    public Programma retrieveProgrammaBySessione(Sessione sessione) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from programma where id_sessione=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, sessione.getId_sessione());
        Programma programma = new Programma();
        SpeakerDao dao = new SpeakerDao();
        ResultSet rs = stm.executeQuery();
        int id_speaker = 0;
        while (rs.next()) {
            programma.setProgrammaID(rs.getInt(1));
            programma.setSessione(sessione);
            id_speaker = rs.getInt("id_keynote");
            if(id_speaker!=0)
                programma.setKeynote(dao.retrieveSpeakerByID(id_speaker));
        }
        return programma;
    }

    public void saveKeynote(Programma programma) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "update programma set id_keynote = ? where id_programma=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, programma.getKeynote().getIdSpeaker());
        stm.setInt(2, programma.getProgrammaID());
        stm.executeUpdate();
    }
}
