package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Sede;
import Persistence.Entities.organizzazione.Indirizzo;

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
            IndirizzoDAO dao = new IndirizzoDAO();
            while(rs.next()){
                Sede s = new Sede();
                s.setSedeID(rs.getInt(1));
                s.setNomeSede(rs.getString(2));
                s.setIndirizzo(dao.retrieveIndirizzoByID(rs.getInt("id_indirizzo")));
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
            PreparedStatement stm=conn.prepareStatement("SELECT nome FROM sede");
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
            String query = "SELECT * from sede WHERE id_sede=?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, idSede);
            IndirizzoDAO dao = new IndirizzoDAO();
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                Indirizzo i = dao.retrieveIndirizzoByID(rs.getInt("id_indirizzo"));
                sede = new Sede(id,nome,i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sede;
    }
}
