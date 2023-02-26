package DAO;

import DbConfig.DBConnection;

import Model.Conferenze.Conferenza;
import Model.Conferenze.Sede;
import Model.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConferenzaDao {

    Connection conn = null;
    DBConnection dbcon = null;
    public List<Conferenza> getAllConferenzeByUtente(Utente user) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        UtenteDAO userDao = new UtenteDAO();
        SedeDao sedeDao = new SedeDao();
        List<Conferenza> list = new ArrayList<>();
        String query = "SELECT * FROM conferenza where proprietario = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,user.getIdUtente());
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            int id = rs.getInt(1);
            String nome = rs.getString(2);
            Date datainizio = rs.getDate(3);
            Date datafine = rs.getDate(4);
            String descrizione = rs.getString(5);
            Sede sede = sedeDao.retrieveSedeByID(rs.getInt(6));
            float budget = rs.getFloat(7);
            Utente proprietario = userDao.retrieveUtentebyID(rs.getInt("proprietario"));
            Conferenza c = new Conferenza(id,nome,datainizio,datafine,descrizione,sede,budget,proprietario);
            list.add(c);
        }
        return list;
    }
    public LinkedList<Conferenza> getAllConferenze() throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        LinkedList<Conferenza> conferenze = new LinkedList<>();
        String query = "SELECT * FROM conferenza";
        PreparedStatement stm = conn.prepareStatement(query);

        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            // logic
        }
        return conferenze;
    }

    public void saveConferenza(Conferenza c) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO conferenza(idconferenza, nome, descrizione, datainizio, datafine, proprietario) VALUES (default,?,?,?,?,?)");
        stm.setString(1, c.getNome());
        stm.setString(2, c.getDescrizione());
        stm.setDate(3, c.getDataInizio());
        stm.setDate(4, c.getDataFine());
        stm.setInt(5, c.getProprietario().getIdUtente());
        stm.executeUpdate();
    }

    public void deleteConferenza(Conferenza c) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn=dbcon.getConnection();
        String query = "DELETE FROM conferenza where idconferenza = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,c.getConferenzaID());
        stm.executeUpdate();
    }
}
