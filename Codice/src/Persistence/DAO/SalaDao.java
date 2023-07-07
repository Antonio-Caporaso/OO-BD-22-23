package Persistence.DAO;

import Persistence.DbConfig.DBConnection;
import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.Conferenze.Sede;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SalaDao {
    private DBConnection dbcon;
    private Connection conn;

    public Sala retrieveSalaByID(int id) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * FROM sala WHERE id_sala = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,id);
        Sala s = new Sala();
        SedeDao sedeDao = new SedeDao();
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
            s.setSalaID(rs.getInt("id_sala"));
            s.setSede(sedeDao.retrieveSedeByID(rs.getInt("id_sede")));
            s.setCapacity(rs.getInt("capienza"));
            s.setNomeSala(rs.getString("nome"));
        }
        return s;
    }


    public LinkedList<Sala> retrieveSaleBySede(Sede sede) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        conn = dbcon.getConnection();
        String query = "SELECT * from sala where id_sede = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setInt(1,sede.getSedeID());
        LinkedList<Sala> sale = new LinkedList<>();
        ResultSet rs = stm.executeQuery();
        SedeDao dao = new SedeDao();
        while(rs.next()){
            Sala s = new Sala();
            s.setSalaID(rs.getInt("id_sala"));
            s.setSede(sede);
            s.setCapacity(rs.getInt("capienza"));
            s.setNomeSala(rs.getString("nome"));
            sale.add(s);
        }
        return sale;
    }
}
