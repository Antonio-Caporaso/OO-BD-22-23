package DAO;

import DbConfig.DBConnection;
import Model.Conferenze.Conferenza;
import Model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConferenzaDao {
    private Connection conn = null;
    private DBConnection dbcon = null;
    private UtenteDAO utenteDAO;
    private ComitatoDao comitatoDao;
    private SedeDao sedeDao;
    public List<Conferenza> getAllConferenzeByUtente(Utente user) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        List<Conferenza> list = new ArrayList<>();
        String query = "SELECT * FROM conferenza where proprietario = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,user.getIdUtente());
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            // Logica di recupero
        }
        return list;
    }
    public List<Conferenza> getAllConferenze() throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        List<Conferenza> conferenze = new LinkedList<>();
        String query = "SELECT * FROM conferenza";
        PreparedStatement stm = conn.prepareStatement(query);
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            // logic
        }
        return conferenze;
    }

    public void saveConferenza(Conferenza c) {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        try{
            PreparedStatement stm = conn.prepareStatement("INSERT INTO conferenza(id, nome, descrizione datainizio, datafine, proprietario) VALUES (default,?,?,?,?,?)");
            stm.setString(1,c.getNome());
            stm.setString(2,c.getDescrizione());
            stm.setString(3,c.getDataInizio());
            stm.setString(4,c.getDataFine());
            stm.setString(5,c.getProprietario());
            stm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
