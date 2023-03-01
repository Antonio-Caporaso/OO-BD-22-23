package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.partecipanti.Chair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChairDao {
    private DBConnection dbcon;
    private Connection conn;
    public Chair retrieveChairByID(int ID) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        Chair chair = new Chair();
        String query = "SELECT * FROM chair where idchair = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,ID);
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            chair.setIdChair(ID);
            chair.setNome(rs.getString(2));
            chair.setCognome(rs.getString(3));
            chair.setTitolo(rs.getString(4));
            chair.setIstituzione(rs.getString(5));
        }
        return chair;
    }
}
