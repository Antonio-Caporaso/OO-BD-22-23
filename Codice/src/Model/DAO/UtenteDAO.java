package Model.DAO;

import Model.DbConfig.DBConnection;
import Exceptions.UtentePresenteException;
import Model.Entities.Utente;
import Model.Entities.Organizzazione.Ente;

import java.sql.*;

public class UtenteDAO {
    Connection conn = null;
    DBConnection dbCon = null;
    public Utente retrieveUtentebyUsername(String urn) throws SQLException {
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        String query = "SELECT * from utente where username = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, urn);
        ResultSet rs = stm.executeQuery();
        EnteDao dao = new EnteDao();
        Utente user = new Utente();
        while(rs.next()){
            user.setId_utente(rs.getInt("id_utente"));
            user.setPassword(rs.getString("password"));
            user.setNome(rs.getString("nome"));
            user.setCognome(rs.getString("cognome"));
            user.setTitolo(rs.getString("titolo"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setIstituzione(dao.retrieveEnte(rs.getInt("id_ente")));
        }
        return user;
    }
    public void saveUtente(Utente utente) throws UtentePresenteException, SQLException {
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        if(!userIsPresent(utente)){
            PreparedStatement query = conn.prepareStatement("INSERT INTO utente(password,nome,cognome,titolo,email,username,id_ente) VALUES (?,?,?,?,?,?,?)");
            query.setString(1,utente.getPassword());
            query.setString(2,utente.getNome());
            query.setString(3,utente.getCognome());
            query.setObject(4,utente.getTitolo(), Types.OTHER);
            query.setString(5,utente.getEmail());
            query.setString(6,utente.getUsername());
            query.setInt(7,utente.getIstituzione().getID());
            query.executeUpdate();
        }else
            throw new UtentePresenteException();
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
            String query = "SELECT * from utente where id_utente = ?";
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
