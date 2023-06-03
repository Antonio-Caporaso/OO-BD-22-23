package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;

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
    public Programma retrieveProgrammaBySessione(Sessione sessione) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from programma where idsessione=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,sessione.getSessioneID());
        Programma programma = new Programma();
        SpeakerDao dao = new SpeakerDao();
        OrganizzatoreDao dao1 = new OrganizzatoreDao();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            programma.setProgrammaID(rs.getInt(1));
            programma.setSessione(sessione);
            programma.setKeynote(dao.retrieveSpeakerByID(rs.getInt(3)));
        }
        return programma;
    }

    public void updateProgramma(Programma programma) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "UPDATE programma set id_chair = ?,";
        PreparedStatement stm = conn.prepareStatement(query);
    }
}
