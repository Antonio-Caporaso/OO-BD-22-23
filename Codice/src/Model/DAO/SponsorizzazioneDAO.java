package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.organizzazione.Sponsorizzazione;

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
        String query = "DELETE FROM sponsor_conferenza WHERE id_conferenza = ?  and id_sponsor = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,s.getConferenza().getId_conferenza());
        stm.setInt(2,dao.getSponsorID(s.getSponsor()));
        stm.executeUpdate();
    }

    public LinkedList<Sponsorizzazione> retrieveSponsorizzazioni(Conferenza c) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT id_sponsor, contributo,valuta from sponsor_conferenza WHERE id_conferenza = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,c.getId_conferenza());
        LinkedList<Sponsorizzazione> sponsorizzazioni = new LinkedList<>();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            Sponsorizzazione sp = new Sponsorizzazione();
            SponsorDao dao = new SponsorDao();
            sp.setConferenza(c);
            sp.setSponsor(dao.getSponsorByID(rs.getInt("id_sponsor")));
            sp.setContributo(rs.getFloat("contributo"));
            sp.setValuta(rs.getString("valuta"));
            sponsorizzazioni.add(sp);
        }
        return sponsorizzazioni;
    }

    public int saveSponsorizzazione(Sponsorizzazione s) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from add_new_sponsorizzazione(?,?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(4,s.getConferenza().getId_conferenza());
        stm.setInt(1,s.getSponsor().getId_sponsor());
        stm.setDouble(2,s.getContributo());
        stm.setString(3,s.getCodiceValuta());
        ResultSet rs = stm.executeQuery();
        int result = 0;
        while(rs.next()){
            result = rs.getInt(1);
        }
        return result;
    }
    public LinkedList<String> retrieveSimboloValute() throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT simbolo from valuta";
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
