package DbConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static DBConnection dbcon = null;
    private Connection conn = null;
    private DBConnection(){}

    public static DBConnection getDBconnection()
    {
        // se istanza nulla la crea
        if (dbcon == null)
            dbcon = new DBConnection();
        return dbcon;
    }

    // metodo per ottenere la connessione
    public Connection getConnection(){
        String pwd = "mandarino";
        try{
            if(conn == null || conn.isClosed()){
                //Registrazione del Driver
                Class.forName("org.postgresql.Driver");
                // Chiama il Driver Manager e chiedi la connessione postgres
                conn = DriverManager.getConnection("jdbc:postgresql://database-oobd.ct0ckvilfzkl.eu-central-1.rds.amazonaws.com","Master_User",pwd);
            }
        }catch (SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }
        return conn;
    }
}