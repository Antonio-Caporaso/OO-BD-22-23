package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.organizzazione.Organizzatore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class OrganizzatoreDao {
    DBConnection dbcon;
    Connection conn;
    public Organizzatore retrieveOrganizzatoreByID(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM organizzatore WHERE idorganizzatore=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
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
    public LinkedList<Organizzatore> retrieveOrganizzatoriComitato(int idComitato) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from organizzatore o where idorganizzatore in (select organizzatore from membrocomitato m where comitato=?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,idComitato);
        ResultSet rs = stm.executeQuery();
        LinkedList<Organizzatore> membri = new LinkedList<>();
        while(rs.next()){
            Organizzatore o = new Organizzatore();
            o.setOrganizzatoreID(rs.getInt(1));
            o.setNome(rs.getString(2));
            o.setCognome(rs.getString(3));
            o.setTitolo(rs.getString(4));
            o.setIstituzione(rs.getString(5));
            o.setEmail(rs.getString(6));
            membri.add(o);
        }
        return membri;
    }
}
