package Model.DbConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static DBConnection dbcon = null;
    private Connection conn = null;
    private DBConnection(){}

    public static DBConnection getDBconnection()
    {
        if (dbcon == null)
            dbcon = new DBConnection();
        return dbcon;
    }
    public Connection getConnection(){
        String pwd = "mandarino";
        try{
            if(conn == null || conn.isClosed()){
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection("jdbc:postgresql://database-oobd.ct0ckvilfzkl.eu-central-1.rds.amazonaws.com:5432/OO_DB?currentSchema=conference","Master_User",pwd);
            }
        }catch (SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }
        return conn;
    }
}