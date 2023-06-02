package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sede;
import Persistence.Entities.Utente;
import Persistence.Entities.organizzazione.Comitato;

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
        ComitatoDao comitatodao = new ComitatoDao();
        while(rs.next())
        {
            int id = rs.getInt("idconferenza");
            String nome = rs.getString("nome");
            Timestamp datainizio = rs.getTimestamp("datainizio");
            Timestamp datafine = rs.getTimestamp("datafine");
            String descrizione = rs.getString("descrizione");
            Comitato scientific =  comitatodao.retrieveComitatobyId(rs.getInt("comitatoscientifico"));
            Comitato local = comitatodao.retrieveComitatobyId(rs.getInt("comitatolocale"));
            Sede sede = sedeDao.retrieveSedeByID(rs.getInt("idsede"));
            float budget = rs.getFloat("budget");
            Utente proprietario = userDao.retrieveUtentebyID(rs.getInt("proprietario"));
            Conferenza c = new Conferenza(id,nome,proprietario,datainizio,datafine,descrizione,local,scientific,sede,budget);
            c.setValuta(rs.getString("valuta"));
            list.add(c);
        }
        rs.close();
        stm.close();
        conn.close();
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
        ComitatoDao comitatodao = new ComitatoDao();
        while(rs.next()){
            Conferenza c = new Conferenza();
            c.setConferenzaID(rs.getInt("idconferenza"));
            c.setNome(rs.getString("nome"));
            c.setDescrizione(rs.getString("descrizione"));
            c.setBudget(rs.getFloat("budget"));
            c.setComitatoScientifico(comitatodao.retrieveComitatobyId(rs.getInt("comitatoscientifico")));
            c.setComitatoLocale(comitatodao.retrieveComitatobyId(rs.getInt("comitatolocale")));
            c.setSede(daosede.retrieveSedeByID(rs.getInt("idsede")));
            c.setProprietario(utentedao.retrieveUtentebyID(rs.getInt("proprietario")));
            c.setDataInizio(rs.getTimestamp("datainizio"));
            c.setDataFine(rs.getTimestamp("datafine"));
            conferenze.add(c);
        }
        return conferenze;
    }

    public void saveConferenza(Conferenza c) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String procedure = "insert_conferenza";
        CallableStatement stm = conn.prepareCall ("CALL "+procedure+"(?,?,?,?,?,?,?,?)");
        stm.setString(1, c.getNome());
        stm.setString(2, c.getDescrizione());
        stm.setTimestamp(3, c.getDataInizio());
        stm.setTimestamp(4, c.getDataFine());
        stm.setFloat(5,c.getBudget());
        stm.setInt(6,c.getSede().getSedeID());
        stm.setInt(8, c.getProprietario().getIdUtente());
        stm.setString(7, c.getCodiceValuta());
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
        String update = "UPDATE conferenza SET nome=?,descrizione=?,datainizio=?,datafine=?,budget=?,idsede=?,valuta=? WHERE idconferenza=?";
        PreparedStatement stm = conn.prepareStatement(update);
        stm.setString(1,conferenza.getNome());
        stm.setString(2,conferenza.getDescrizione());
        stm.setTimestamp(3,conferenza.getDataInizio());
        stm.setTimestamp(4,conferenza.getDataFine());
        stm.setFloat(5,conferenza.getBudget());
        stm.setInt(8,conferenza.getConferenzaID());
        stm.setInt(6,conferenza.getSede().getSedeID());
        stm.setString(7,conferenza.getCodiceValuta());
        stm.executeUpdate();
    }
