package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Organizzazione.Indirizzo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IndirizzoDAO {
    Connection conn = null;
    DBConnection dbCon = null;

    public Indirizzo retrieveIndirizzoByID(int id) throws SQLException {
        dbCon =DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        String query = "select * from indirizzo where id_indirizzo=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
        ResultSet rs = stm.executeQuery();
        Indirizzo i = new Indirizzo();
        while(rs.next()){
            i.setVia(rs.getString("via"));
            i.setCivico(rs.getString("civico"));
            i.setCap(rs.getString("cap"));
            i.setProvincia(rs.getString("provincia"));
            i.setStato(rs.getString("nazione"));
            i.setCity(rs.getString("city"));
        }
        return  i;
    }
}
