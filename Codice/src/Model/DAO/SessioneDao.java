package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Conferenza;
import Model.Entities.Sessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SessioneDao {
    private Connection conn;
    private DBConnection dbcon;

    public int getNumeroPartecipanti() throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT COUNT(*) from partecipazione";
        PreparedStatement stm = conn.prepareStatement(query);
        int result = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }

    public void removeSessione(Sessione sessione) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "delete from sessione where id_sessione = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, sessione.getId_sessione());
        statement.executeUpdate();
    }

    public Sessione retrieveSessioneById(int idSessione) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM sessione where id_sessione=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, idSessione);
        Sessione s = new Sessione();
        ResultSet rs = stm.executeQuery();
        ConferenzaDao dao = new ConferenzaDao();
        OrganizzatoreDao organizzatoreDao = new OrganizzatoreDao();
        SalaDao salaDao = new SalaDao();
        ProgrammaDao programmaDao = new ProgrammaDao();
        while (rs.next()) {
            s.setId_sessione(idSessione);
            s.setConferenza(dao.retrieveConferenzaByID(rs.getInt("id_conferenza")));
            s.setTitolo(rs.getString("titolo"));
            s.setInizio(rs.getTimestamp("inizio"));
            s.setFine(rs.getTimestamp("fine"));
            s.setCoordinatore(organizzatoreDao.retrieveOrganizzatoreByID(rs.getInt("id_coordinatore")));
            s.setLocazione(salaDao.retrieveSalaByID(rs.getInt("id_sala")));
            s.setProgramma(programmaDao.retrieveProgrammaBySessione(s));
        }
        return s;
    }

    public LinkedList<Sessione> retrieveSessioniByConferenza(Conferenza conferenza) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        OrganizzatoreDao dao = new OrganizzatoreDao();
        String query = "SELECT * from sessione WHERE id_conferenza = ? order by inizio";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1, conferenza.getId_conferenza());
        LinkedList<Sessione> sessioni = new LinkedList<>();
        SalaDao salaDao = new SalaDao();
        OrganizzatoreDao organizzatoreDao = new OrganizzatoreDao();
        ResultSet rs = stm.executeQuery();
        ProgrammaDao programmaDao = new ProgrammaDao();
        while (rs.next()) {
            Sessione s = new Sessione();
            s.setConferenza(conferenza);
            int id = rs.getInt("id_sessione");
            s.setId_sessione(id);
            s.setTitolo(rs.getString("titolo"));
            s.setInizio(rs.getTimestamp("inizio"));
            s.setFine(rs.getTimestamp("fine"));
            s.setCoordinatore(organizzatoreDao.retrieveOrganizzatoreByID(rs.getInt("id_coordinatore")));
            s.setLocazione(salaDao.retrieveSalaByID(rs.getInt("id_sala")));
            s.setProgramma(programmaDao.retrieveProgrammaBySessione(s));
            sessioni.add(s);
        }
        return sessioni;
    }

    public int saveSessione(Sessione sessione) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from add_new_sessione(?,?,?,?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, sessione.getTitolo());
        stm.setTimestamp(2, sessione.getInizio());
        stm.setTimestamp(3, sessione.getFine());
        stm.setInt(4, sessione.getLocazione().getSalaID());
        stm.setInt(5, sessione.getConferenza().getId_conferenza());
        stm.setInt(6, sessione.getCoordinatore().getId_organizzatore());
        ResultSet rs = stm.executeQuery();
        int result = 0;
        if (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }

    public void updateSessione(Sessione sessione) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "UPDATE sessione set titolo = ?,inizio=?, fine=?,id_sala=?,id_coordinatore=? where id_sessione=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, sessione.getTitolo());
        stm.setTimestamp(2, sessione.getInizio());
        stm.setTimestamp(3, sessione.getFine());
        stm.setInt(4, sessione.getLocazione().getSalaID());
        stm.setInt(5, sessione.getCoordinatore().getId_organizzatore());
        stm.setInt(6, sessione.getId_sessione());
        stm.executeUpdate();
    }
}
