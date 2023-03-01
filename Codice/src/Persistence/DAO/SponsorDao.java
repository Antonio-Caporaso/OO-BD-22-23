package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.DTO.organizzazione.Sponsor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SponsorDao {
    DBConnection dbConnection;
    Connection connection;
    public LinkedList<String> retrieveNomiSponsor() {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        LinkedList<String> nomi = new LinkedList<>();
        try{
            PreparedStatement stm = connection.prepareStatement("SELECT nome FROM sponsor");
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                nomi.add(rs.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return nomi;
    }

    public LinkedList<Sponsor> retrieveSponsors() {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        LinkedList<Sponsor> sponsors = new LinkedList<>();
        try{
            PreparedStatement stm = connection.prepareStatement("SELECT * from sponsor");
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                Sponsor s = new Sponsor(id,nome);
                sponsors.add(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sponsors;
    }
    public Sponsor getSponsorByID(int id) throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        Sponsor s = new Sponsor();
        String query = "SELECT * FROM SPONSOR WHERE idsponsor=?";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setInt(1,id);
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            s.setSponsorID(rs.getInt(1));
            s.setNome(rs.getString(2));
        }
        rs.close();
        stm.close();
        return s;
    }
}
