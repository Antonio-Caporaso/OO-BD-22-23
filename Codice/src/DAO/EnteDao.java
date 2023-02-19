package DAO;

import DbConfig.DBConnection;

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
}