//    public Conferenza retrieveConferenzaByNome(String nomeConferenza){
//        dbcon = DBConnection.getDBconnection();
//        conn = dbcon.getConnection();
//        Conferenza conferenza = null;
//        UtenteDAO userDao = new UtenteDAO();
//        SedeDao sedeDao = new SedeDao();
//        try{
//            String query = "SELECT * from conferenza where nome = ?";
//            PreparedStatement stm = conn.prepareStatement(query);
//            stm.setString(1, nomeConferenza);
//            ResultSet rs = stm.executeQuery();
//            ComitatoDao comitatodao = new ComitatoDao();
//            while(rs.next()){
//                int id = rs.getInt(1);
//                String nome = rs.getString(2);
//                Timestamp datainizio = rs.getTimestamp(3);
//                Timestamp datafine = rs.getTimestamp(4);
//                ComitatoScientifico scientific = (ComitatoScientifico) comitatodao.retrieveComitatobyId(rs.getInt("comitatoscientifico"));
//                ComitatoLocale local = (ComitatoLocale) comitatodao.retrieveComitatobyId(rs.getInt("comitatolocale"));
//                String descrizione = rs.getString(5);
//                Sede sede = sedeDao.retrieveSedeByID(rs.getInt(6));
//                float budget = rs.getFloat(7);
//                Utente proprietario = userDao.retrieveUtentebyID(rs.getInt("proprietario"));
//                String valuta = rs.getString("valuta");
//                conferenza = new Conferenza(id,nome,proprietario,datainizio,datafine,descrizione,local,scientific,sede,budget,valuta);
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return conferenza;
//    }
    public LinkedList<Conferenza> retrieveBySede(Sede sede) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM retrieve_conferenze_by_sede(?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,sede.getSedeID());
        LinkedList<Conferenza> conferenze = new LinkedList<>();
        UtenteDAO utentedao = new UtenteDAO();
        ResultSet rs = stm.executeQuery();
        ComitatoDao comitatodao = new ComitatoDao();
        while(rs.next()){
            Conferenza c = new Conferenza();
            c.setConferenzaID(rs.getInt("idconferenza"));
            c.setNome(rs.getString("nome"));
            c.setDescrizione(rs.getString("descrizione"));
            c.setBudget(rs.getFloat("budget"));
            c.setSede(sede);
            c.setComitatoScientifico(comitatodao.retrieveComitatobyId(rs.getInt("comitatoscientifico")));
            c.setComitatoLocale(comitatodao.retrieveComitatobyId(rs.getInt("comitatolocale")));
            c.setProprietario(utentedao.retrieveUtentebyID(rs.getInt("proprietario")));
            c.setDataInizio(rs.getTimestamp("datainizio"));
            c.setDataFine(rs.getTimestamp("datafine"));
            c.setValuta(rs.getString("valuta"));
            conferenze.add(c);
        }
        return conferenze;
    }

    public LinkedList<Conferenza> retrieveByDateInterval(Date inizio, Date fine) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "select * from retrieve_conferenze_by_interval(?,?)";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setDate(1,inizio);
        stm.setDate(2,fine);
        SedeDao sededao = new SedeDao();
        UtenteDAO utentedao = new UtenteDAO();
        LinkedList<Conferenza> conferenze = new LinkedList<>();
        ResultSet rs = stm.executeQuery();
        ComitatoDao comitatodao = new ComitatoDao();
        while(rs.next()){
            Conferenza c = new Conferenza();
            c.setConferenzaID(rs.getInt("idconferenza"));
            c.setNome(rs.getString("nome"));
            c.setDescrizione(rs.getString("descrizione"));
            c.setBudget(rs.getFloat("budget"));
            c.setComitatoScientifico(comitatodao.retrieveComitatobyId(rs.getInt("comitatoscientifico")));
            c.setComitatoLocale(comitatodao.retrieveComitatobyId(rs.getInt("comitatolocale")));
            c.setSede(sededao.retrieveSedeByID(rs.getInt("idsede")));
            c.setProprietario(utentedao.retrieveUtentebyID(rs.getInt("proprietario")));
            c.setDataInizio(rs.getTimestamp("datainizio"));
            c.setDataFine(rs.getTimestamp("datafine"));
            c.setValuta(rs.getString("valuta"));
            conferenze.add(c);
        }
        return conferenze;
    }
    public Conferenza retrieveConferenzaByNomeAndIdUtente(String nomeConferenza, int idUtente){
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        Conferenza conferenza = null;
        UtenteDAO userDao = new UtenteDAO();
        SedeDao sedeDao = new SedeDao();
        try{
            String query = "SELECT * from conferenza where nome = ? and proprietario =?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, nomeConferenza);
            stm.setInt(2,idUtente);
            ResultSet rs = stm.executeQuery();
            ComitatoDao comitatodao = new ComitatoDao();
            while(rs.next()){
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                Timestamp datainizio = rs.getTimestamp(3);
                Timestamp datafine = rs.getTimestamp(4);
                String descrizione = rs.getString(5);
                Comitato scientific = (comitatodao.retrieveComitatobyId(rs.getInt("comitatoscientifico")));
                Comitato local = (comitatodao.retrieveComitatobyId(rs.getInt("comitatolocale")));
                Sede sede = sedeDao.retrieveSedeByID(rs.getInt(6));
                float budget = rs.getFloat(7);
                Utente proprietario = userDao.retrieveUtentebyID(rs.getInt("proprietario"));
                String valuta = rs.getString("valuta");
                conferenza = new Conferenza(id,nome,proprietario,datainizio,datafine,descrizione,local,scientific,sede,budget,valuta);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conferenza;
    }
}
