package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Conferenze.Intervallo;
import Model.Entities.Conferenze.Programma;
import org.postgresql.util.PGInterval;

import java.sql.*;
import java.util.LinkedList;

public class IntervalloDao {
    private DBConnection dbcon;
    private Connection conn;
    public LinkedList<Intervallo> retrieveIntervalliByProgramma(Programma programma) throws SQLException {
        LinkedList<Intervallo> intervalli = new LinkedList<>();
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM intervallo where id_programma=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,programma.getProgrammaID());
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            Intervallo i = new Intervallo();
            i.setId_entry(rs.getInt(1));
            i.setProgramma(programma);
            i.setTipologia(rs.getString("tipologia"));
            i.setInizio(rs.getTimestamp("inizio"));
            i.setFine(rs.getTimestamp("fine"));
            i.setType(i.getTipologia());
            intervalli.add(i);
        }
        return intervalli;
    }

    public int createIntervallo(Intervallo intervallo, PGInterval durata) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        int result = 0;
        String query = "select * from save_intervallo(?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, intervallo.getTipologia());
        stm.setInt(2,intervallo.getProgramma().getProgrammaID());
        stm.setObject(3,durata);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }
    public void createNewIntervallo(Intervallo intervallo, PGInterval durata) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        int result = 0;
        String query = "CALL add_intervallo(?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, intervallo.getTipologia());
        stm.setInt(2,intervallo.getProgramma().getProgrammaID());
        stm.setObject(3,durata);
        stm.execute();
    }


    public void deleteIntervallo(Intervallo intervallo) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "DELETE FROM intervallo where id_intervallo=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,intervallo.getId_entry());
        stm.executeUpdate();
    }

    public Intervallo retrieveIntervalloByID(int idEntry) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from intervallo where id_intervallo=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,idEntry);
        Intervallo i = new Intervallo();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            i.setId_entry(rs.getInt(1));
            i.setTipologia(rs.getString("tipologia"));
            i.setInizio(rs.getTimestamp("inizio"));
            i.setFine(rs.getTimestamp("fine"));
            i.setType(i.getTipologia());
        }
        return i;
    }

    public void updateIntervallo(Intervallo intervallo) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "update intervallo set tipologia=?, inizio= ?, fine=? where id_intervallo=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1,intervallo.getTipologia());
        stm.setTimestamp(2,intervallo.getInizio());
        stm.setTimestamp(3,intervallo.getFine());
        stm.setInt(4,intervallo.getId_entry());
        stm.executeUpdate();
    }
}
