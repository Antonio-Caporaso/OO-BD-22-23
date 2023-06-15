package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Intervallo;
import Persistence.Entities.Conferenze.Programma;

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
            i.setId(rs.getInt(1));
            i.setProgramma(programma);
            i.setTipologia(rs.getString("tipologia"));
            i.setOrario(rs.getTimestamp(4));
            intervalli.add(i);
        }
        return intervalli;
    }

    public int saveIntervallo(Intervallo intervallo) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        int result = 0;
        String query = "INSERT INTO intervallo VALUES (default, CAST(? AS intervallost), ?, ?) RETURNING id_intervallo";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setObject(1, intervallo.getTipologia(), Types.OTHER);
        stm.setInt(2, intervallo.getProgramma().getProgrammaID());
        stm.setTimestamp(3, intervallo.getOrario());
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }

    public int saveInterval(Intervallo intervallo) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        int result = 0;
        String query = "select * from save_intervallo(?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(2, intervallo.getTipologia());
        stm.setInt(1,intervallo.getProgramma().getProgrammaID());
        stm.setTimestamp(3,intervallo.getOrario());
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            result = rs.getInt(1);
        }
        return result;
    }

    public void removeIntervallo(Intervallo intervallo) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "DELETE FROM intervallo where id_intervallo=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,intervallo.getId());
        stm.executeUpdate();
    }

    public void updateIntervallo(Intervallo intervallo) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "update intervallo set tipologia=?, orario=? where id_intervallo=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(3,intervallo.getId());
        stm.setString(1,intervallo.getTipologia());
        stm.setTimestamp(2,intervallo.getOrario());
        stm.executeUpdate();
    }
}
