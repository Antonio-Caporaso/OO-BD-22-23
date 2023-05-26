package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.organizzazione.Comitato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComitatoDao {
    Connection conn = null;
    DBConnection dbcon = null;
    public Comitato retrieveComitatobyId(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from comitato where idcomitato=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
        ResultSet rs = stm.executeQuery();
        OrganizzatoreDao dao = new OrganizzatoreDao();
        Comitato c = new Comitato();
        while(rs.next()){
            c.setComitatoID(rs.getInt("idcomitato"));
            c.setTipologia(rs.getString("tipologia"));
        }
        return c;
    }
}
