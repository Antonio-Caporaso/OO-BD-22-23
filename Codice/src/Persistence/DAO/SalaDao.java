package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.DTO.Conferenze.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
