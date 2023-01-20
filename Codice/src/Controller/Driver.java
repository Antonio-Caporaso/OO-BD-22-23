package Controller;

import Model.dbConfig.DBConnection;
import Views.LoginFrame;

import java.sql.Connection;

public class Driver {
    LoginFrame lf;
    
    public static void main(String[] args) {
        Connection conn = null;
        DBConnection dbConnection = DBConnection.getDBconnection();
        conn = dbConnection.getConnection();
        if(conn == null){
            System.out.println("Connessione non riuscita");
            System.exit(0);
        }
        System.out.println("Connessione OK");
        Driver d = new Driver();
    }

    public Driver(){
        lf = new LoginFrame(this);
        lf.setVisible(true);
    }
}
