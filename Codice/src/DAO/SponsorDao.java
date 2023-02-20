package DAO;

import DbConfig.DBConnection;
import Model.organizzazione.Sponsor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
