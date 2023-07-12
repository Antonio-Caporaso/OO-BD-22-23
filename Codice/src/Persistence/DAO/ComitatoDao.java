package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.organizzazione.Comitato;
import Persistence.Entities.organizzazione.Organizzatore;

import java.sql.*;
import java.util.LinkedList;

public class ComitatoDao {
    Connection conn = null;
    DBConnection dbcon = null;
    public Comitato retrieveComitatobyId(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from comitato where id_comitato=(?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
        ResultSet rs = stm.executeQuery();
        OrganizzatoreDao dao = new OrganizzatoreDao();
        Comitato c = new Comitato();
        while(rs.next()){
            c.setId_comitato(rs.getInt("id_comitato"));
            c.setTipologia(rs.getString("tipologia"));
        }
        return c;
    }
    public void addMembroComitato(Organizzatore organizzatore, Comitato comitato) throws SQLException{
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "CALL add_membro_comitato(?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,organizzatore.getId_organizzatore());
        stm.setInt(2,comitato.getId_comitato());
        stm.executeUpdate();
    }
    public void removeMembroComitato (Organizzatore organizzatore, Comitato comitato) throws SQLException{
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "DELETE FROM organizzatore_comitato WHERE id_organizzatore=? AND id_comitato=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,organizzatore.getId_organizzatore());
        stm.setInt(2,comitato.getId_comitato());
        stm.executeUpdate();
    }
    public Comitato retreiveMembriComitatoScientificoByIDComitato(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from  show_comitato_scientifico(?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
        ResultSet rs = stm.executeQuery();
        OrganizzatoreDao dao = new OrganizzatoreDao();
        Comitato c = new Comitato();
        while(rs.next()){
            c.setId_comitato(rs.getInt("id_comitato"));
            c.setTipologia(rs.getString("tipologia"));
        }
        return c;
    }

}
