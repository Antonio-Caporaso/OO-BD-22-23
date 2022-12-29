import Conferenza.*;
import Organizzazione.*;
import database.*;

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
        
        Ente e1 = new Ente("Università degli studi di Napoli");
        System.out.println(e1.toString());
	}
}
