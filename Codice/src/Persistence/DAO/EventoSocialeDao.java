package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.EventoSociale;
import Persistence.Entities.Conferenze.Programma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class EventoSocialeDao {
    private DBConnection dbcon;
    private Connection conn;

    public void deleteEvento(EventoSociale e) throws SQLException {
        dbcon=DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "DELETE FROM eventosociale where id_evento=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,e.getId());
        stm.executeUpdate();
    }

    public LinkedList<EventoSociale> retrieveEventiByProgramma(Programma programma) throws SQLException {
        dbcon=DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query="SELECT * FROM eventosociale where id_programma=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,programma.getProgrammaID());
        ResultSet rs = stm.executeQuery();
        LinkedList<EventoSociale> eventi = new LinkedList<>();
        ProgrammaDao dao = new ProgrammaDao();
        while(rs.next()){
            EventoSociale e = new EventoSociale();
            e.setId(rs.getInt(1));
            e.setProgramma(dao.retrieveProgrammaByID(rs.getInt(2)));
            e.setTipologia(rs.getString(3));
            e.setOrario(rs.getTimestamp(4));
            eventi.add(e);
        }
        return  eventi;
    }

    public int saveEvento(EventoSociale e) throws SQLException {
        dbcon=DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        int result = 0;
        String query="select * from save_evento(?,?,?) ";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,e.getProgramma().getProgrammaID());
        stm.setString(2,e.getTipologia());
        stm.setTimestamp(3,e.getOrario());
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }

    public void updateEvento(EventoSociale eventoSociale) throws SQLException {
        dbcon=DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "update eventosociale set tipologia=?,orario=? where id_evento=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(3,eventoSociale.getId());
        stm.setString(1,eventoSociale.getTipologia());
        stm.setTimestamp(2,eventoSociale.getOrario());
        stm.executeUpdate();
    }
}
