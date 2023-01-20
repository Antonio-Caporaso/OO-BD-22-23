package Model.dbConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    // istanza statica e privata della istanza di questa classe
    private static DBConnection dbcon = null;
    // istanza privata della connessione ad database
    private Connection conn = null;

    // Costruttore privato
    private DBConnection(){}

    // metodo pubblico per ottenere un oggetto della classe privata
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
                conn = DriverManager.getConnection("jdbc:postgresql://oo-bd-instance.ct0ckvilfzkl.eu-central-1.rds.amazonaws.com:5432/oo_bd_22_23","MasterUser",pwd);
            }
        }catch (SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }
        return conn;
    }
}