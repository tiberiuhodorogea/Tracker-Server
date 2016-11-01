package Framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
///singleton
	
	private static DataBaseManager instance = null;
	private Connection connection=null;
	private static String DATABASE_NAME = "tracker_database.db";
	
	public static synchronized DataBaseManager get(){
		if(instance == null)
			instance = new DataBaseManager();
		return instance;
	}
	
	private DataBaseManager(){
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection( "jdbc:sqlite:" + DATABASE_NAME );
	    	} catch ( Exception e ) {
	    		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    		System.exit(0);
	    	}
	    	System.out.println("Opened database successfully");
		}
	public Connection getConnection(){
		return this.connection;
	}
	
	public Statement getStatement() throws SQLException{
		return this.getConnection().createStatement();
	}
	

}
