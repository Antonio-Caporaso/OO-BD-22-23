package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.organizzazione.Sponsor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SponsorDao {
    DBConnection dbConnection;
    Connection connection;

    public int getSponsorID(Sponsor sponsor) throws SQLException {
        int id = 0;
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        LinkedList<String> nomi = new LinkedList<>();
        String query = "SELECT id_sponsor from sponsor where nome = ?";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setString(1,sponsor.getNome());
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            id = rs.getInt(1);
        }
        return id;
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
        String query = "SELECT * FROM SPONSOR WHERE id_sponsor=?";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setInt(1,id);
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            s.setId_sponsor(rs.getInt(1));
            s.setNome(rs.getString(2));
        }
        rs.close();
        stm.close();
        return s;
    }
    public int getSponsorNumber() throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        String query = "select count(distinct id_sponsor) from sponsor_conferenza";
        PreparedStatement stm = connection.prepareStatement(query);
        int result = 0;
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            result = rs.getInt(1);
        }
        return result;
    }
}
