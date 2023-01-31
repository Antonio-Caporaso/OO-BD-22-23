package DAO;

import DbConfig.DBConnection;

import java.sql.*;

public class UtenteDAO {
    public String getPasswordByUsername(String username){
        Connection conn = null;
        DBConnection dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        PreparedStatement stm = null;
        String pwd = null;
        try{
            stm = conn.prepareStatement("SELECT pwd FROM utente WHERE username=?");
            stm.setString(1, username);
            ResultSet result = stm.executeQuery();
            while(result.next()) {
                pwd = result.getString(1);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return pwd;
    }
}
