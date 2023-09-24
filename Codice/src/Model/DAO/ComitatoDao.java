package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Organizzazione.Comitato;
import Model.Entities.Organizzazione.Organizzatore;

import java.sql.*;
import java.util.LinkedList;

public class ComitatoDao {
    Connection conn = null;
    DBConnection dbcon = null;
    public Comitato retrieveComitatobyId(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from comitato where id_comitato=?";
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
    public Comitato retrieveComitatoScientificoByConferenza(Conferenza conferenza) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select comitato_s from conferenza where id_conferenza = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, conferenza.getId_conferenza());
        ResultSet rs = stm.executeQuery();
        Comitato comitato_s = new Comitato();
        while (rs.next()) {
            int id = rs.getInt(1);
            comitato_s.setId_comitato(id);
        }
        return comitato_s;
    }
    public Comitato retrieveComitatoLocaleByConferenza(Conferenza conferenza) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select comitato_l from conferenza where id_conferenza = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, conferenza.getId_conferenza());
        ResultSet rs = stm.executeQuery();
        Comitato comitato_l = new Comitato();
        while (rs.next()) {
            int id = rs.getInt(1);
            comitato_l.setId_comitato(id);
        }
        return comitato_l;
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
    public LinkedList<Organizzatore> retreiveMembriComitato(Comitato comitato) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select o.id_organizzatore,o.nome,o.cognome,o.email,o.titolo,o.id_ente from organizzatore o natural join organizzatore_comitato where id_comitato = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,comitato.getId_comitato());
        ResultSet rs = stm.executeQuery();
        LinkedList<Organizzatore> membri = new LinkedList<>();
        EnteDao enteDao = new EnteDao();
        while(rs.next()){
            Organizzatore o = new Organizzatore();
            o.setId_organizzatore(rs.getInt(1));
            o.setNome(rs.getString(2));
            o.setCognome(rs.getString(3));
            o.setEmail(rs.getString(4));
            o.setTitolo(rs.getString(5));
            o.setIstituzione(enteDao.retrieveEnte(rs.getInt(6)));
            membri.add(o);
        }
        return membri;
    }
}
