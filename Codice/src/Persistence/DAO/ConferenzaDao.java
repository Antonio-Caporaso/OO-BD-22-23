package Persistence.DAO;

import Persistence.DbConfig.DBConnection;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sede;
import Persistence.Entities.Utente;

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
        SedeDao daosede = new SedeDao();
        UtenteDAO utentedao = new UtenteDAO();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            Conferenza c = new Conferenza();
            c.setConferenzaID(rs.getInt(1));
            c.setNome(rs.getString("nome"));
            c.setDescrizione(rs.getString("descrizione"));
            c.setBudget(rs.getFloat("budget"));
            c.setSede(daosede.retrieveSedeByID(rs.getInt("sede")));
            c.setProprietario(utentedao.retrieveUtentebyID(rs.getInt("proprietario")));
            c.setDataInizio(rs.getDate("datainizio"));
            c.setDataFine(rs.getDate("datafine"));
            conferenze.add(c);
        }
        return conferenze;
    }

    public void saveConferenza(Conferenza c) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String procedure = "insert_conferenza";
        CallableStatement stm = conn.prepareCall ("CALL "+procedure+"(?,?,?,?,?,?,?)");
        stm.setString(1, c.getNome());
        stm.setString(2, c.getDescrizione());
        stm.setDate(3, c.getDataInizio());
        stm.setDate(4, c.getDataFine());
        stm.setFloat(5,c.getBudget());
        stm.setInt(6, c.getProprietario().getIdUtente());
        stm.setInt(7,c.getSede().getSedeID());
        stm.execute();
    }

    public void deleteConferenza(Conferenza c) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn=dbcon.getConnection();
        String query = "DELETE FROM conferenza where idconferenza = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,c.getConferenzaID());
        stm.executeUpdate();
    }

    public void updateDettagliConferenza(Conferenza conferenza) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String update = "UPDATE conferenza SET nome=?,descrizione=?,datainizio=?,datafine=?,budget=? WHERE idconferenza=?";
        PreparedStatement stm = conn.prepareStatement(update);
        stm.setString(1,conferenza.getNome());
        stm.setString(2,conferenza.getDescrizione());
        stm.setDate(3,conferenza.getDataInizio());
        stm.setDate(4,conferenza.getDataFine());
        stm.setFloat(5,conferenza.getBudget());
        stm.setInt(6,conferenza.getConferenzaID());
        stm.executeUpdate();
    }

}
