package DAO;

import DbConfig.DBConnection;
import Model.organizzazione.Ente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
