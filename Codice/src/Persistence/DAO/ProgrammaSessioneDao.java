package Persistence.DAO;
import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Programma;
import Persistence.Entities.Conferenze.Sessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgrammaSessioneDao {
    private DBConnection dbcon;
    private Connection conn;

//    public Programma retrieveProgrammaBySessione(Programma programma) throws SQLException {
//        dbcon = DBConnection.getDBconnection();
//        conn = dbcon.getConnection();
//        String query = "SELECT intervento.titolo, intervallo.tipologia AS intervallo_tipologia, eventosociale.tipologia AS eventosociale_tipologia, " +
//                        "intervento.orario AS intervento_orario, intervallo.orario AS intervallo_orario, eventosociale.orario AS eventosociale_orario " +
//                        "FROM intervento " +
//                        "LEFT JOIN intervallo ON intervento.idprogramma = intervallo.id_programma " +
//                        "LEFT JOIN eventosociale ON intervento.idprogramma = eventosociale.id_programma";
//        PreparedStatement stm = conn.prepareStatement(query);
//        stm.setInt(1, programma.getProgrammaID());
//        ResultSet rs = stm.executeQuery();
//
//    }
}
