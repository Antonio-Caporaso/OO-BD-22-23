package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.DTO.Conferenze.Sede;

import java.sql.*;
import java.util.LinkedList;

public class SedeDao {
    Connection conn = null;
    DBConnection dbCon = null;
    public LinkedList<Sede> retrieveAllSedi(){
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        LinkedList<Sede> sedi = new LinkedList<Sede>();
        try {
            PreparedStatement stm=conn.prepareStatement("SELECT * FROM sede");
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Sede s = new Sede();
                s.setSedeID(rs.getInt(1));
                s.setNomeSede(rs.getString(2));
                s.setIndirizzo(rs.getString(3));
                s.setCap(rs.getString(4));
                s.setCostoAffitto(rs.getDouble(5));
                sedi.add(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sedi;
    }
    public LinkedList<String> retrieveNomeSedi(){
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        LinkedList<String> sedi = new LinkedList<String>();
        try {
            PreparedStatement stm=conn.prepareStatement("SELECT nomesede FROM sede");
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                sedi.add(rs.getString(1));
            }
            rs.close();
            stm.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sedi;
    }
    public Sede retrieveSedeByID(int idSede) {
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        Sede sede = null;
        try {
            String query = "SELECT * from sede WHERE idSede=?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, idSede);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                String indirizzo = rs.getString(3);
                String cap = rs.getString(4);
                String city = rs.getString(5);
                float costo = rs.getFloat(6);
                sede = new Sede(id,nome,indirizzo,cap,city,costo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sede;
    }
    public void saveSede(Sede sede){
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        try{
            PreparedStatement stm = conn.prepareStatement("INSERT INTO sede VALUES (default,?,?,?,?,?,?)");
            stm.setString(1, sede.getNomeSede());
            stm.setString(2,sede.getIndirizzo());
            stm.setString(4,sede.getCap());
            stm.setString(5,sede.getCity());
            stm.setDouble(6,sede.getCostoAffitto());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
