package DAO;

import DbConfig.DBConnection;
import Exceptions.UtentePresenteException;
import Model.Utente;

import java.sql.*;

public class UtenteDAO {
    Connection conn = null;
    DBConnection dbCon = null;
    public String getPasswordByUsername(String username){
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        String pwd = null;
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT pwd FROM utente WHERE username=?");
            stm.setString(1, username);
            ResultSet result = stm.executeQuery();
            while(result.next()) {
                pwd = result.getString(1);
            }
            result.close();
            stm.close();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return pwd;
    }
    public void saveUtente(Utente utente) throws UtentePresenteException{
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        try{
            if(userIsPresentByUsername(utente.getUsername())){
                PreparedStatement query = conn.prepareStatement("INSERT INTO utente VALUES (default,?,?,?,?,?,?)");
                query.setString(1,utente.getPassword());
                query.setString(2,utente.getNome());
                query.setString(3,utente.getCognome());
                query.setString(4,utente.getTitolo());
                query.setString(5, utente.getEmail());
                query.setString(6,utente.getUsername());
                //query.setString(7,utente.getIstituzione());
                query.executeUpdate();
            }else{
                throw new UtentePresenteException();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private boolean userIsPresentByUsername(String username){
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        boolean result = false;
        PreparedStatement stm = null;
        try{
            stm = conn.prepareStatement("SELECT count(*) from utente where username=?");
            stm.setString(1,username);
            ResultSet rs= (stm.executeQuery());
            while(rs.next())
                result = (rs.getInt(1)==0);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}
