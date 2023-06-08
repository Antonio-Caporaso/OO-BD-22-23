package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Intervento;
import Persistence.Entities.Conferenze.Programma;

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
        String query = "SELECT * from intervento where idprogramma=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,programma.getProgrammaID());
        ResultSet rs = stm.executeQuery();
        SpeakerDao dao = new SpeakerDao();
        while(rs.next()){
            Intervento i = new Intervento();
            i.setInterventoID(rs.getInt("idintervento"));
            i.setTitolo(rs.getString("titolo"));
            i.setSpeaker(dao.retrieveSpeakerByID(rs.getInt("idspeaker")));
            i.setOrario(rs.getTimestamp("orario"));
            i.setEstratto(rs.getString("abstract"));
            interventi.add(i);
        }
        return interventi;
    }
    public void saveIntervento(Intervento intervento) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "INSERT INTO intervento values (default,?,?,?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,intervento.getSpeaker().getIdSpeaker());
        stm.setInt(2,intervento.getProgramma().getProgrammaID());
        stm.setString(3,intervento.getEstratto());
        stm.setString(4,intervento.getTitolo());
        stm.setTimestamp(5,intervento.getOrario());
        stm.executeUpdate();
    }

    public int addIntervento(Intervento intervento) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from save_intervento(?,?,?,?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,intervento.getProgramma().getProgrammaID());
        stm.setInt(2,intervento.getSpeaker().getIdSpeaker());
        stm.setString(3,intervento.getTitolo());
        stm.setString(4,intervento.getEstratto());
        stm.setTimestamp(5,intervento.getOrario());
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
        String query = "delete from intervento where idintervento=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,intervento.getInterventoID());
        stm.executeUpdate();
    }

    public void updateIntervento(Intervento i) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "udpdate intervento set titolo=?,idspeaker=?,orario=?,abstract=? where idintervento=?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1,i.getTitolo());
        stm.setString(4,i.getEstratto());
        stm.setInt(2,i.getSpeaker().getIdSpeaker());
        stm.setTimestamp(3,i.getOrario());
        stm.setInt(5,i.getInterventoID());
        stm.executeUpdate();
    }
}
