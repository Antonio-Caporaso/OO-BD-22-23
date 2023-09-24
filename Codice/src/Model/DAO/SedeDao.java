package Model.DAO;

import Model.DbConfig.DBConnection;
import Model.Entities.Conferenze.Sede;
import Model.Entities.Organizzazione.Indirizzo;

import java.sql.*;
import java.util.LinkedList;

public class SedeDao {
    Connection conn = null;
    DBConnection dbCon = null;
    public LinkedList<Sede> retrieveSediLibere(Timestamp inizio, Timestamp fine) throws SQLException {
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        String function = "select * from show_sedi_libere(?,?)";
        PreparedStatement stm = conn.prepareStatement(function);
        stm.setTimestamp(1,inizio);
        stm.setTimestamp(2,fine);
        ResultSet rs = stm.executeQuery();
        LinkedList<Sede> sedi = new LinkedList<>();
        IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
        while(rs.next()){
            Sede s = new Sede();
            s.setSedeID(rs.getInt("id_sede"));
            s.setNomeSede(rs.getString("nome"));
            s.setIndirizzo(indirizzoDAO.retrieveIndirizzoByID(rs.getInt("id_indirizzo")));
            sedi.add(s);
        }
        return sedi;
    }
    public LinkedList<Sede> retrieveAllSedi() throws SQLException {
        dbCon = DBConnection.getDBconnection();
        conn = dbCon.getConnection();
        LinkedList<Sede> sedi = new LinkedList<Sede>();
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
