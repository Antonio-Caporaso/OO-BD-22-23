package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Comitato;

import java.sql.*;

public class ComitatoDao {
    Connection conn = null;
    DBConnection dbcon = null;
    public Comitato retrieveComitatobyId(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from retrieve_comitato(?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
        ResultSet rs = stm.executeQuery();
        OrganizzatoreDao dao = new OrganizzatoreDao();
        Comitato c = new Comitato();
        while(rs.next()){
            c.setComitatoID(rs.getInt("id_comitato"));
            c.setTipologia(rs.getString("tipologia"));
        }
        return c;
    }
}
