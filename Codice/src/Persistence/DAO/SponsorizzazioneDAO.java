package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Sponsorizzazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SponsorizzazioneDAO {
    private Connection conn = null;
    private DBConnection dbcon = null;

    public void removeSponsorizzazione(Sponsorizzazione s) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        SponsorDao dao = new SponsorDao();
        int id = dao.getSponsorID(s.getSponsor());
        String query = "DELETE FROM SPONSORIZZAZIONE WHERE idconferenza = ?  and idsponsor = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,s.getConferenza().getConferenzaID());
        stm.setInt(2,id);
        stm.executeUpdate();
    }

    public LinkedList<Sponsorizzazione> retrieveSponsorizzazioni(Conferenza c) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT idsponsor, contributo,valuta from sponsorizzazione WHERE idconferenza = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,c.getConferenzaID());
        LinkedList<Sponsorizzazione> sponsorizzazioni = new LinkedList<>();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            Sponsorizzazione sp = new Sponsorizzazione();
            SponsorDao dao = new SponsorDao();
            sp.setConferenza(c);
            sp.setSponsor(dao.getSponsorByID(rs.getInt(1)));
            sp.setContributo(rs.getFloat(2));
            sp.setValuta(rs.getString(3));
            sponsorizzazioni.add(sp);
        }
        return sponsorizzazioni;
    }

    public void saveSponsorizzazione(Sponsorizzazione s) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "INSERT INTO sponsorizzazione VALUES (?,?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,s.getConferenza().getConferenzaID());
        stm.setInt(2,s.getSponsor().getSponsorID());
        stm.setDouble(3,s.getContributo());
        stm.setString(4,s.getCodiceValuta());
        stm.executeUpdate();
    }
    public LinkedList<String> retrieveSimboloValute() throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT simbolo from valute";
        PreparedStatement stm = conn.prepareStatement(query);
        LinkedList<String> valute = new LinkedList<>();
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            String s = rs.getString(1);
            valute.add(s);
        }
        return valute;
    }
}
