package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.Conferenze.Sede;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SalaDao {
    private DBConnection dbcon;
    private Connection conn;

    public Sala retrieveSalaByID(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM sala WHERE idsala = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
        Sala s = new Sala();
        SedeDao sedeDao = new SedeDao();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            s.setSalaID(rs.getInt(1));
            s.setSede(sedeDao.retrieveSedeByID(rs.getInt(2)));
            s.setCapacity(rs.getInt(3));
            s.setNomeSala(rs.getString(4));
        }
        return s;
    }
    public List<String> retrieveNomeSalaBySedeID(int idsede) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT nomesala FROM sala WHERE idsede = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,idsede);
        List<String> salaNames = new ArrayList<>();
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            salaNames.add(rs.getString(1));
        }
        return salaNames;
    }
    public Sala retreiveSalaBySedeIdAndNomeSala(int idsede, String nomeSala) throws SQLException{
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM sala WHERE idsede = ? AND nomesala= ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,idsede);
        stm.setString(2,nomeSala);
        Sala s = new Sala();
        SedeDao sedeDao = new SedeDao();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            s.setSalaID(rs.getInt(1));
            s.setSede(sedeDao.retrieveSedeByID(rs.getInt(2)));
            s.setCapacity(rs.getInt(3));
            s.setNomeSala(rs.getString(4));
        }
        return s;
    }

    public LinkedList<Sala> retrieveSaleBySede(Sede sede) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from sala where idsede = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,sede.getSedeID());
        LinkedList<Sala> sale = new LinkedList<>();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            Sala s = new Sala();

        }
        return sale;
    }
}
