package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Conferenza;
import Model.Entities.Sede;
import Model.Entities.Utente;
import org.postgresql.util.PGInterval;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ConferenzaDao {

    Connection conn = null;
    DBConnection dbcon = null;

    private void setConferenza(SedeDao sededao, UtenteDAO utentedao, LinkedList<Conferenza> conferenze, ResultSet rs, ComitatoDao comitatodao) throws SQLException {
        Conferenza c = new Conferenza();
        c.setId_conferenza(rs.getInt("id_conferenza"));
        c.setTitolo(rs.getString("titolo"));
        c.setInizio(rs.getTimestamp("inizio"));
        c.setFine(rs.getTimestamp("fine"));
        c.setDescrizione(rs.getString("descrizione"));
        c.setProprietario(utentedao.retrieveUtentebyID(rs.getInt("id_utente")));
        c.setComitato_s(comitatodao.retrieveComitatobyId(rs.getInt("comitato_s")));
        c.setComitato_l(comitatodao.retrieveComitatobyId(rs.getInt("comitato_l")));
        c.setSede(sededao.retrieveSedeByID(rs.getInt("id_sede")));
        conferenze.add(c);
    }

    public void deleteConferenza(Conferenza c) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "DELETE FROM conferenza where id_conferenza = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, c.getId_conferenza());
        stm.executeUpdate();
    }

    public List<Conferenza> getAllConferenzeByUtente(Utente user) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        UtenteDAO userDao = new UtenteDAO();
        SedeDao sedeDao = new SedeDao();
        LinkedList<Conferenza> conferenze = new LinkedList<>();
        String query = "SELECT * FROM conferenza where id_utente = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, user.getId_utente());
        ResultSet rs = stm.executeQuery();
        ComitatoDao comitatodao = new ComitatoDao();
        while (rs.next()) {
            Conferenza c = new Conferenza();
            c.setProprietario(user);
            c.setId_conferenza(rs.getInt("id_conferenza"));
            c.setTitolo(rs.getString("titolo"));
            c.setInizio(rs.getTimestamp("inizio"));
            c.setFine(rs.getTimestamp("fine"));
            c.setDescrizione(rs.getString("descrizione"));
            c.setComitato_s(comitatodao.retrieveComitatobyId(rs.getInt("comitato_s")));
            c.setComitato_l(comitatodao.retrieveComitatobyId(rs.getInt("comitato_l")));
            c.setSede(sedeDao.retrieveSedeByID(rs.getInt("id_sede")));
            conferenze.add(c);
        }
        rs.close();
        stm.close();
        conn.close();
        return conferenze;
    }

    public int getConferenceNumber() throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select count(*) from conferenza";
        PreparedStatement stm = conn.prepareStatement(query);
        int result = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }

    public LinkedList<Conferenza> retrieveByDateInterval(Date inizio, Date fine) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from show_conference_by_date(?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setDate(1, inizio);
        stm.setDate(2, fine);
        SedeDao sededao = new SedeDao();
        UtenteDAO utentedao = new UtenteDAO();
        LinkedList<Conferenza> conferenze = new LinkedList<>();
        ResultSet rs = stm.executeQuery();
        ComitatoDao comitatodao = new ComitatoDao();
        while (rs.next()) {
            setConferenza(sededao, utentedao, conferenze, rs, comitatodao);
        }
        return conferenze;
    }

    public LinkedList<Conferenza> retrieveByDateIntervalAndSede(Date inizio, Date fine, Sede sede) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from show_conferences_by_sede_and_date(?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setDate(2, inizio);
        stm.setDate(3, fine);
        stm.setInt(1, sede.getSedeID());
        SedeDao sededao = new SedeDao();
        UtenteDAO utentedao = new UtenteDAO();
        LinkedList<Conferenza> conferenze = new LinkedList<>();
        ResultSet rs = stm.executeQuery();
        ComitatoDao comitatodao = new ComitatoDao();
        while (rs.next()) {
            setConferenza(sededao, utentedao, conferenze, rs, comitatodao);
        }
        return conferenze;
    }

    public Conferenza retrieveConferenzaByID(int idConferenza) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM conferenza where id_conferenza =?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, idConferenza);
        ResultSet rs = stm.executeQuery();
        Conferenza c = new Conferenza();
        ComitatoDao comitatodao = new ComitatoDao();
        UtenteDAO utentedao = new UtenteDAO();
        SedeDao sededao = new SedeDao();
        while (rs.next()) {
            c.setId_conferenza(idConferenza);
            c.setTitolo(rs.getString("titolo"));
            c.setInizio(rs.getTimestamp("inizio"));
            c.setFine(rs.getTimestamp("fine"));
            c.setDescrizione(rs.getString("descrizione"));
            c.setProprietario(utentedao.retrieveUtentebyID(rs.getInt("id_utente")));
            c.setComitato_s(comitatodao.retrieveComitatobyId(rs.getInt("comitato_s")));
            c.setComitato_l(comitatodao.retrieveComitatobyId(rs.getInt("comitato_l")));
            c.setSede(sededao.retrieveSedeByID(rs.getInt("id_sede")));
        }
        return c;
    }

    public int saveConferenza(Conferenza c) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from add_conferenza_details(?,?,?,?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, c.getTitolo());
        stm.setTimestamp(2, c.getInizio());
        stm.setTimestamp(3, c.getFine());
        stm.setInt(4, c.getSede().getSedeID());
        stm.setString(5, c.getDescrizione());
        stm.setInt(6, c.getProprietario().getId_utente());
        ResultSet rs = stm.executeQuery();
        int result = 0;
        while (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }
    public void updateDettagliConferenza(Conferenza conferenza) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String update = "UPDATE conferenza SET titolo=?,descrizione=?,inizio=?,fine=?,id_sede=? WHERE id_conferenza=?";
        PreparedStatement stm = conn.prepareStatement(update);
        stm.setString(1, conferenza.getTitolo());
        stm.setString(2, conferenza.getDescrizione());
        stm.setTimestamp(3, conferenza.getInizio());
        stm.setTimestamp(4, conferenza.getFine());
        stm.setInt(5, conferenza.getSede().getSedeID());
        stm.setInt(6, conferenza.getId_conferenza());
        stm.executeUpdate();
    }
}
