package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Conferenza;
import Model.Entities.Ente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class EnteDao {
    private Connection connection;
    private DBConnection dbConnection;

    public int getTotalEnti() throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        String query = "select count(distinct id_ente) from ente_conferenza";
        PreparedStatement stm = connection.prepareStatement(query);
        int result = 0;
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }

    public void removeEnteOrganizzatore(Ente ente, Conferenza conferenza) throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        String query = "delete from ente_conferenza where id_conferenza=? and id_ente=?";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setInt(1, conferenza.getId_conferenza());
        stm.setInt(2, ente.getID());
        stm.executeUpdate();
    }

    public Ente retrieveEnte(int id) throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        String query = "select * from ente where id_ente=?";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        Ente e = new Ente();
        while (rs.next()) {
            e.setID(rs.getInt(1));
            e.setNome(rs.getString(2));
            e.setSigla(rs.getString(3));
        }
        return e;
    }

    public LinkedList<Ente> retrieveEnti() {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        LinkedList<Ente> enti = new LinkedList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * from ente");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                String sigla = rs.getString(3);
                Ente e = new Ente(id, nome, sigla);
                enti.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enti;
    }

    public LinkedList<Ente> retrieveEntiOrganizzatori(Conferenza conferenza) throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        String query = "select e.id_ente, e.nome, e.sigla from ente_conferenza e1 natural join ente e where id_conferenza=?";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setInt(1, conferenza.getId_conferenza());
        LinkedList<Ente> enti = new LinkedList<Ente>();
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Ente e = new Ente();
            e.setID(rs.getInt(1));
            e.setNome(rs.getString(2));
            e.setSigla(rs.getString(3));
            enti.add(e);
        }
        return enti;
    }

    public int saveEnteOrganizzatore(Ente ente, Conferenza conferenza) throws SQLException {
        dbConnection = DBConnection.getDBconnection();
        connection = dbConnection.getConnection();
        String query = "select * from add_new_ente(?,?)";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setInt(1, ente.getID());
        stm.setInt(2, conferenza.getId_conferenza());
        ResultSet rs = stm.executeQuery();
        int result = 0;
        while (rs.next()) {
            result = rs.getInt(1);
        }
        return result;
    }
}
