package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Organizzatore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class OrganizzatoreDao {
    DBConnection dbcon;
    Connection conn;

    public LinkedList<Organizzatore> retrieveOrganizzatoreByEnte(int ente) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM organizzatore WHERE id_ente=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, ente);
        LinkedList<Organizzatore> organizzatori = new LinkedList<>();
        EnteDao dao = new EnteDao();
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Organizzatore org = new Organizzatore();
            org.setId_organizzatore(rs.getInt("id_organizzatore"));
            org.setNome(rs.getString("nome"));
            org.setCognome(rs.getString("cognome"));
            org.setTitolo(rs.getString("titolo"));
            org.setIstituzione(dao.retrieveEnte(rs.getInt("id_ente")));
            org.setEmail(rs.getString("email"));
            organizzatori.add(org);
        }
        return organizzatori;
    }

    public Organizzatore retrieveOrganizzatoreByID(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM organizzatore WHERE id_organizzatore=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, id);
        Organizzatore org = new Organizzatore();
        EnteDao dao = new EnteDao();
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            org.setId_organizzatore(id);
            org.setNome(rs.getString("nome"));
            org.setCognome(rs.getString("cognome"));
            org.setTitolo(rs.getString("titolo"));
            org.setIstituzione(dao.retrieveEnte(rs.getInt("id_ente")));
            org.setEmail(rs.getString("email"));
        }
        return org;
    }
}
