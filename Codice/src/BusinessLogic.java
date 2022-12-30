import conferenza.*;
import database.*;
import organizzazione.*;

import java.sql.Connection;
public class BusinessLogic {

	public static void main(String[] args) {
		
		System.out.println("Questa è la Business Logic!");
		
		//Verifica connessione al database
		Connection conn = null;
        DBConnection dbConnection = DBConnection.getDbconnection();
        conn = dbConnection.getConnection();

        if(conn == null){
            System.out.println("Connessione non riuscita");
            System.exit(0);
        }
        System.out.println("Connessione OK");
	}
}
