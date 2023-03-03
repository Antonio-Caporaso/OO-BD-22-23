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
        SalaDao daosala = new SalaDao();
        ProgrammaDao programmaDao = new ProgrammaDao();
        String query = "SELECT * from sessione WHERE idconferenza = ?";
        PreparedStatement stm = conn.prepareStatement(query);
//        stm.setInt(1,conferenza.getConferenzaID());
        stm.setInt(1,53);
        LinkedList<Sessione> sessioni = new LinkedList<>();
        ResultSet rs  = stm.executeQuery();
        while(rs.next()){
            Sessione s = new Sessione();
            s.setSessioneID(rs.getInt(1));
            s.setTitolo(rs.getString(2));
            s.setDataInizio(rs.getDate(4));
            s.setDataFine(rs.getDate(5));
            s.setLocazione(daosala.retrieveSalaByID(rs.getInt(6)));
            s.setConferenza(conferenza);
            s.setProgramma(programmaDao.retrieveProgrammaByID(rs.getInt(7)));
            sessioni.add(s);
        }
        return sessioni;
    }
    public void saveSessione(Sessione sessione)throws SQLException{
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO sessione(idsessione, titolo, datainizio, datafine,idsala,idconferenza,ora_inizio,ora_fine) VALUES (default,?,?,?,?,?,?,?)");
        stm.setString(1, sessione.getTitolo());
        stm.setDate(2,sessione.getDataInizio());
        stm.setDate(3, sessione.getDataFine());
        stm.setInt(4, sessione.getLocazione().getSalaID());
        stm.setInt(5, sessione.getConferenza().getConferenzaID());
        stm.setTime(6, sessione.getOrarioInizio());
        stm.setTime(7,sessione.getOrarioFine());
        stm.execute();
    }
}
