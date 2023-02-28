package DAO;

import DbConfig.DBConnection;
import Model.Conferenze.Conferenza;
import Model.organizzazione.Ente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class EnteDao {
    private DBConnection dbConnection;
    private Connection connection;
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
                Ente e = new Ente(id,nome);
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
        String query ="select e.idente, e.nome from organizza o natural join ente e where idconferenza=?";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setInt(1,conferenza.getConferenzaID());
        LinkedList<Ente> enti = new LinkedList<Ente>();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            Ente e = new Ente();
            e.setEnteID(rs.getInt(1));
            e.setNome(rs.getString(2));
            enti.add(e);
        }
        return enti;
    }
}
