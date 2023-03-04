package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;

import java.sql.*;
import java.util.LinkedList;

public class SessioneDao {


    private DBConnection dbcon;
    private Connection conn;

    public void removeSessione(Sessione sessione) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "delete from sessione where idsessione = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1,sessione.getSessioneID());
        statement.executeUpdate();
    }
    public void updateSessione(Sessione sessione) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "UPDATE sessione set titolo = ?, datainizio=?, datafine=?,idsala=? where idsessione=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1,sessione.getTitolo());
        stm.setDate(2,sessione.getDataInizio());
        stm.setDate(3,sessione.getDataFine());
        stm.setInt(4,sessione.getLocazione().getSalaID());
        stm.setInt(5,sessione.getSessioneID());
        stm.executeUpdate();
    }
    public LinkedList<Sessione> retrieveSessioni(Conferenza conferenza) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        SalaDao daosala = new SalaDao();
        ProgrammaDao programmaDao = new ProgrammaDao();
        String query = "SELECT * from sessione WHERE idconferenza = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,conferenza.getConferenzaID());
        LinkedList<Sessione> sessioni = new LinkedList<>();
        ResultSet rs  = stm.executeQuery();
        while(rs.next()){
            Sessione s = new Sessione();
            s.setSessioneID(rs.getInt("idsessione"));
            s.setTitolo(rs.getString("titolo"));
            s.setDataInizio(rs.getDate("datainizio"));
            s.setDataFine(rs.getDate("datafine"));
            s.setLocazione(daosala.retrieveSalaByID(rs.getInt("idsala")));
            s.setConferenza(conferenza);
            s.setOrarioInizio(rs.getTime("ora_inizio"));
            s.setOrarioFine(rs.getTime("ora_fine"));
            sessioni.add(s);
        }
        return sessioni;
    }
    public void saveSessione(Sessione sessione)throws SQLException{
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String procedure = "insert_sessione";
        CallableStatement stm = conn.prepareCall("CALL "+procedure+" (?,?,?,?,?,?,?)");
        stm.setString(1, sessione.getTitolo());
        stm.setDate(2,sessione.getDataInizio());
        stm.setDate(3, sessione.getDataFine());
        stm.setTime(4, sessione.getOrarioInizio());
        stm.setTime(5,sessione.getOrarioFine());
        stm.setInt(6, sessione.getConferenza().getConferenzaID());
        stm.setInt(7, sessione.getLocazione().getSalaID());
        stm.execute();
    }
}
