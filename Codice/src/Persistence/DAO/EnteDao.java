package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.organizzazione.Ente;

import java.sql.*;
import java.util.LinkedList;

public class EnteDao {
    private DBConnection dbConnection;
    private Connection connection;
    public void saveEnteOrganizzatore(Ente ente, Conferenza conferenza) throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        String query = "call add_ente(?,?)";
        CallableStatement stm = connection.prepareCall(query);
        stm.setInt(1,ente.getId_ente());
        stm.setInt(2,conferenza.getId_conferenza());
        stm.executeUpdate();
    }
    public void removeEnteOrganizzatore(Ente ente, Conferenza conferenza) throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        String query = "delete from ente_conferenza where id_conferenza=? and id_ente=?";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setInt(1,conferenza.getId_conferenza());
        stm.setInt(2,ente.getId_ente());
        stm.executeUpdate();
    }
    public LinkedList<String> retrieveAllNomiEnti() {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        LinkedList<String> enti = new LinkedList<>();
        try{
            PreparedStatement stm = connection.prepareStatement("SELECT nome FROM ente");
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                enti.add(rs.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return enti;
    }

    public LinkedList<Ente> retrieveEnti() {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        LinkedList<Ente> enti = new LinkedList<>();
        try{
            PreparedStatement stm = connection.prepareStatement("SELECT * from ente");
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                String sigla = rs.getString(3);
                Ente e = new Ente(id,nome,sigla);
                enti.add(e);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return enti;
    }
    public LinkedList<Ente> retrieveEntiOrganizzatori(Conferenza conferenza) throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection= dbConnection.getConnection();
        String query ="select e.id_ente, e.nome, e.sigla from enti_conferenza e1 natural join ente e where id_conferenza=?";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setInt(1,conferenza.getId_conferenza());
        LinkedList<Ente> enti = new LinkedList<Ente>();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            Ente e = new Ente();
            e.setId_ente(rs.getInt(1));
            e.setNome(rs.getString(2));
            e.setSigla(rs.getString(3));
            enti.add(e);
        }
        return enti;
    }

    public Ente retrieveEnte(int id) throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        String query = "select * from ente where id_ente=?";
        PreparedStatement stm = connection.prepareStatement(query);
        ResultSet rs = stm.executeQuery();
        Ente e = new Ente();
        while (rs.next()) {
            e.setId_ente(rs.getInt(1));
            e.setNome(rs.getString(2));
            e.setSigla(rs.getString(3));
        }
        return e;
    }
}
