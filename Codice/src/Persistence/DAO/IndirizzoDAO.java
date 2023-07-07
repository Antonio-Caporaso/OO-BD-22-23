package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.organizzazione.Indirizzo;

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
        ResultSet rs = stm.executeQuery();
        Indirizzo i = new Indirizzo();
        while(rs.next()){
            i.setVia(rs.getString("via"));
            i.setCivico(rs.getString("civico"));
            i.setCap(rs.getString("cap"));
            i.setProvincia(rs.getString("provincia"));
            i.setStato(rs.getString("stato"));
            i.setCity(rs.getString("city"));
        }
        return  i;
    }
}
