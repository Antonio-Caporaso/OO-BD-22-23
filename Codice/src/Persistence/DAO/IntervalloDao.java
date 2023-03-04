package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Intervallo;
import Persistence.Entities.Conferenze.Programma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void saveIntervallo(Intervallo intervallo) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "INSERT INTO intervallo values (default,?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,intervallo.getProgramma().getProgrammaID());
        stm.setString(2,intervallo.getTipologia());
        stm.setTimestamp(3,intervallo.getOrario());
        stm.executeUpdate();
    }

    public void removeIntervallo(Intervallo intervallo) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "DELETE FROM intervallo where id_intervallo=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,intervallo.getId());
        stm.executeUpdate();
    }
}
