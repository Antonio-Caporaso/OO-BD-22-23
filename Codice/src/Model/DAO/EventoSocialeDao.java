package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Conferenze.EventoSociale;
import Model.Entities.Conferenze.Programma;
import org.postgresql.util.PGInterval;

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
        String query = "DELETE FROM evento where id_evento=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,e.getId_entry());
        stm.executeUpdate();
    }

    public LinkedList<EventoSociale> retrieveEventiByProgramma(Programma programma) throws SQLException {
        dbcon=DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query="SELECT * FROM evento where id_programma=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,programma.getProgrammaID());
        ResultSet rs = stm.executeQuery();
        LinkedList<EventoSociale> eventi = new LinkedList<>();
        ProgrammaDao dao = new ProgrammaDao();
        while(rs.next()){
            EventoSociale e = new EventoSociale();
            e.setId_entry(rs.getInt("id_evento"));
            e.setProgramma(dao.retrieveProgrammaByID(rs.getInt("id_programma")));
            e.setTipologia(rs.getString("tipologia"));
            e.setInizio(rs.getTimestamp("inizio"));
            e.setFine(rs.getTimestamp("fine"));
            e.setType(e.getTipologia());
            eventi.add(e);
        }
        return  eventi;
    }

    public EventoSociale retrieveEventoByID(int idEntry) throws SQLException {
        dbcon=DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query="SELECT * FROM evento where id_evento=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,idEntry);
        EventoSociale e = new EventoSociale();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            e.setId_entry(rs.getInt("id_evento"));
            e.setTipologia(rs.getString("tipologia"));
            e.setInizio(rs.getTimestamp("inizio"));
            e.setFine(rs.getTimestamp("fine"));
            e.setType(e.getTipologia());
        }
        return  e;
    }

    public int saveEvento(EventoSociale e, PGInterval durata) throws SQLException {
        dbcon=DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        int result = 0;
        String query="select * from add_new_evento(?,?,?) ";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1,e.getTipologia());
        stm.setInt(2,e.getProgramma().getProgrammaID());
        stm.setObject(3,durata);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }

    public void updateEvento(EventoSociale eventoSociale) throws SQLException {
        dbcon=DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "update eventosociale set tipologia=?,inizio=?,fine=? where id_evento=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1,eventoSociale.getTipologia());
        stm.setTimestamp(2,eventoSociale.getInizio());
        stm.setTimestamp(3,eventoSociale.getFine());
        stm.setInt(4,eventoSociale.getId_entry());
        stm.executeUpdate();
    }
}
