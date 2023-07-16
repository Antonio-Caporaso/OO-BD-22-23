package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.Programma;
import Utilities.Stats;
import org.postgresql.util.PGInterval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class InterventoDao {
    private DBConnection dbcon;
    private Connection conn;
    public LinkedList<Intervento> retrieveInterventiByProgramma(Programma programma) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        LinkedList<Intervento> interventi = new LinkedList<>();
        String query = "SELECT * from intervento where id_programma=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,programma.getProgrammaID());
        ResultSet rs = stm.executeQuery();
        SpeakerDao dao = new SpeakerDao();
        while(rs.next()){
            Intervento i = new Intervento();
            i.setId_intervento(rs.getInt("id_intervento"));
            i.setProgramma(programma);
            i.setTitolo(rs.getString("titolo"));
            i.setSpeaker(dao.retrieveSpeakerByID(rs.getInt("id_speaker")));
            i.setInizio(rs.getTimestamp("inizio"));
            i.setFine(rs.getTimestamp("fine"));
            i.setEstratto(rs.getString("abstract"));
            interventi.add(i);
        }
        return interventi;
    }

    public int createIntervento(Intervento intervento, PGInterval durata) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from add_new_intervento(?,?,?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1,intervento.getTitolo());
        stm.setString(2,intervento.getEstratto());
        stm.setInt(3,intervento.getSpeaker().getIdSpeaker());
        stm.setInt(4,intervento.getProgramma().getProgrammaID());
        stm.setObject(5,durata);
        ResultSet rs = stm.executeQuery();
        int result = 0;
        while(rs.next()){
            result = rs.getInt(1);
        }
        return result;
    }
    public void removeIntervento(Intervento intervento) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "delete from intervento where id_intervento=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,intervento.getId_intervento());
        stm.executeUpdate();
    }
    public void updateIntervento(Intervento i) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "udpdate intervento set titolo=?,idspeaker=?,inizio=?,fine=?,abstract=? where id_intervento=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1,i.getTitolo());
        stm.setInt(2,i.getSpeaker().getIdSpeaker());
        stm.setTimestamp(3,i.getInizio());
        stm.setTimestamp(4,i.getFine());
        stm.setString(5,i.getEstratto());
        stm.setInt(6,i.getId_intervento());
        stm.executeUpdate();
    }
    public LinkedList<Stats> retrieveInterventiStatsByMonth(Integer month, Integer year) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from show_percentage_interventi(?,?)"; //Da migliorare nella schermata in modo tale da far selezionare l'anno
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,month);
        stm.setInt(2,year);
        ResultSet rs = stm.executeQuery();
        LinkedList<Stats> stats = new LinkedList<>();
        while(rs.next()){
            Stats s = new Stats();
            s.setIstituzione(rs.getString(1));
            s.setPercentuale(rs.getDouble(2));
            stats.add(s);
        }
        return stats;
    }
    public LinkedList<Stats> retrieveInterventiStatsByYear(Integer i) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from show_percentage_interventi(?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,i);
        ResultSet rs = stm.executeQuery();
        LinkedList<Stats> stats = new LinkedList<>();
        while(rs.next()){
            Stats s = new Stats();
            s.setIstituzione(rs.getString(1));
            s.setPercentuale(rs.getDouble(2));
            stats.add(s);
        }
        return stats;
    }
}
