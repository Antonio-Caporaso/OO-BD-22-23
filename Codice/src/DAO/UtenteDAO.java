package DAO;

import DbConfig.DBConnection;
import Exceptions.UtentePresenteException;
import Model.Utente;

import java.sql.*;

public class UtenteDAO {
    Connection conn = null;
    DBConnection dbCon = null;
    public Utente retrieveUtentebyUsername(String username){
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        Utente user = null;
        try{
            String query = "SELECT * from utente where username = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("iduser");
                String pwd = rs.getString("pwd");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String titolo = rs.getString("titolo");
                String email = rs.getString("email");
                String usrname = rs.getString("username");
                String istituzione = rs.getString("istituzione");
                user = new Utente(id,titolo,usrname,pwd,nome,cognome,email,istituzione);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }
    public void saveUtente(Utente utente) throws UtentePresenteException{
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        try{
            if(userIsPresentByUsername(utente.getUsername())){
                PreparedStatement query = conn.prepareStatement("INSERT INTO utente VALUES (default,?,?,?,?,?,?,?)");
                query.setString(1,utente.getPassword());
                query.setString(2,utente.getNome());
                query.setString(3,utente.getCognome());
                query.setString(4,utente.getTitolo());
                query.setString(5, utente.getEmail());
                query.setString(6,utente.getUsername());
                query.setString(7,utente.getIstituzione());
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
