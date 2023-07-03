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
        String query = "SELECT * FROM organizzatore WHERE id_organizzatore=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
        Organizzatore org = new Organizzatore();
        EnteDao dao = new EnteDao();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            org.setOrganizzatoreID(id);
            org.setNome(rs.getString("nome"));
            org.setCognome(rs.getString("cognome"));
            org.setTitolo(rs.getString("titolo"));
            org.setIstituzione(dao.retrieveEnte(rs.getInt("id_ente")));
            org.setEmail(rs.getString("email"));
        }
        return org;
    }
    public LinkedList<Organizzatore> retrieveOrganizzatoriComitato(int idComitato) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from organizzatore o where id_organizzatore in (select id_organizzatore from organizzatore_comitato m where id_comitato=?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,idComitato);
        ResultSet rs = stm.executeQuery();
        EnteDao dao = new EnteDao();
        LinkedList<Organizzatore> membri = new LinkedList<>();
        while(rs.next()){
            Organizzatore o = new Organizzatore();
            o.setOrganizzatoreID(rs.getInt("id_organizzatore"));
            o.setNome(rs.getString("nome"));
            o.setCognome(rs.getString("cognome"));
            o.setTitolo(rs.getString("titolo"));
            o.setIstituzione(dao.retrieveEnte(rs.getInt("id_ente")));
            o.setEmail(rs.getString("email"));
            membri.add(o);
        }
        return membri;
    }
}
