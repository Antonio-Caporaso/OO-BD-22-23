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
            i.setOrario(rs.getTime("orario"));
            i.setEstratto(rs.getString("abstract"));
            interventi.add(i);
        }
        return interventi;
    }
}
