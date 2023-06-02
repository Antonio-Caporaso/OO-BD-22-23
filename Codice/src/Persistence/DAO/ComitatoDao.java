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
    public void insertComitato(int numeroOrganizzatori, int idComitato) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String procedure = "inserisci_organizzatori";
        CallableStatement stm = conn.prepareCall ("CALL "+procedure+"(?,?)");
        stm.setInt(1, numeroOrganizzatori);
        stm.setInt(2, idComitato);
        stm.execute();
    }
}
