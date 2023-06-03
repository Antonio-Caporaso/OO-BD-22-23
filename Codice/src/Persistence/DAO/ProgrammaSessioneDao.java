package Persistence.DAO;
import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.organizzazione.ActivityModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class ProgrammaSessioneDao {
    private DBConnection dbcon;
    private Connection conn;

    public LinkedList<ActivityModel> retrieveProgrammaBySessione(Programma programma) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT " +
                "Intervento.titolo AS intervento_titolo, " +
                "Intervento.orario AS intervento_orario, " +
                "Intervallo.tipologia AS intervallo_tipologia, " +
                "Intervallo.orario AS intervallo_orario, " +
                "Eventosociale.tipologia AS eventosociale_tipologia, " +
                "Eventosociale.orario AS eventosociale_orario " +
                "FROM " +
                "Intervento " +
                "LEFT JOIN " +
                "Intervallo ON Intervento.idprogramma = Intervallo.id_programma " +
                "LEFT JOIN " +
                "Eventosociale ON Intervento.idprogramma = Eventosociale.id_programma " +
                "WHERE " +
                "Intervento.idprogramma = ? " +
                "ORDER BY Intervento.orario, Intervallo.orario, Eventosociale.orario";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,programma.getProgrammaID());
        LinkedList<ActivityModel> activityModelLinkedList = new LinkedList<>();
        ResultSet rs  = stm.executeQuery();
        while(rs.next()){
            ActivityModel activity = new ActivityModel();
            activity.setAttivita(rs.getString(1));
            activity.setDataInizio(rs.getTimestamp(2));
            activityModelLinkedList.add(activity);
        }
        return activityModelLinkedList;
    }
}
