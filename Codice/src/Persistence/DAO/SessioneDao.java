package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SessioneDao {
    private DBConnection dbcon;
    private Connection conn;
    public LinkedList<Sessione> retrieveSessioni(Conferenza conferenza) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        ChairDao daochair = new ChairDao();
        SalaDao daosala = new SalaDao();
        ProgrammaDao programmaDao = new ProgrammaDao();
        String query = "SELECT * fron sessione WHERE idconferenza = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,conferenza.getConferenzaID());
        LinkedList<Sessione> sessioni = new LinkedList<>();
        ResultSet rs  = stm.executeQuery();
        while(rs.next()){
            Sessione s = new Sessione();
            s.setSessioneID(rs.getInt(1));
            s.setTitolo(rs.getString(2));
            s.setCoordinatore(daochair.retrieveChairByID(rs.getInt(3)));
            s.setDataInizio(rs.getDate(4));
            s.setDataFine(rs.getDate(5));
            s.setLocazione(daosala.retrieveSalaByID(rs.getInt(6)));
            s.setConferenza(conferenza);
            s.setProgramma(programmaDao.retrieveProgrammaByID(rs.getInt(7)));
            sessioni.add(s);
        }
        return sessioni;
    }
}
