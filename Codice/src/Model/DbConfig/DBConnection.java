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
        
        try{
            if(conn == null || conn.isClosed()){
                Class.forName("org.postgresql.Driver");
                // Inserire indirizzo del database, nome utente e password
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProgettoOOP", "postgres", "postgres");
            }
        }catch (SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }
        return conn;
    }
}