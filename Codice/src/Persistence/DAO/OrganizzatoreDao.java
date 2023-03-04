package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.organizzazione.Organizzatore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizzatoreDao {
    DBConnection dbcon;
    Connection conn;
    public Organizzatore retrieveOrganizzatoreByID(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM organizzatore WHERE idorganizzatore=?";
        PreparedStatement stm = conn.prepareStatement(query);
        Organizzatore org = new Organizzatore();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            org.setOrganizzatoreID(id);
            org.setNome(rs.getString("nome"));
            org.setCognome(rs.getString("cognome"));
            org.setTitolo(rs.getString("titolo"));
            org.setIstituzione(rs.getString("istituzione"));
            org.setEmail(rs.getString("email"));
        }
        return org;
    }
}
