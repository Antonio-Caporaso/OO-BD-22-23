package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.organizzazione.Comitato;
import Model.Entities.organizzazione.Organizzatore;

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
        OrganizzatoreDao organizzatoreDao = new OrganizzatoreDao();
        while (rs.next()) {
            comitato_s.setId_comitato(rs.getInt(1));
            comitato_s.setMembri(organizzatoreDao.retrieveOrganizzatoriComitato(comitato_s.getId_comitato()));
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
        OrganizzatoreDao organizzatoreDao = new OrganizzatoreDao();
        while (rs.next()) {
            comitato_l.setId_comitato(rs.getInt(1));
            comitato_l.setMembri(organizzatoreDao.retrieveOrganizzatoriComitato(comitato_l.getId_comitato()));
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
        String query = "select o.nome,o.cognome,o.email,o.titolo,o.id_ente from organizzatore o natural join organizzatore_comitato where id_comitato = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,comitato.getId_comitato());
        ResultSet rs = stm.executeQuery();
        LinkedList<Organizzatore> membri = new LinkedList<>();
        EnteDao enteDao = new EnteDao();
        while(rs.next()){
            Organizzatore o = new Organizzatore();
            o.setNome(rs.getString(1));
            o.setCognome(rs.getString(2));
            o.setEmail(rs.getString(3));
            o.setTitolo(rs.getString(4));
            o.setIstituzione(enteDao.retrieveEnte(rs.getInt(5)));
            membri.add(o);
        }
        return membri;
    }
}
