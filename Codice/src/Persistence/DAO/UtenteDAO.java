package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Exceptions.UtentePresenteException;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Ente;

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
            EnteDao dao = new EnteDao();
            while(rs.next()){
                int id = rs.getInt("id_user");
                String pwd = rs.getString("password");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String titolo = rs.getString("titolo");
                String email = rs.getString("email");
                String usrname = rs.getString("username");
                Ente istituzione = dao.retrieveEnte(rs.getInt("id_ente"));
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
            if(!userIsPresent(utente)){
                PreparedStatement query = conn.prepareStatement("INSERT INTO utente(password,nome,cognome,titolo,email,username,id_ente) VALUES (default,?,?,?,?,?,?,?)");
                query.setString(1,utente.getPassword());
                query.setString(2,utente.getNome());
                query.setString(3,utente.getCognome());
                query.setString(4,utente.getTitolo());
                query.setString(5, utente.getEmail());
                query.setString(6,utente.getUsername());
                query.setInt(7,utente.getIstituzione().getId_ente());
                query.executeUpdate();
            }else{
                throw new UtentePresenteException();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private boolean userIsPresent(Utente user){
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        boolean result = false;
        PreparedStatement stm = null;
        try{
            stm = conn.prepareStatement("SELECT count(*) from utente where username=?");
            stm.setString(1,user.getUsername());
            ResultSet rs= (stm.executeQuery());
            while(rs.next())
                result = (rs.getInt(1)==1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public Utente retrieveUtentebyID(int id) {
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        Utente user = null;
        try{
            String query = "SELECT * from utente where id_user = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            EnteDao dao = new EnteDao();
            while(rs.next()){
                String pwd = rs.getString("password");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String titolo = rs.getString("titolo");
                String email = rs.getString("email");
                String usrname = rs.getString("username");
                Ente istituzione = dao.retrieveEnte(rs.getInt("id_ente"));
                user = new Utente(id,titolo,usrname,pwd,nome,cognome,email,istituzione);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }
}
