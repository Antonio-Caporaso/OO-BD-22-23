package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.DTO.Conferenze.Conferenza;
import Persistence.DTO.organizzazione.Sponsorizzazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SponsorizzazioneDAO {
    private Connection conn = null;
    private DBConnection dbcon = null;

    public LinkedList<Sponsorizzazione> retrieveSponsorizzazioni(Conferenza c) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT idsponsor, contributo from sponsorizzazione WHERE idconferenza = ?";
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
            sponsorizzazioni.add(sp);
        }
        return sponsorizzazioni;
    }
}
